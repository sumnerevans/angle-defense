package angleDefenseGui;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class DrawContext {
    public class Panel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (img == null) return;

            g.drawImage(img, 0, 0, null);
        }
    }

    private Map<String, Integer> vboHandles;

    private long window = -1;

    private int fbo = -1;
    private int fbo_color;
    private int fbo_depth;

    private int width = 512;
    private int height = 512;

    private GLCapabilities capabilities;
    private  BufferedImage img;

    public void loadAssets(File dir) {
        // TODO: Implement
    }

    public void init() {
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
    }

    public void close() {

    }

    public void preDraw() {
        GL11.glClearDepth(1000);
        GL11.glClearColor(0.2f, 0.8f, 0.8f, 1.0f);
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

    public void drawTower(String asset, int x, int y) {
        // TODO: Implement
    }

    public void drawUnit(String asset, float x, float y) {
        // TODO: Implement
    }

    public void drawTile(String asset, int x, int y) {
        // TODO: Implement
    }
}
