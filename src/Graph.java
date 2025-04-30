import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {
    protected int v;

    protected int[][] adjM;

    public Graph(int v){
        this.v = v;
        adjM = new int[v][v];
        for(int i = 0; i < v; i++){
            Arrays.fill(adjM[i],0);
        }
    }

    public int[][] getAdjM() {
        return adjM;
    }

    public void addEdge(int startV, int endV, int capacity){
        adjM[startV][endV] = capacity;
    }

}
