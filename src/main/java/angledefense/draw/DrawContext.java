package angledefense.draw;

import angledefense.logic.Game;
import angledefense.logic.Location;
import angledefense.util.Util;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.DoubleBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

/**
 * Basically an OpenGL wrapper, don't look at this unless you want to have your brain explode.
 */
public class DrawContext {
    private Map<String, Model> models;
    private Map<String, VertexBuffer> vertbufs = new HashMap<>();
    private Map<String, Integer> textures = new HashMap<>();

    private long window = -1;

    private int width = Game.INITIAL_WIDTH;
    private int height = Game.INITIAL_HEIGHT;

    private GLCapabilities capabilities;

    private ShaderProgram shader;
    private int unMapSize;
    private int unVertRange;
    private int unModelTrans;
    private int unTexture;

    private Game game;

    public DrawContext(Game game) {
        this.game = game;
    }

    /**
     * Loads the game assests
     *
     * @throws FileNotFoundException
     */
    private void loadAssets() throws FileNotFoundException {
        models = new HashMap<>();

        JsonObject o = new JsonParser().parse(new InputStreamReader(Util.newFileStream("assets.json"))).getAsJsonObject();

        // Load all of the things
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

    /**
     * OpenGL mess
     *
     * @throws IOException
     */
    public void init() throws IOException {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!GLFW.glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);

        DoubleBuffer mousebuf1 = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer mousebuf2 = BufferUtils.createDoubleBuffer(1);

        window = GLFW.glfwCreateWindow(width, height, "", 0, 0);
        GLFW.glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                DrawContext.this.width = width;
                DrawContext.this.height = height;
                GL11.glViewport(0, 0, width, height);
                updateSize();
            }
        });
        GLFW.glfwSetWindowCloseCallback(window, new GLFWWindowCloseCallback() {
            @Override
            public void invoke(long window) {
                DrawContext.this.game.setGameOver();
            }
        });
        GLFW.glfwSetMouseButtonCallback(window, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                mousebuf1.clear();
                mousebuf2.clear();
                GLFW.glfwGetCursorPos(window, mousebuf1, mousebuf2);
                game.onBoardClick(getLocFromWindow((int) mousebuf1.get(0), (int) mousebuf2.get(0)), button, action == GLFW_PRESS);
            }
        });
        GLFW.glfwMakeContextCurrent(window);

        capabilities = GL.createCapabilities();
        System.out.printf("[OPENGL VERSION]: %s\n", GL11.glGetString(GL11.GL_VERSION));
        if (!capabilities.OpenGL30) {
            throw new IllegalStateException("[ERROR]: OpenGL 3.0 is not supported! You can't run the game, sorry.");
        }

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

        updateSize();

        GL11.glClearDepth(1);
        GL11.glClearColor(0f, 0f, 0f, 0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    private void updateSize() {
        int mw = game.getBoard().width;
        int mh = game.getBoard().height;

        float xscale = (float) width / mw;
        float yscale = (float) height / mh;
        float scale = Math.min(xscale, yscale) * 2;
        xscale = scale / width;
        yscale = scale / height;

        float tx = -.5f * mw;
        float ty = -.5f * mh + 1;

        GL20.glUniform4f(unMapSize, tx, ty, xscale, -yscale);
    }

    private Location getLocFromWindow(int x, int y) {
        int mw = game.getBoard().width;
        int mh = game.getBoard().height;

        float xscale = (float) width / mw;
        float yscale = (float) height / mh;
        float scale = Math.min(xscale, yscale);

        float bw = width - mw * scale;
        float bh = height - mh * scale;

        float xx = (x - bw / 2) / scale;
        float yy = (y - bh / 2) / scale;

        return new Location(xx, yy);
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
        GLFW.glfwSwapBuffers(window);
        GLFW.glfwPollEvents();

        /*
        int[] data = new int[width * height];

        GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL12.GL_UNSIGNED_INT_8_8_8_8, data);

        {
            DataBufferInt databuf = new DataBufferInt(data, data.length);
            int[] bitmasks = new int[]{0xFF000000, 0x00FF0000, 0x0000FF00, 0x000000FF};
            SampleModel model = new SinglePixelPackedSampleModel(DataBuffer.TYPE_INT, width, height, bitmasks);
            WritableRaster raster = Raster.createWritableRaster(model, databuf, null);
            img = new BufferedImage(new DirectColorModel(32, bitmasks[0], bitmasks[1], bitmasks[2], bitmasks[3]), raster, false, null);
        }
        */
    }
}
