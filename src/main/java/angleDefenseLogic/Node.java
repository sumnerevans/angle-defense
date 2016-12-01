package angleDefenseLogic;

/**
 * Created by sumner on 11/29/16.
 */
public class Node {
    public final float x, y;
    public final Node next;

    public Node(float x, float y, Node next) {
        this.x = x;
        this.y = y;
        this.next = next;
    }
}
