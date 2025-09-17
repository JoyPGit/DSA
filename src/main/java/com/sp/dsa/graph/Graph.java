package com.sp.dsa.graph;

import java.util.*;

public class Graph {
    /**
     Basic flow chart

     1 Directed
     -> with edge weights (adj matrix), else adj list
     -> shortest time/dist (BFS w/ pruning)
     -> order/loop (Topo, Dfs w/ coloring / Kahn's)
     -> traversal/count/path (Dfs)
     -> If no edge weights, prefer dfs

     2 Undirected
     -> loop/components (union find)
     -> bipartite (bfs)
     -> count (dfs)

     3 Grid
     -> no union find
     dfs (w/ visited)
     bfs

     ++++++++++++++++++++++++++++++++++++++++++++++++++++++
     Questions
     ++++++++++++++++++++++++++++++++++++++++++++++++++++++
     **Directed**

     -- **traversal/find count**
     Evaluate Division
     Keys and Rooms
     Clone Graph
     Find All Possible Recipes from Given Supplies

     -- **path**
     All Paths From Source to Target (dfs w/ backtracking)

     -- **order**
     Course schedule 1, 2
     Find Eventual Safe States (dfs with safe and unsafe sets/reverse Kahn)
     Reconstruct Itinerary
     Alien Dictionary
     All Ancestors of a Node in a Directed Acyclic Graph (reverse graph)

     -- **time/dist**
     Network Delay Time
     Cheapest Flights Within K Stops
     Time Needed to Inform All Employees

     +++++++++++++++++++++++++++++++++++++++++++++++++++

     **<b>Undirected</b>**

     -- **components**
     Number of Provinces
     Number of Connected Components in an Undirected Graph
     Number of Complete Components
     Redundant Connection
     Graph Valid Tree

     -- **loop**

     **exceptions**
     Is Graph Bipartite? (bfs)
     Minimum Cost to Reach City With Discounts (bfs with pruning)
     Critical Connections in a Network (dfs)

     +++++++++++++++++++++++++++++++++++++++++++++++++++
     **Grid**

     -- **order/traversal**
     All paths from source to target

     -- **time/path**
     Rotting oranges

     __________________________________________________
     **Tricky/Overlapping**

     Number of Operations to Make Network Connected
     Minimize Malware Spread
     Find Minimum Time to Reach Last Room II
     Detonate the Maximum Bombs
     Tree Diameter
     */

    /**
     * dfs template
     * 1.1 keys and rooms
     * https://leetcode.com/problems/keys-and-rooms/submissions/1772005473/
     */
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


    /**
     * 1.2 evaluate division
     *  bi-directional -> visited
     *  hashset -> check if double exists
     *  a/b; b/c -> a/c
     *
     */
    class Solution1_2 {
        private int index = 0;
        private double[] res;
        private Set<String> set = new HashSet<>();
        private Map<String, List<Node>> map = new HashMap<>();
        private double currAns = -1;
        private Set<String> visited = new HashSet<>();

        public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
            res = new double[queries.size()];

            // build a graph // 1
            for(int i =0; i<equations.size(); i++){
                String start = equations.get(i).get(0);
                String end = equations.get(i).get(1);
                set.add(start); set.add(end);
                List<Node> list = map.getOrDefault(start, new ArrayList<>());
                list.add(new Node(end, values[i]));
                map.put(start, list);

                // 2 bi-directional
                List<Node> list1 = map.getOrDefault(end, new ArrayList<>());
                list1.add(new Node(start, 1/values[i]));
                map.put(end, list1);
            }

            // System.out.println(map);
            // iterate over queries and find ans
            for(List<String> list : queries) {
                dfs(list.get(0), list.get(1), 1.0); // 3
                res[index++] = currAns;
                currAns = -1;
                visited.clear();

            }
            return res;
        }

