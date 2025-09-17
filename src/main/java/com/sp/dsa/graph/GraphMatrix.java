package com.sp.dsa.graph;

import java.util.*;

public class GraphMatrix {

    /**
     * **Grid**
     *
     *
     * -- **time/path** (bfs with pruning)
     * Rotting oranges
     * Walls and Gates
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
}
