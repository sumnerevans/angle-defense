package angledefense.config;

import angledefense.logic.*;

/**
 * Created by sumner on 11/29/16.
 */
public class Node {
    public final Location location;
    public final Node next;

    public Node(Location location, Node next) {
        this.location = location;
        this.next = next;
    }
}
