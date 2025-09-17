package com.sp.dsa.Backtracking;

import java.util.*;

public class Matrix {
    /**
     *
     * https://leetcode.com/problems/all-paths-from-source-to-target/description/
     */
    class Solution1_1 {
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
    }
}
