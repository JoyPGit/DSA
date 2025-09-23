**BFS**
```java
class Solution {
    /**
    start with a gene > modify -> should be valid (present in bank)
    -> stop when endGene is reached
    -> backtracking, (A/C/G/T)
    */
    public int minMutation(String startGene, String endGene, String[] bank) {
        int count = 0;
        char[] choices = {'A', 'C', 'G', 'T'};
        Set<String> valid = new HashSet<>();
        Set<String> visited = new HashSet<>();
        // add words of bank to valid
        for(String str : bank) valid.add(str);
    
        // add to visited as soon as added to q
        Deque<String> q = new LinkedList<>();
        q.addLast(startGene);
        visited.add(startGene); // 1

        while(q.size() != 0){
            count++;
            int size = q.size();
            for(int s = 0; s<size; s++){ // 2
                String curr = q.removeFirst();
                char[] currArray = curr.toCharArray();
                // check for all positions
                for(int i =0; i<currArray.length; i++){
                    char temp = currArray[i];
                    for(char c : choices) {
                        if(temp == c) continue; // 3
                        currArray[i] = c;
                        String newString = new String(currArray);
                        if(isSafe(newString, valid, visited)){
                            if(newString.equals(endGene)) return count; // 4
                            q.addLast(newString);
                            visited.add(newString);
                        }
                    }
                    currArray[i] = temp;
                }
            }
        }
        return -1;
    }

    private boolean isSafe(String str, Set<String> valid, Set<String> visited){
        if(valid.contains(str) && !visited.contains(str)) return true;
        return false;
    }
}
```

**BFS with pruning**
```java
class Solution {
public int networkDelayTime(int[][] times, int n, int k) {
    int[][] arr = new int[n+1][n+1];
    for(int[] row: arr) Arrays.fill(row, -1); // 0 wt edge check
        int[] visited = new int[n+1];
        Arrays.fill(visited, Integer.MAX_VALUE);
        visited[0] = 0;
        visited[k] = 0;
        // build graph
        for(int[] time : times){
            arr[time[0]][time[1]] = time[2];
        }
        Deque<Node> q = new LinkedList<>();
        q.addLast(new Node(k, 0));
        visited[k] = 1;
        while(q.size() != 0){
            Node curr = q.removeFirst();
            for(int i =1; i<=n; i++){
                if(edge != -1){
                    if(visited[i] > curr.time + edge){
                        visited[i] = curr.time + edge;
                        q.addLast(new Node(i, visited[i]));
                    }
                }
            }
        }
        int max = Integer.MIN_VALUE;
        for(int i : visited){
            if(i == Integer.MAX_VALUE) return -1;
            max = Math.max(max, i);
        }
        return max;
    }
    class Node{
        int key, time;
        Node(int k, int t){
            this.key = k;
            this.time = t;
        }
    }
}
```

**DFS**
```java
class Solution{
    private Set<Integer> visited;
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        this.visited = new HashSet<>();
        dfs(0, rooms);
        return visited.size() == rooms.size();
    }
    
    private void dfs(int node, List<List<Integer>> rooms){
        if(visited.contains(node)) return;
        visited.add(node);
        for(int i : rooms.get(node)) dfs(i, rooms);
    }
}
```

**Topo Sort DFS with color 0,1,2**
```java
class Solution3 {
    int index;
    int[] color, res;
    Map<Integer, List<Integer>> map;
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int n = numCourses;
        this.index = n-1;
        this.color = new int[n]; this.res = new int[n];
        this.map = new HashMap<>();
    
        for(int[] pre : prerequisites){
            List<Integer> list = map.getOrDefault(pre[1], new ArrayList<>());
            list.add(pre[0]);
            map.put(pre[1], list);
        }

        for(int i =0; i<n; i++){
            if(color[i] == 0){
                if(dfs(i)) return new int[]{};
            }
        }
        return res;
    }

    // return true if conflict
    private boolean dfs(int node){
        if(color[node] == 2) return false;
        if(color[node] == 1) return true;

        color[node] = 1;
        List<Integer> list = map.getOrDefault(node, new ArrayList<>());
        for(int i : list) {
            if(dfs(i)) return true;
        }
        color[node] = 2;
        res[index--] = node;
        return false;
    }
}
```

**Union Find**
```java
class Solution {
    int[] parent, rank;
    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        parent = new int[n+1];
        rank = new int[n+1];

        // initialize parent
        for(int i =1; i<=n; i++){
            parent[i] = i;
            rank[i] = 0;
        }

        // edge by edge
        for(int[] edge : edges){
            if(!union(edge[0], edge[1])) return edge;
        }
        return new int[]{};
    }

    private boolean union(int a, int b){
        int root1 = findParent(a), root2 = findParent(b);
        // System.out.println("roots "+root1+", "+root2);
        // System.out.println(Arrays.toString(parent));
        // same component, no union
        if(root1 == root2) {
            return false;
        }
        parent[root1] = root2; // set leader
        // System.out.println("after "+Arrays.toString(parent));
        return true;
    }

    private int findParent(int node){
        while(node != parent[node]){
            parent[node] = parent[parent[node]];
            node = parent[node];
        }
        return node;
    }
}
```

**Backtracking**
```java
class Solution { 
    List<List<Integer>> res = new ArrayList<>();
    Map<Integer, List<Integer>> map = new HashMap<>();
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        int n = graph.length;
        for(int i=0; i<n; i++){
            List<Integer> list = map.getOrDefault(i, new ArrayList<>());
            for(int j : graph[i]) list.add(j);
            map.put(i, list);
        }
        // System.out.println(map);
        dfs(0, new ArrayList<>(), n-1);
        return res;
    }

    private void dfs(int node, List<Integer> list, int target){
        list.add(node); // [ADD]

        if(node == target){
            res.add(new ArrayList<>(list));
            list.remove(list.size()-1);
            return;
        }

        for(int i : map.get(node)) dfs(i, list, target);
        list.remove(list.size()-1); [REMOVE]
    }

}
```

**Grid BFS**
```java
class Solution {
    int[] rows = {0, -1, 0, 1}, cols = {-1, 0, 1, 0};

    public int orangesRotting(int[][] grid) {
        Deque<Node> q = new LinkedList<>();
        int m = grid.length, n = grid[0].length, time = -1;
        int goodOnes = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) {
                    q.addLast(new Node(i, j));
                }
                if (grid[i][j] == 1) goodOnes++;
            }
        }
        if (goodOnes == 0 && q.size() == 0) return 0;

        while (q.size() != 0) {
            int size = q.size();
            for (int s = 0; s < size; s++) {
                Node curr = q.removeFirst();
                for (int i = 0; i < 4; i++) {
                    int newRow = curr.row + rows[i], newCol = curr.col + cols[i];
                    if (isSafe(newRow, newCol, grid)) {
                        goodOnes--;
                        grid[newRow][newCol] = 2;
                        q.addLast(new Node(newRow, newCol));
                    }
                }
            }
            time++;
        }
        return goodOnes == 0 ? time : -1;
    }

    private boolean isSafe(int row, int col, int[][] grid) {
        if (row >= 0 && row < grid.length
                && col >= 0 && col < grid[0].length
                && grid[row][col] == 1) return true;
        return false;
    }

    class Node {
        int row, col;

        Node(int r, int c) {
            this.row = r;
            this.col = c;
        }
    }
}

```