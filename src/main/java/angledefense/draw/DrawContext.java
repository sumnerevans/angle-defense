package angledefense.draw;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

import angledefense.logic.Util;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.Platform;

import javax.imageio.ImageIO;
import javax.swing.*;

public class DrawContext {
    public class Panel extends JPanel {
        public Panel() {
            super();

            this.setPreferredSize(new Dimension(width, height));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (img == null) return;

            g.drawImage(img, 0, 0, null);
        }
    }

    private Map<String, Model> models;
    private Map<String, VertexBuffer> vertbufs = new HashMap<>();
    private Map<String, Integer> textures = new HashMap<>();

    private long window = -1;

    private int fbo = -1;
    private int fbo_color;
    private int fbo_depth;

    private int width = 1024;
    private int height = 1024;

    private GLCapabilities capabilities;
    private BufferedImage img;

    ShaderProgram shader;
    private int unMapSize;
    private int unVertRange;
    private int unModelTrans;
    private int unTexture;

    private void loadAssets() throws FileNotFoundException {
        models = new HashMap<>();

        JsonObject o = new JsonParser().parse(new InputStreamReader(Util.newFileStream("assets.json"))).getAsJsonObject();
        o.entrySet().forEach(e -> {
            JsonObject a = e.getValue().getAsJsonObject();
            String name = e.getKey();
            switch (a.get("type").getAsString()) {
                case "model":
                    VertexBuffer vb = loadOBJ(a.get("path").getAsString());
                    int tex = loadPNG(a.get("texture").getAsString());
                    models.put(name, new Model(vb, tex));
                    break;
            }
        });

        ModelHandle.globalctx = this;
        ModelHandle.waiting.forEach(d -> d.accept(this));
    }

    Model getModel(String modelAsset) {
        return models.get(modelAsset);
    }

    private VertexBuffer loadOBJ(String path) {
        return vertbufs.computeIfAbsent(path, p -> {
            try {
                return OBJLoader.load(Util.newFileStream(path));
            } catch (FileNotFoundException e) {
                return null;
            }
        });
    }

    private int uploadImage(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();

        int[] data = new int[w * h];
        img.getRGB(0, 0, w, h, data, 0, w);

        int tex = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, w, h, 0, GL11.GL_RGBA, GL12.GL_UNSIGNED_INT_8_8_8_8, data);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        return tex;
    }

    private int loadPNG(String path) {
        return textures.computeIfAbsent(path, p -> {
            try {
                return uploadImage(ImageIO.read(Util.newFileStream(path)));
            } catch (IOException e) {
                return null;
            }
        });
    }

    public void init() throws IOException {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!GLFW.glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);

        window = GLFW.glfwCreateWindow(width, height, "", 0, 0);
        GLFW.glfwMakeContextCurrent(window);

        capabilities = GL.createCapabilities();
        System.out.printf("[OPENGL VERSION]: %s\n", GL11.glGetString(GL11.GL_VERSION));
        if (!capabilities.OpenGL30) {
            throw new IllegalStateException("[ERROR]: OpenGL 3.0 is not supported! You can't run the game, sorry.");
        }

        fbo = GL30.glGenFramebuffers();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo);

        fbo_color = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbo_color);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL12.GL_UNSIGNED_INT_8_8_8_8, new int[width * height]);
        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, fbo_color, 0);

        fbo_depth = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbo_depth);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_DEPTH_COMPONENT, width, height, 0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, new float[width * height]);
        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, fbo_depth, 0);

        loadAssets();

        shader = ShaderProgram.builder()
                .setVert(Util.newFileStream("shaders/main.v.glsl"))
                .setFrag(Util.newFileStream("shaders/main.f.glsl"))
                .build();

        shader.bind();

        unMapSize = shader.getUnLoc("u_map_size");
        unVertRange = shader.getUnLoc("u_vert_range");
        unModelTrans = shader.getUnLoc("u_model_trans");
        unTexture = shader.getUnLoc("u_color_tex");

        GL11.glClearDepth(1);
        GL11.glClearColor(0f, 0f, 0f, 0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public void setMapSize(float minx, float miny, float width, float height) {
        GL20.glUniform4f(unMapSize, minx, miny, width, height);
    }

    public void setVerticalRange(float minalt, float maxalt) {
        GL20.glUniform2f(unVertRange, minalt, maxalt);
    }

    void setModelTransform(float[] matrix) {
        GL20.glUniformMatrix4fv(unModelTrans, true, matrix);
    }

    void setShaderTexture(int tex) {
        GL20.glUniform1i(unTexture, tex);
    }

    public void close() {
        GL30.glDeleteFramebuffers(fbo);
        GL11.glDeleteTextures(fbo_color);
        GL11.glDeleteTextures(fbo_depth);

        vertbufs.values().forEach(VertexBuffer::delete);
        vertbufs.clear();
        textures.values().forEach(GL11::glDeleteTextures);
        textures.clear();

        shader.delete();

        models = null;
        ModelHandle.globalctx = null;
    }

    public void preDraw() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void postDraw() {
        int[] data = new int[width * height];

        GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL12.GL_UNSIGNED_INT_8_8_8_8, data);

        {
            DataBufferInt databuf = new DataBufferInt(data, data.length);
            int[] bitmasks = new int[]{0xFF000000, 0x00FF0000, 0x0000FF00, 0x000000FF};
            SampleModel model = new SinglePixelPackedSampleModel(DataBuffer.TYPE_INT, width, height, bitmasks);
            WritableRaster raster = Raster.createWritableRaster(model, databuf, null);
            img = new BufferedImage(new DirectColorModel(32, bitmasks[0], bitmasks[1], bitmasks[2], bitmasks[3]), raster, false, null);
        }
    }
}
