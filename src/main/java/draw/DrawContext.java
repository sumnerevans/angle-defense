package draw;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

import angleDefenseLogic.Util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

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

    private Map<String, Model> models = new HashMap<>();
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

    public void loadAssets() throws FileNotFoundException {
        JsonObject o = new JsonParser().parse(new InputStreamReader(Util.newFileStream("assets.json"))).getAsJsonObject();
        o.entrySet().forEach(e -> {
            JsonObject a = e.getValue().getAsJsonObject();
            String name = e.getKey();
            switch (a.get("type").getAsString()) {
                case "model":
                    models.put(name, Model.load(DrawContext.this, a));
                    break;
            }
        });
    }

    public Model getModel(String asset) {
        return models.get(asset);
    }

    VertexBuffer loadOBJ(String path) {
        return vertbufs.computeIfAbsent(path, p -> {
            try {
                return OBJLoader.load(Util.newFileStream(path));
            } catch (FileNotFoundException e) {
                return null;
            }
        });
    }

    int uploadImage(BufferedImage img) {
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

    int loadPNG(String path) {
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

        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 0);

        window = GLFW.glfwCreateWindow(width, height, "", 0, 0);
        GLFW.glfwMakeContextCurrent(window);

        capabilities = GL.createCapabilities();

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

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        shader = ShaderProgram.builder()
                .setVert(Util.newFileStream("shaders/main.v.glsl"))
                .setFrag(Util.newFileStream("shaders/main.f.glsl"))
                .build();

        shader.bind();

        unMapSize = shader.getUnLoc("map_size");
        unVertRange = shader.getUnLoc("vert_range");
        unModelTrans = shader.getUnLoc("model_trans");
        unTexture = shader.getUnLoc("texture");

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

    public void setModelTransform(float[] matrix) {
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

        models.clear();
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
            img.setData(Raster.createRaster(model, databuf, null));
        }
    }
}
