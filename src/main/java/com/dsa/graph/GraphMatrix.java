package com.dsa.graph;

import java.util.*;

public class GraphMatrix {

    /**
     * **Grid**
     *
     *
     * -- **time/path** (bfs with pruning)
     * Rotting oranges
     * Walls and Gates
     * Rotting Oranges
     * Snakes and Ladders
     *
     *
     * -- [order/traversal] (dfs)
     * All paths from source to target
     *
     */


    /**
     * bfs with pruning, maintain visited; update only if dist > curr.dist+1
     * initialize visited to INF
     * use Node class {row, col, dist}
     * <p>
     * https://leetcode.com/problems/walls-and-gates/submissions/1774161942/
     */
    class Solution1_1 {
        int[] rows = new int[]{0, 1, 0, -1}, cols = new int[]{-1, 0, 1, 0};

        public void wallsAndGates(int[][] rooms) {
            int m = rooms.length, n = rooms[0].length;
            int[][] dist = new int[m][n];
            for (int[] i : dist) Arrays.fill(i, Integer.MAX_VALUE);

            Deque<Node> q = new LinkedList<>();

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (rooms[i][j] == 0) {
                        q.addLast(new Node(i, j, 0));
                    }
                }
            }

            while (q.size() != 0) {
                int size = q.size();
                for (int s = 0; s < size; s++) {
                    Node curr = q.removeFirst();
                    for (int i = 0; i < 4; i++) {
                        int newRow = curr.r + rows[i], newCol = curr.c + cols[i];
                        if (isSafe(newRow, newCol, rooms)) {
                            if (dist[newRow][newCol] > curr.dist + 1) {
                                dist[newRow][newCol] = curr.dist + 1;
                                q.addLast(new Node(newRow, newCol, curr.dist + 1));
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (rooms[i][j] == 0 || rooms[i][j] == -1) continue;
                    rooms[i][j] = dist[i][j];
                }
            }
        }

        class Node {
            int r, c, dist;

            Node(int r, int c, int d) {
                this.r = r;
                this.c = c;
                this.dist = d;
            }
        }

        private boolean isSafe(int row, int col, int[][] arr) {
            if (row >= 0 && row < arr.length
                    && col >= 0 && col < arr[0].length
                    && arr[row][col] != -1) return true;
            return false;
        }
    }

    /**
     * bfs -> queue; pruning is done by marking as rotten
     * <p>
     * https://leetcode.com/problems/rotting-oranges/submissions/1764566002/
     */
    class Solution1_2 {
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

    class Solution {
        /**
         1 build arr
         2 build map

         starting from one ladder, the next ladder won't be considered
         as we start from index + 1 after climbing ladder

         if ladder or snake, only mark the final node as visited

         visited check and continue too only when adding

         */
        int[] arr;
        Map<Integer, Integer> map = new HashMap<>();
        int[] visited;
        public int snakesAndLadders(int[][] board) {
            int m = board.length, n = board[0].length, total = m*n;
            int index =0, count = 0, base = 0;

            visited = new int[total+1];

            // build map correctly
            boolean lToR = true;
            for(int i =m-1; i>=0; i--){
                if(lToR){
                    for(int j = 0; j<n; j++){
                        index++;
                        if(board[i][j] != -1) map.put(index, board[i][j]);
                    }
                }
                else{
                    for(int j = n-1; j>=0; j--){
                        index++;
                        if(board[i][j] != -1) map.put(index, board[i][j]);
                    }
                }
                lToR = !lToR;
            }

            // bfs -> no subsequent
            Deque<Integer> q = new LinkedList<>();
            q.addLast(1);
            visited[1] = 1;

            while(q.size() != 0){
                int size = q.size();
                for(int s = 0; s<size; s++){
                    int curr = q.removeFirst();
                    if(curr == total) return count;
                    // System.out.println("curr "+curr.index+", "+curr.count);

                    for(int i =1; i<=6; i++){
                        int newIndex = curr + i;
                        if(newIndex > total) continue;
                        // TODO : find why
                        // THIS IS THE PROBLEM, why?
                        // You're checking visited[newIndex] before applying
                        // the ladder/snake logic
                        // if(visited[newIndex] != 0) continue;

                        // TODO : find why
                        // THIS IS THE PROBLEM; why?
                        // if(newIndex == total) return count+1;

                        if(map.containsKey(newIndex)){
                            // go to the top index of ladder
                            newIndex = map.get(newIndex);
                        }

                        // TODO : find why it works here but not at line 210
                        if(newIndex == total) return count+1;

                        if(visited[newIndex] != 0) continue;
                        q.addLast(newIndex);
                        visited[newIndex] = 1;
                    }
                }
                count++;

            }
            return -1;

        }

    }

    /**
     * isSafe when grid[i][j] = 0;
     * mark visited while adding to q for quicker processing
     * https://leetcode.com/problems/shortest-path-in-binary-matrix/submissions/1649702262/
     */
    class Solution1_3 {
        private int[] rows = {0, 1, 1, 1, 0, -1, -1, -1},
                cols = {-1, -1, 0, 1, 1, 1, 0, -1};

        public int shortestPathBinaryMatrix(int[][] grid) {
            if (grid[0][0] != 0) return -1;
            int m = grid.length, n = grid[0].length;
            if (m == 1 && n == 1) return grid[0][0] == 0 ? 1 : -1;
            Deque<Node> q = new LinkedList<>();
            q.addLast(new Node(0, 0, 1));
            grid[0][0] = -1; // mark visited

            while (q.size() != 0) {
                int size = q.size();
                for (int s = 0; s < size; s++) {
                    Node curr = q.removeFirst();
                    for (int i = 0; i < 8; i++) {
                        int newR = curr.row + rows[i], newC = curr.col + cols[i];
                        if (isSafe(grid, newR, newC)) {
                            if (newR == m - 1 && newC == n - 1) return curr.val + 1;
                            q.addLast(new Node(newR, newC, curr.val + 1));
                            grid[newR][newC] = -1;
                        }
                    }
                }
            }
            return -1;
        }

        private boolean isSafe(int[][] arr, int row, int col) {
            if (row >= 0 && row < arr.length
                    && col >= 0 && col < arr[0].length
                    && arr[row][col] == 0) return true;
            return false;
        }

        class Node {
            int row, col, val;

            Node(int r, int c, int v) {
                this.row = r;
                this.col = c;
                this.val = v;
            }
        }
    }

    /**
     * bfs
     * visited, mark when adding to q
     * dir 0 -> left, 1 -> up, 2 -> right, 3 -> down
     * hold drection as well
     * check if there exists a wall in the same direction, if yes go orthogonal
     * https://leetcode.com/problems/the-maze/submissions/1779241937/
     */
    class Solution1_4 {

        private int[][][] visited;
        private Deque<Node> q = new LinkedList<>();

        public boolean hasPath(int[][] maze, int[] start, int[] destination) {
            int m = maze.length, n = maze[0].length;
            visited = new int[m][n][4];

            for(int i =0; i<4; i++) {
                q.addLast(new Node(start[0], start[1], i));
                visited[start[0]][start[1]][i] = 1; //
            }

            while(q.size() != 0){
                int size = q.size();
                for(int s = 0; s<size; s++){
                    Node curr = q.removeFirst();
                    // System.out.println(curr);
                    if(hasWall(curr, maze)){
                        // System.out.println("has opp "+curr);
                        // ball can stop at dest only if opposing wall, hence inside
                        // hasWall
                        if(curr.row == destination[0] && curr.col == destination[1])
                            return true;
                        // add ortho dirs;
                        addOrthoDirs(curr, maze);
                    }
                    else {
                        // continue in same dir
                        addSameDir(curr, maze);
                    }
                }
            }
            return false;
        }

        private boolean hasWall(Node node, int[][] arr){
            int row = node.row, col = node.col;
            if(node.dir == 0) col--;
            if(node.dir == 1) row--;
            if(node.dir == 2) col++;
            if(node.dir == 3) row++;
            if(row>=0 && row<arr.length
                    && col>=0 && col< arr[0].length
                    && arr[row][col] == 1) return true;
            else if(row<0 || row>=arr.length // >=
                    || col<0 || col>=arr[0].length) return true;
            return false;
        }

        private void addOrthoDirs(Node node, int[][] arr){
            if(node.dir == 0 || node.dir == 2){
                addUp(node, arr);
                addDown(node, arr);
            }

            if(node.dir == 1 || node.dir == 3){
                addLeft(node, arr);
                addRight(node, arr);
            }
        }

        private void addUp(Node node, int [][] arr){
            int row = node.row-1, col = node.col;
            if(isSafe(row, col, 1, arr)) {
                q.addLast(new Node(row, col, 1));
                visited[row][col][1] = 1;
            }
        }

        private void addDown(Node node, int [][] arr){
            int row = node.row+1, col = node.col;
            if(isSafe(row, col, 3, arr)) { // node.dir might be diff
                q.addLast(new Node(row, col, 3));
                visited[row][col][3] = 1;
            }
        }

        private void addLeft(Node node, int [][] arr){
            int row = node.row, col = node.col-1;
            if(isSafe(row, col, 0, arr)) {
                q.addLast(new Node(row, col, 0));
                visited[row][col][0] = 1;
            }
        }

        private void addRight(Node node, int [][] arr){
            int row = node.row, col = node.col+1;
            if(isSafe(row, col, 2, arr)) {
                q.addLast(new Node(row, col, 2));
                visited[row][col][2] = 1;
            }
        }

        private void addSameDir(Node node, int[][] arr){
            int row = node.row, col = node.col;
            if(node.dir == 0) col--;
            if(node.dir == 1) row--;
            if(node.dir == 2) col++;
            if(node.dir == 3) row++;
            if(isSafe(row, col, node.dir, arr)) {
                q.addLast(new Node(row, col, node.dir));
                visited[row][col][node.dir] = 1;
            }
        }

        // add only if unvisited in the same dir
        private boolean isSafe(int row, int col, int dir, int[][] arr){
            if(row>=0 && row<arr.length
            && col>=0 && col<arr[0].length
            && visited[row][col][dir] == 0
            && arr[row][col] == 0) return true;
            return false;
        }

        class Node{
            int row, col, dir;
            Node(int r, int c, int d){
                this.row = r;
                this.col = c;
                this.dir = d;
            }

            public String toString(){
                return "{"+this.row+", "+this.col+", "+this.dir+"}";
            }
        }
    }

    /**
     * dfs recur w/ visited -> marking as 2 is equivalent to visited
     *
     */
    class Solution2_1 {
        int[] rows = {0, -1, 0, 1}, cols = {-1, 0, 1, 0};
        public int numIslands(char[][] grid) {
            int m = grid.length, n = grid[0].length, count = 0;
            for(int i =0; i<m; i++){
                for(int j = 0; j<n; j++){
                    if(grid[i][j] == '1') {
                        dfs(i, j, grid);
                        count++;
                    }
                }
            }
            return count;
        }

        private void dfs(int row, int col, char[][] arr){
            arr[row][col] = '2';
            for(int i=0; i<4; i++){
                int newRow = row + rows[i], newCol = col + cols[i];
                if(isSafe(newRow, newCol, arr)) dfs(newRow, newCol, arr);
            }
        }

        private boolean isSafe(int row, int col, char[][] arr){
            if(row>=0 && row<arr.length
                    && col>=0 && col<arr[0].length
                    && arr[row][col] == '1') return true;
            return false;
        }
    }
    /**
     *
     * https://leetcode.com/problems/minimum-knight-moves/description/
     * https://leetcode.com/problems/number-of-closed-islands/
     * https://leetcode.com/problems/pacific-atlantic-water-flow/
     */

    class Solution2_2 {
        /**
         if we start from the middle dia ([0, m-1], [m-1][0]) and go up and down?
         add to safe list
         if further nodes reach safe state they can reach the corresponding ocean
         (OR)
         start 2 dfs dfs1 for pacific, dfs2 for atlantic?
         2 dp state arrs?
         (OR)
         visited might nt be reqd coz of prev/only visiting lesser ht nodes?

         dp boolean or int (0, 1, 2) -> can hold visietd as well?

         1-> pacific, 2 -> atlantic; if dfs logic is same,
         maybe only target can be changed?

         isSafe -> will all be visited? when to backtrack after isSafe or
         outside of it?
         */
        private List<List<Integer>> res = new ArrayList<>();
        private int[] rows = {0, 1, 0, -1}, cols = {-1, 0, 1, 0};
        private int[][] dp1, dp2;
        private int[][] visited;
        private Set<String> set = new HashSet<>();
        public List<List<Integer>> pacificAtlantic(int[][] heights) {
            int m = heights.length, n = heights[0].length;
            dp1 = new int[m][n]; dp2 = new int[m][n];
            visited = new int[m][n];
            // Arrays.fill(dp1[0], true);
            // for(int i =0; i<m; i++) dp1[i][0] = true;

            // Arrays.fill(dp2[m-1], true);
            // for(int i =0; i<m; i++) dp2[i][n-1] = true;

            for(int i =0; i<m; i++){
                for(int j =0; j<n; j++){
                    // 1 -> pacific check for [row][col]
                    // [0] row or [0] col
                    if(dfs1(i, j, heights, Integer.MIN_VALUE, 0, 0, visited)){
                        set.add(i+","+j);
                    };
                }
            }

            for(int i =0; i<m; i++){
                for(int j =0; j<n; j++){
                    // 2 -> atlantic check for [row][col]
                    // [m-1] row or [n-1] col
                    if(dfs2(i, j, heights, Integer.MIN_VALUE, m-1, n-1, visited)){
                        if(set.contains(i+","+j)) res.add(new ArrayList<>(Arrays.asList(i, j)));
                    }
                }
                // System.out.println(i+", "+Arrays.toString(dp2[i]));
            }

            // for(int i =0; i<m; i++){
            //     for(int j =0; j<n; j++){
            //         if(dp1[i][j] == 2 && dp2[i][j] == 2) {
            //             res.add(Arrays.asList(i, j));
            //         }
            //     }
            // }

            return res;
        }

        private boolean dfs1(int row, int col, int[][] arr, int prev, int targetRow, int targetCol, int[][] visited){
            if(isSafe(row, col, arr, prev, visited)){
                // if(dp1[row][col] != 0) return dp1[row][col] == 2?true:false;
                if(row == targetRow || col == targetCol){
                    // dp1[row][col]  = 2;
                    return true;
                }
                visited[row][col] = 1;
                boolean val = false;
                for(int i =0; i<4; i++){
                    int newRow = row + rows[i], newCol = col + cols[i];
                    val |= dfs1(newRow, newCol, arr, arr[row][col], targetRow, targetCol, visited);
                }
                // if(val) dp1[row][col] = 2;
                // else dp1[row][col] = 1;
                visited[row][col] = 0;
                return val;
            }
            return false;
        }

        private boolean dfs2(int row, int col, int[][] arr, int prev, int targetRow, int targetCol, int[][] visited){
            if(isSafe(row, col, arr, prev, visited)){
                // if(dp2[row][col] != 0) return dp2[row][col] == 2?true:false;

                if(row == targetRow || col == targetCol){
                    // dp2[row][col]  = 2;
                    return true;
                }
                visited[row][col] = 1;
                System.out.println(row+", "+col);
                boolean val = false;
                for(int i =0; i<4; i++){
                    int newRow = row + rows[i], newCol = col + cols[i];
                    // if(isSafe())
                    val |= dfs2(newRow, newCol, arr, arr[row][col], targetRow, targetCol, visited);
                }
                System.out.println(val);
                // if(val) dp2[row][col] = 2;
                // else dp2[row][col] = 1;
                visited[row][col] = 0;
                return val;
            }
            return false;
        }

        private boolean isSafe(int row, int col, int[][] arr, int prev, int[][] visited){
            if(row>=0 && row<arr.length
                    && col>= 0 && col<arr[0].length
                    && arr[row][col] >= prev
                    && visited[row][col] == 0) return true;
            return false;
        }
    }


    public static void main(String[] args) {
        GraphMatrix gm = new GraphMatrix();
        int[][] maze = new int[][]{{0,0,1,0,0},{0,0,0,0,0},{0,0,0,1,0},
                {1,1,0,1,1}, {0,0,0,0,0}};

        int[] start = new int[]{0,4}, destination = new int[]{4,4};
        gm.new Solution1_4().hasPath(maze, start, destination);
    }
}


/**
 *
 *
 * snakes
 *
 * {3=2, 5=6, 6=9, 8=7}
 *
 * [-1, -1, -1, 2, 9, 6, -1, -1, 7, -1]
 */