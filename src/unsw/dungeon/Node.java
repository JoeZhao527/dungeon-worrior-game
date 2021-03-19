package unsw.dungeon;

public class Node {
    private int x;
    private int y;
    private int distance;
    public Node(int x, int y, int distance) {
        this.x = x;
        this.y = y;
        this.distance = distance;
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public int distance() {
        return this.distance;
    }
}