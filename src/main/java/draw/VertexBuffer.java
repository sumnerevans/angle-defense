package draw;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import java.nio.FloatBuffer;

/**
 * Created by Sam Sartor on 12/4/2016.
 */
public class VertexBuffer {
    public static class Builder {
        public final FloatBuffer data;
        public final int vertexCount;

        private Builder(int verts) {
            this.vertexCount = verts;
            data = BufferUtils.createFloatBuffer(verts * 8);
        }

        public void add(float... floats) {
            data.put(floats);
        }

        public VertexBuffer build() {
            data.flip();

            int vao = GL30.glGenVertexArrays();
            GL30.glBindVertexArray(vao);

            int vbo = GL15.glGenBuffers();
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);

            GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 8, 0);
            GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, true, 8, 3);
            GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, true, 8, 6);

            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
            GL30.glBindVertexArray(0);

            return new VertexBuffer(vbo, vao, vertexCount);
        }
    }

    // Vertex Buffer Object
    public final int vbo;
    // Vertex Array Object
    public final int vao;
    public final int vertexCount;

    public VertexBuffer(int vbo, int vao, int vertexCount) {
        this.vbo = vbo;
        this.vao = vao;
        this.vertexCount = vertexCount;
    }

    public void draw() {
        GL30.glBindVertexArray(vao);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    public static Builder builder(int verts) {
        return new Builder(verts);
    }
}
