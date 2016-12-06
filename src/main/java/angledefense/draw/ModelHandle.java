package angledefense.draw;

import angledefense.logic.Location;
import angledefense.util.Matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Sam Sartor on 12/4/2016.
 */
public class ModelHandle {
	static List<Consumer<DrawContext>> waiting = new ArrayList<>();
	static DrawContext globalctx = null;
	public float[] transform = new float[]{
			1, 0, 0, 0,
			0, 1, 0, 0,
			0, 0, 1, 0,
			0, 0, 0, 1};
	private DrawContext ctx = null;
	private Model model = null;

	private ModelHandle() {
	}

	/**
	 * Creates a ModelHandle given an asset name
	 * @param modelAsset
	 * @return
	 */
	public static ModelHandle create(String modelAsset) {
		ModelHandle out = new ModelHandle();
		if (globalctx != null) {
			out.ctx = globalctx;
			out.model = globalctx.getModel(modelAsset);
		} else {
			waiting.add(c -> {
				out.model = c.getModel(modelAsset);
				out.ctx = c;
			});
		}
		return out;
	}

	public void draw() {
		if (model == null) throw new IllegalStateException("DrawContext has not been initialized");
		else if (globalctx == null) throw new IllegalStateException("DrawContext has not been closed");

		ctx.setModelTransform(transform);
		model.draw(ctx);
	}

	public void setTransform(float[] matrix) {
		transform = matrix;
	}

	public void setTransform(Location loc, float scale, float altitude, float rotation) {
		transform = Matrix.gen(loc, scale, altitude, rotation);
	}
}
