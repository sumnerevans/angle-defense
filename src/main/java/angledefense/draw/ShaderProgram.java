package angledefense.draw;

import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Sam Sartor on 12/4/2016.
 */
public class ShaderProgram {
    public static class Builder {
        private String vertSrc;
        private String fragSrc;

        public Builder setVert(String vertSrc) {
            this.vertSrc = vertSrc;
            return this;
        }

        public Builder setVert(InputStream stream) throws IOException {
            setVert(IOUtils.toString(stream));
            return this;
        }

        public Builder setFrag(String fragSrc) {
            this.fragSrc = fragSrc;
            return this;
        }

        public Builder setFrag(InputStream stream) throws IOException {
            setFrag(IOUtils.toString(stream));
            return this;
        }

        private void printLog(String pre, String log) {
            if (!log.isEmpty()) System.out.printf("[%s LOG]: %s", pre, log);
        }

        public ShaderProgram build() {
            int vert = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
            GL20.glShaderSource(vert, vertSrc);
            GL20.glCompileShader(vert);
            printLog("VERTEX SHADER", GL20.glGetShaderInfoLog(vert));

            int frag = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
            GL20.glShaderSource(frag, fragSrc);
            GL20.glCompileShader(frag);
            printLog("FRAGMENT SHADER", GL20.glGetShaderInfoLog(frag));

            int prog = GL20.glCreateProgram();
            GL20.glAttachShader(prog, vert);
            GL20.glAttachShader(prog, frag);
            GL20.glLinkProgram(prog);
            printLog("SHADER LINK", GL20.glGetProgramInfoLog(frag));

            return new ShaderProgram(vert, frag, prog);
        }
    }

    public final int vert;
    public final int frag;
    public final int prog;

    private ShaderProgram(int vert, int frag, int prog) {
        this.vert = vert;
        this.frag = frag;
        this.prog = prog;
    }

    public void bind() {
        GL20.glUseProgram(prog);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    public int getUnLoc(String uniform) {
        return GL20.glGetUniformLocation(prog, uniform);
    }

    public void delete() {
        GL20.glDeleteProgram(prog);
        GL20.glDeleteShader(vert);
        GL20.glDeleteShader(frag);
    }

    public static Builder builder() {
        return new Builder();
    }
}