        private void dfs(String start, String end, double prev){
            // System.out.println(start + ", "+prev);
            if(!set.contains(start)) return;
            if(start.equals(end)) { // 5 found
                // System.out.println("inside "+end + ", "+prev);
                currAns = prev;
                return;
            }
            if(visited.contains(start)) return; // 6 visited
            visited.add(start);
            if(!map.containsKey(start)) return;
            List<Node> list = map.get(start);
            for(Node node : list){
                dfs(node.str, end, node.val*prev); // 4 prev
            }
            return;
        }
    }

    class Node{
        String str; double val;
        Node(String str, double v){
            this.str = str;
            this.val = v;
        }

        @Override
        public String toString(){
            return "{"+this.str+", "+this.val+"}";
        }
    }

    /**
     * 2 all paths
     * dfs with backtracking
     * https://leetcode.com/problems/all-paths-from-source-to-target/submissions/1771745292
     */
    // visited?
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
        list.add(node);

        if(node == target){
            res.add(new ArrayList<>(list));
            list.remove(list.size()-1);
            return;
        }

        for(int i : map.get(node)) dfs(i, list, target);
        list.remove(list.size()-1);
    }

    /**
     * 3 course schedule (topo sort)
     *  dfs with coloring 0,1,2
     *  https://leetcode.com/problems/course-schedule-ii/submissions/1772729539/
     */
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

    /**
     * 3.2 all ancestors
     * REVERSE GRAPH -> eventual safe states
     * caching?
     * https://leetcode.com/problems/all-ancestors-of-a-node-in-a-directed-acyclic-graph/submissions/1772744216/
     */
    class Solution3_2 {
        private int[] color;
        private Map<Integer, List<Integer>> map;
        private List<List<Integer>> res;
        public List<List<Integer>> getAncestors(int n, int[][] edges) {
            this.color = new int[n];
            this.map = new HashMap<>();
            this.res = new ArrayList<>();

            for(int[] i : edges) {
                List<Integer> list = map.getOrDefault(i[1], new ArrayList<>());
                list.add(i[0]);
                map.put(i[1], list);
            }

            // topo sort
            List<Set<Integer>> sets = new ArrayList<>();
            Deque<Integer> q = new LinkedList<>();

            // caching? if parent's ancestors are computed, can child import those?
            for(int i =0; i<n; i++){
                List<Integer> list = new ArrayList<>();
                if(color[i] == 0) dfs(i, i, list);
                Collections.sort(list);
                res.add(list);
                Arrays.fill(color, 0);
            }
            return res;
        }

        private boolean dfs(int node, int parent, List<Integer> currList){
            if(color[node] == 2) return false;
            if(color[node] == 1) return true;
            color[node] = 1;
            if(node != parent) currList.add(node);
            List<Integer> list = map.getOrDefault(node, new ArrayList<>());
            for(int i : list){
                if(dfs(i, parent, currList)) return true;
            }

            color[node] = 2;
            return false;
        }
    }

    /**
     * eventual safe states
     * any node that leads to any loop or unsafe node is unsafe
     * maintain a safe and an unsafe set
     * topo sort, add safe and unsafe check as well
     * https://leetcode.com/problems/find-eventual-safe-states/submissions/1773101945/
     */
    class Solution3_3 {
        private int[] color;
        private Set<Integer> safeSet, unsafeSet;
        public List<Integer> eventualSafeNodes(int[][] graph) {
            int n = graph.length;
            List<Integer> res = new ArrayList<>();

            safeSet = new HashSet<>();
            unsafeSet = new HashSet<>();
            color = new int[n];
            for(int i =0; i<n; i++) {
                if(color[i] == 0){
                    // adding to res doesn't work as some nodemiht have been
                    // added to safe set during internal dfs
                    dfs(graph, i);
                }
            }

            for(int i =0; i<n; i++) if(safeSet.contains(i)) res.add(i);
            return res;
        }

        /**
         distinguish whether processed = safe or unsafe.
         So when color[node] == 2, you shouldn't just return false
         it could incorrectly treat unsafe nodes as safe
         (or at least not unsafe).
         */
        // return true for loop and unsafe
        private boolean dfs(int[][] graph, int node){
            if(color[node] == 2){
                if(safeSet.contains(node)) return false;
                if(unsafeSet.contains(node)) return true;
            }

            if(color[node] == 1) return true; // loop
            if(unsafeSet.contains(node)) return true; // 2

            // System.out.println(node);
            color[node] = 1;
            boolean safe = true;
            for(int i : graph[node]) {
                if(dfs(graph, i)) {
                    unsafeSet.add(i);
                    safe = false;
                    // break;
                }
            }
            // System.out.println(Arrays.toString(color));
            // System.out.println(safeSet);
            color[node] = 2;

            if(safe) safeSet.add(node);
            else unsafeSet.add(node);
            return !safe;
        }
    }

    /**
     * bfs with pruning for time/dist
     * network delay time
     * https://leetcode.com/problems/network-delay-time/submissions/1771608019/
     */
    class Solution4 {
        // bfs w/ pruning
        // visited/time arr -> checks both visited and stores time
        // contrast with Dijkstra
        // https://leetcode.com/problems/network-delay-time/submissions/1768306469/
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

            while(q.size() != 0){
                Node curr = q.removeFirst();
                visited[k] = 1;
                for(int i =1; i<=n; i++){
                    int edge = arr[curr.key][i];
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

    /**
     * cheapest flights
     * Dijkstra, check for stop count, use Node in pQ
     *
     * “Dijkstra’s does not work because it’s a greedy algorithm,
     * and cannot deal with the k‑stops constraint. …
     * We want to pay more now (cheap flights vs expensive) to preserve stops
     * for the future, where we savea more money.
     * Dijkstra will never pick that, because it always takes cheapest cost now.”
     *
     *
     * bfs with pruning; pruning -> reject if constraints fail
     *
     * https://leetcode.com/problems/cheapest-flights-within-k-stops/submissions/1769786510/
     */
    class Solution4_1 {
        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
            int[] dist = new int[n];
            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[src] = 0;

            int[][] arr = new int[n][n];
            for (int[] flight : flights) {
                arr[flight[0]][flight[1]] = flight[2];
            }

            Deque<Node> pq = new LinkedList<>();
            pq.add(new Node(src, 0, 0));

            while (pq.size() != 0) {
                Node curr = pq.remove();
                int d = curr.dist;
                for (int i = 0; i < n; i++) {
                    int edge = arr[curr.key][i];
                    if (edge == 0) continue;
                    // System.out.println(i+", "+dist[i]);
                    // System.out.println("d "+d +", edge "+edge);
                    if (dist[i] > d + edge && curr.stops <= k) {
                        dist[i] = d + edge;
                        // System.out.println("after "+i+", "+dist[i]);
                        pq.add(new Node(i, dist[i], curr.stops + 1));
                        // System.out.println(pq);
                        // System.out.println(Arrays.toString(dist));
                    }
                }
            }

            return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
        }

        // static class?
        class Node {
            int key, dist, stops;

            Node(int k, int d, int s) {
                this.key = k;
                this.dist = d;
                this.stops = s;
            }

            // toString to print Node object
            public String toString() {
                return "{" + this.key + ", " + this.dist + ", " + this.stops + "}";
            }
        }
    }

    /**
     * Undirected graphs, union find
     * 1. nodes in the same component will have the same group leader
     * 2. if edge exists, both nodes will be part of same component
     *
     * union find -> imp components parent[], rank[], union(), getParent()
     * check edge by edge
     */
    class Solution5 {

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

        /**
         [0,1,2,3] edge 1<->2
         node   parent
         1        1
         2        2
         [0,2,2,3] set parent[root] -> parent[1] = 2

         edge 1<->3
         1        2 (loop)
         3        3
         [0,2,3,3] set parent[root] -> parent[2] = 3
         edge 2<->3
         2        3
         3        3
         redundant edge found as parents same
         */
        private int findParent(int node){
            while(node != parent[node]){
                parent[node] = parent[parent[node]];
                node = parent[node];
            }
            return node;
        }
    }

    /**
     * Number of provinces
     * https://leetcode.com/problems/number-of-provinces/submissions/1772529289/
     */
    class Solution5_2 {
        private int count;
        private int[] parent;
        public int findCircleNum(int[][] isConnected) {
            int n = isConnected.length;
            this.count = n;
            this.parent = new int[n+1];

            for(int i = 1; i<=n; i++) parent[i] = i;

            for(int i =0; i<n; i++){
                for(int j= 0; j<n; j++){
                    if(j==i || isConnected[i][j] == 0) continue;
                    // edge from i+1 -> j+1
                    union(i+1, j+1);
                }
                // System.out.println(map);
            }

            return count;
        }

        private boolean union(int a, int b){
            int root1 = findParent(a), root2 = findParent(b);
            if(root1 == root2) return false;
            else {
                parent[root1] = root2;
                count--;
            }
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

    /**
     * graph valid tree, union find
     * a tree doesn't have loops, so it should be a single component
     * reduce count after every union
     * https://leetcode.com/problems/graph-valid-tree/submissions/1773857394/
     */
    class Solution5_3 {
        private int count = 0;
        private int[] parent;
        public boolean validTree(int n, int[][] edges) {
            this.parent = new int[n];
            count = n;
            for(int i =0; i<n; i++) parent[i] = i;

            for(int[] edge : edges){
                if(!union(edge[0], edge[1])) return false;
            }
            // a single component
            if(count != 1) return false;
            return true;
        }

        private boolean union(int a, int b){
            int root1 = findParent(a), root2 = findParent(b);
            if(root1 == root2) return false;
            else parent[root1] = root2;
            // after every union reduce count
            count--;
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

    class Solution5_4 {
        /**
         Why visited won't work
         But visited.size() only counts the number of unique nodes that
         appeared in any connection — not the number of connected components.
         This doesn't reflect how many components are left to connect.

         Find the no of components; to connect m comps, we need m-1 edges.
         If reqd > unreqd, we have a solution

         https://leetcode.com/problems/number-of-operations-to-make-network-connected/submissions/1773950570/
         */
        int[] parent;
        private int reqdEdges = 0, unReqdEdges = 0, count;

        public int makeConnected(int n, int[][] connections) {
            this.parent = new int[n];
            this.count = n;

            for(int i =0; i<n; i++) parent[i] = i;

            for(int[] edge : connections){
                // unnecessary edge
                if(!union(edge[0], edge[1])) {
                    // System.out.println("unnecessary "+edge[0]+", "+edge[1]);
                    unReqdEdges++;
                }
            }
            // System.out.println(count+", "+unnecessaryEdges);
            reqdEdges = count-1;
            return (reqdEdges > unReqdEdges)?-1:reqdEdges;
        }

        private boolean union(int a, int b){
            int root1 = findParent(a), root2 = findParent(b);

            if(root1 == root2) return false;
            else parent[root1] = root2;
            count--;
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
}
