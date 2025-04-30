import java.util.*;

public class Main {
    public static void main(String[] args) {
        Graph g1 = initNetwork1();
        long startTime = System.nanoTime();
        int maxFlow = edmondsKarp(g1, 0, g1.getAdjM().length-1);
        long endTime = System.nanoTime();
        System.out.println("Max flow is " + maxFlow + "\nTime using Edmonds Karp is " + (endTime - startTime) + " nanoseconds");
        System.out.println();

        Graph g2 = initNetwork1();
        startTime = System.nanoTime();
        maxFlow = fordFulkerson(g2, 0, g2.getAdjM().length-1);
        endTime = System.nanoTime();
        System.out.println("Max flow is " + maxFlow + "\nTime using Fjord Fulkerson is " + (endTime - startTime) + " nanoseconds");
        System.out.println();

        Graph g3 = initNetwork2();
        startTime = System.nanoTime();
        maxFlow = edmondsKarp(g3, 0, g3.getAdjM().length-1);
        endTime = System.nanoTime();
        System.out.println("Max flow is " + maxFlow + "\nTime using Edmonds Karp is " + (endTime - startTime) + " nanoseconds");
        System.out.println();

        Graph g4 = initNetwork2();
        startTime = System.nanoTime();
        maxFlow = fordFulkerson(g4, 0, g1.getAdjM().length-1);
        endTime = System.nanoTime();
        System.out.println("Max flow is " + maxFlow + "\nTime using Fjord Fulkerson is " + (endTime - startTime) + " nanoseconds");
    }

    public static boolean bfs(Graph g, int s, int t, HashMap<Integer, Integer> predecessors){
        int[][] adjM = Arrays.copyOf(g.getAdjM(), g.getAdjM().length);
        Queue<Integer> q = new LinkedList<>();
        predecessors.clear();
        predecessors.put(s, -1);
        q.add(s);

        while(!q.isEmpty()){
            int nextNode = q.poll();
            for(int i = 0; i < adjM.length; i++){
                //System.out.println(predecessors + " i:" + i + " does not contains key: " + !predecessors.containsKey(i));
                if(!predecessors.containsKey(i)  && adjM[nextNode][i] > 0){
                    predecessors.put(i, nextNode);
                    if(i == t){
                        return true;
                    }
                    q.add(i);
                }
            }
        }
        return false;
    }

    public static boolean dfs(Graph g, int s, int t, HashMap<Integer, Integer> predecessors){
        int[][] adjM = g.getAdjM();
        predecessors.clear();
        predecessors.put(s, -1);
        Stack<Integer> stack = new Stack<>();

        stack.push(s);
        while(!stack.isEmpty()){
            int nextNode = stack.pop();
            for(int i = adjM.length - 1; i >= 0; i--){
                if(!predecessors.containsKey(i)  && adjM[nextNode][i] > 0){
                    predecessors.put(i, nextNode);
                    if(i == t){
                        return true;
                    }
                    stack.add(i);
                }
            }
        }
        return false;
    }

    public static int edmondsKarp(Graph g, int s, int t){
        int[][] adjM = g.getAdjM();
        System.out.println("Graph as adjacency matrix");
        printAdjM(adjM);
        int maxFlow = 0;
        HashMap<Integer, Integer> predecessors = new HashMap<>();
        while(bfs(g, s, t, predecessors)){
            int pathFlow = Integer.MAX_VALUE;
            for(int i = t; i != s; i = predecessors.get(i)){
                int u = predecessors.get(i);
                pathFlow = Math.min(pathFlow, adjM[u][i]);
            }

            for(int i = t; i != s; i = predecessors.get(i)){
                int u = predecessors.get(i);
                adjM[u][i] -= pathFlow;
                adjM[i][u] += pathFlow;
            }

            maxFlow += pathFlow;
            predecessors.clear();

        }
        return maxFlow;
    }

    public static int fordFulkerson(Graph g, int s, int t) {
        int[][] adjM = g.getAdjM();
        System.out.println("Graph as adjacency matrix");
        printAdjM(adjM);
        int maxFlow = 0;
        HashMap<Integer, Integer> predecessors = new HashMap<>();
        while (dfs(g, s, t, predecessors)) {
            int pathFlow = Integer.MAX_VALUE;
            for(int i = t; i != s; i = predecessors.get(i)){
                int u = predecessors.get(i);
                pathFlow = Math.min(pathFlow, adjM[u][i]);
            }
            for(int i = t; i != s; i = predecessors.get(i)){
                int u = predecessors.get(i);
                adjM[u][i] -= pathFlow;
                adjM[i][u] += pathFlow;
            }
            maxFlow += pathFlow;
            predecessors.clear();

        }
        return maxFlow;
    }

    public static Graph initNetwork1(){
        Graph g = new Graph(5);
        g.addEdge(0, 1, 20);
        g.addEdge(0, 2, 15);
        g.addEdge(1, 2, 18);
        g.addEdge(2, 3, 10);
        g.addEdge(1, 4, 8);
        g.addEdge(2, 4, 3);
        g.addEdge(3, 4, 12);

        return g;
    }

    public static Graph initNetwork2(){
        Graph g = new Graph(6);
        g.addEdge(0,1,100);
        g.addEdge(0,2,100);
        g.addEdge(1,2,1);
        g.addEdge(1,3,100);
        g.addEdge(1,4,1);
        g.addEdge(2,3,1);
        g.addEdge(2,4,100);
        g.addEdge(3,4,1);
        g.addEdge(3,5,100);
        g.addEdge(4,5,100);

        return g;
    }

    public static void printAdjM(int[][] adjM){
        for(int[] i : adjM)
            System.out.println(Arrays.toString(i));
    }
}