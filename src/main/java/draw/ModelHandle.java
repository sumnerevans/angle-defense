package draw;

import angleDefenseLogic.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Sam Sartor on 12/4/2016.
 */
public class ModelHandle {
    static List<Consumer<DrawContext>> waiting = new ArrayList<>();
    static DrawContext globalctx = null;

    private DrawContext ctx = null;
    private Model model = null;

    public float[] transform = new float[] {
        1, 0, 0, 0,
        0, 1, 0, 0,
        0, 0, 1, 0,
        0, 0, 0, 1};

    private ModelHandle() {}

    public void draw() {
        if (model == null) throw new IllegalStateException("DrawContext has not been initialized");
        else if (globalctx == null) throw new IllegalStateException("DrawContext has not been closed");

        ctx.setModelTransform(transform);
        model.draw(ctx);
    }

    public void setTransform(float[] matrix) {
        transform = matrix;
    }

    public void setTransform(Location loc, float altitude, float scale, float rotation) {
        transform = Matrix.gen(loc, altitude, scale, rotation);
    }

    public static ModelHandle create(String modelAsset) {
        ModelHandle out = new ModelHandle();
        if (globalctx != null) {
            out.model = globalctx.getModel(modelAsset);
        } else {
            waiting.add(c -> {
                out.model = c.getModel(modelAsset);
                out.ctx = c;
            });
        }
        return out;
    }
}
