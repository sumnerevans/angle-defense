package angleDefenseLogic;

import angleDefenseGui.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.awt.*;
import java.lang.reflect.*;
import java.util.*;

public class Board implements IDrawable {
    public final int width, height;

    private final Node[] startNodes;
    private final Node[] endNodes;
    private final Square[][] squares;
    private final Image image;
    private final Decoration[] decorations;

    public Board(int width, int height, Node[] startNodes, Node[] endNodes, Square[][] squares,
                 Decoration[] decorations, Image image) {
        this.width = width;
        this.height = height;
        this.startNodes = startNodes;
        this.endNodes = endNodes;
        this.squares = squares;
        this.image = image;
        this.decorations = decorations;
    }

    public static class Builder implements JsonDeserializer<Board> {
        private Gson gson = new Gson();

        @Override
        public Board deserialize(JsonElement json, Type type, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject jobject = json.getAsJsonObject();

            // Deserialize the primitives
            int width = jobject.get("width").getAsInt();
            int height = jobject.get("height").getAsInt();
            String imageName = jobject.get("image").getAsString();

            Map<String, Node> created = new HashMap<>();
            JsonObject nodesJson = jobject.get("nodes").getAsJsonObject();

            // Deserialize the start nodes
            JsonArray startNodes = jobject.get("startNodes").getAsJsonArray();
            Node[] starts = new Node[startNodes.size()];
            for (int i = 0; i < starts.length; i++) {
                starts[i] = buildNode(startNodes.get(i).getAsString(), nodesJson, created);
            }

            // Deserialize end nodes
            JsonArray endNodes = jobject.get("endNodes").getAsJsonArray();
            Node[] ends = new Node[endNodes.size()];
            for (int i = 0; i < ends.length; i++) {
                ends[i] = buildNode(endNodes.get(i).getAsString(), nodesJson, created);
            }

            // Deserialize the Decorations
            Type decorationsType = new TypeToken<Decoration[]>() {
            }.getType();

            Decoration[] decorations = gson.fromJson(jobject.get("decorations").getAsJsonArray(),
                    decorationsType);

            Square[][] squares = new Square[width][height];
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    // TODO: Make this work
                    squares[i][j] = new Square(i, j, null, null);
                }
            }

            // TODO: Pass the image
            return new Board(width, height, starts, ends, squares, decorations, null);
        }

        private Node buildNode(String name, JsonObject nodesJson, Map<String, Node> created) {
            Node out = created.get(name);
            if (out != null) return out;

            JsonArray node = nodesJson.get(name).getAsJsonArray();

            float x = node.get(0).getAsFloat();
            float y = node.get(1).getAsFloat();

            Node next = null;
            if (node.size() > 2) {
                next = buildNode(node.get(2).getAsString(), nodesJson, created);
            }

            out = new Node(x, y, next);
            created.put(name, out);

            return out;
        }

    }

    @Override
    public void draw(DrawContext drawContext) {
        // TODO Auto-generated method stub

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Square getSquare(int x, int y) {
        return this.squares[x][y];
    }

    public Decoration[] getDecorations() {
        return decorations;
    }
}
