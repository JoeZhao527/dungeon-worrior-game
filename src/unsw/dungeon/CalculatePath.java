package unsw.dungeon;

import java.util.LinkedList;
import java.util.Queue;

public class CalculatePath {

    private int startX, startY, endX, endY, maxX, maxY;
    private Enemy enemy;
    private Player player;

    public CalculatePath(Player player, Enemy enemy, Dungeon dungeon) {
        this.startX = enemy.getX();
        this.startY = enemy.getY();
        this.endX = player.getX();
        this.endY = player.getY();
        this.maxX = dungeon.getWidth();
        this.maxY = dungeon.getHeight();
        this.enemy = enemy;
        this.player = player;
    }

    public int nextStep() {
        // create a queue
        Queue<Integer> qx = new LinkedList<>();
        Queue<Integer> qy = new LinkedList<>();
        
        // create a visited array
        boolean[][] visited = new boolean[maxX][maxY];
        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                visited[i][j] = false;
            }
        }

        // create a predecisor array
        Node[][] pred = new Node[maxX][maxY];
        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                pred[i][j] = null;
            }
        }
        pred[startX][startY] = new Node(startX,startY,0);
        // set the first node as visited, put it into the queue
        visited[startX][startY] = true;
        qx.add(startX);
        qy.add(startY);

        boolean reachEnd = false;
        int nodesInLayer = 1;
        int nodesInNextLayer = 0;
        // loop through the grid
        while (qx.isEmpty() == false) {
            // dequeue an item
            int x = qx.remove();
            int y = qy.remove();

            
            // explore neighbours
            if (enemy.checkMovable(x+1, y) && visited[x+1][y] ==false) {
                qx.add(x+1);
                qy.add(y);
                visited[x+1][y] = true;
                if (pred[x+1][y] == null) 
                    pred[x+1][y] = new Node(x,y,pred[x][y].distance()+1);
                else if (pred[x+1][y].distance() >= pred[x][y].distance()+1) 
                    pred[x+1][y] = new Node(x,y,pred[x][y].distance()+1);
                nodesInNextLayer++;
            }
            if (enemy.checkMovable(x, y+1) && visited[x][y+1] ==false) {
                qx.add(x);
                qy.add(y+1);
                visited[x][y+1] = true;
                if (pred[x][y+1] == null) 
                    pred[x][y+1] = new Node(x,y,pred[x][y].distance()+1);
                else if (pred[x][y+1].distance() >= pred[x][y].distance()+1) 
                    pred[x][y+1] = new Node(x,y,pred[x][y].distance()+1);
                nodesInNextLayer++;
            }
            if (enemy.checkMovable(x-1, y) && visited[x-1][y] ==false) {
                qx.add(x-1);
                qy.add(y);
                visited[x-1][y] = true;
                if (pred[x-1][y] == null) 
                    pred[x-1][y] = new Node(x,y,pred[x][y].distance()+1);
                else if (pred[x-1][y].distance() >= pred[x][y].distance()+1) 
                    pred[x-1][y] = new Node(x,y,pred[x][y].distance()+1);
                nodesInNextLayer++;
            }
            if (enemy.checkMovable(x, y-1) && visited[x][y-1] ==false) {
                qx.add(x);
                qy.add(y-1);
                visited[x][y-1] = true;
                if (pred[x][y-1] == null) 
                    pred[x][y-1] = new Node(x,y,pred[x][y].distance()+1);
                else if (pred[x][y-1].distance() >= pred[x][y].distance()+1) 
                    pred[x][y-1] = new Node(x,y,pred[x][y].distance()+1);
                nodesInNextLayer++;
            }
            nodesInLayer--;
            if (nodesInLayer == 0) {
                nodesInLayer = nodesInNextLayer;
                nodesInNextLayer = 0;
            }

            // if it reaches the end, stop searching
            if (x == endX && y == endY) {
                reachEnd = true;
                break;
            }
        }

        int i = endX;
        int j = endY;
        int direction = -1;
        if (reachEnd) {
            while (i != startX || j != startY) {
                int x = pred[i][j].getX();
                int y = pred[i][j].getY();
                //System.out.format("pred[%d][%d] is %d %d\n",i,j,x,y);
                if (i == x && j == y-1) direction = 0;
                else if (i == x && j == y+1) direction = 1;
                else if (i == x-1 && j == y) direction = 2;
                else direction = 3;
                i = x;
                j = y;
            }
        }
        //System.out.println(direction);
        if (player.getPotion() != null) {
            if (direction == 0) direction = 1;
            if (direction == 1) direction = 0;
            if (direction == 2) direction = 3;
            if (direction == 3) direction = 2;
        }
        return direction;
    }
}