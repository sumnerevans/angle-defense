package angledefense.draw;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

/**
 * Created by Sam Sartor on 12/4/2016.
 */
public class Model {
	public final VertexBuffer verts;
	public final int texture;

	Model(VertexBuffer verts, int texture) {
		this.verts = verts;
		this.texture = texture;
	}

	public void draw(DrawContext context) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);

		context.setShaderTexture(0);
		verts.draw();
	}
}
