package angledefense.config;

import angledefense.draw.DrawContext;
import angledefense.logic.Decoration;
import angledefense.logic.IDrawable;
import angledefense.logic.Location;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is immutable
 */
public class Board implements IDrawable {
	public final int width, height;

	private final Node[] startNodes;
	private final Node[] endNodes;
	private final Square[][] squares;
	private final Decoration[] decorations;

	/**
	 * Create a board, this is called by the Builder
	 *  @param width
	 * @param height
	 * @param startNodes
	 * @param endNodes
	 * @param squares
	 * @param decorations
	 */
	private Board(int width, int height, Node[] startNodes, Node[] endNodes, Square[][] squares,
				  Decoration[] decorations) {
		this.width = width;
		this.height = height;
		this.startNodes = startNodes;
		this.endNodes = endNodes;
		this.squares = squares;
		this.decorations = decorations;
	}

	@Override
	public void draw(DrawContext drawContext) {
		// Draw the squares
		for (Square[] row : squares) {
			for (Square s : row) {
				s.draw(drawContext);
			}
		}
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

	public Node[] getStartNodes() {
		return startNodes;
	}

	/**
	 * Builds a Board from JSON
	 */
	public static class Builder implements JsonDeserializer<Board> {
		private Gson gson = new Gson();

		// Deserializes the Board JSON
		@Override
		public Board deserialize(JsonElement json, Type type, JsonDeserializationContext context)
				throws JsonParseException {
			JsonObject jsonObject = json.getAsJsonObject();

			// Deserialize the primitives
			int width = jsonObject.get("width").getAsInt();
			int height = jsonObject.get("height").getAsInt();

			Map<String, Node> created = new HashMap<>();
			JsonObject nodesJson = jsonObject.get("nodes").getAsJsonObject();

			// Deserialize the start nodes
			JsonArray startNodes = jsonObject.get("startNodes").getAsJsonArray();
			Node[] starts = new Node[startNodes.size()];
			for (int i = 0; i < starts.length; i++) {
				starts[i] = buildNode(startNodes.get(i).getAsString(), nodesJson, created);
			}

			// Deserialize end nodes
			JsonArray endNodes = jsonObject.get("endNodes").getAsJsonArray();
			Node[] ends = new Node[endNodes.size()];
			for (int i = 0; i < ends.length; i++) {
				ends[i] = buildNode(endNodes.get(i).getAsString(), nodesJson, created);
			}

			// Deserialize the Decorations
			Type decorationsType = new TypeToken<Decoration[]>() {
			}.getType();

			Decoration[] decorations = gson.fromJson(jsonObject.get("decorations").getAsJsonArray(),
					decorationsType);

			// Deserialize the squareTypes
			Type stringArrayType = new TypeToken<String[]>() {
			}.getType();

			String[] squareTypes = gson.fromJson(jsonObject.get("squareTypes").getAsJsonArray(),
					stringArrayType);

			// Create and populate the squares array
			Square[][] squares = new Square[width][height];

			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					Square.SquareType squareType;

					if (squareTypes[j].charAt(i) == 'T')
						squareType = Square.SquareType.TRENCH;
					else
						squareType = Square.SquareType.GROUND;

					// Create and populate the cliff sides
					ArrayList<Square.CliffSide> cliffSides = new ArrayList<>();

					// Add the appropriate cliffSides
					if (squareType == Square.SquareType.GROUND) {
						// TOP
						if (j - 1 > 0 && squareTypes[j - 1].charAt(i) == 'T') {
							cliffSides.add(Square.CliffSide.TOP);
						}

						// RIGHT
						if (i + 1 < squareTypes[j].length() && squareTypes[j].charAt(i + 1) == 'T') {
							cliffSides.add(Square.CliffSide.RIGHT);
						}

						// BOTTOM
						if (j + 1 < squareTypes.length && squareTypes[j + 1].charAt(i) == 'T') {
							cliffSides.add(Square.CliffSide.BOTTOM);
						}

						// LEFT
						if (i - 1 > 0 && squareTypes[j].charAt(i - 1) == 'T') {
							cliffSides.add(Square.CliffSide.LEFT);
						}
					}

					squares[i][j] = new Square(new Location(i, j), squareType,
							cliffSides.toArray(new Square.CliffSide[0]));
				}
			}

			return new Board(width, height, starts, ends, squares, decorations);
		}

		/**
		 * Builds a node from JSON
		 *
		 * @param name
		 * @param nodesJson
		 * @param created
		 * @return
		 */
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

			out = new Node(new Location(x, y), next);
			created.put(name, out);

			return out;
		}
	}
}
