package com.dsa.graph;

import java.util.*;

public class Backtracking {
    /**
     * for grid use bfs/dfs
     *
     * -- [shortest time/dist] bfs
     *
     * -- [order] dfs
     *
     *  make a change
     *  recur
     *  revert the change
     *
     */


    /**
     * bfs
     * word ladder
     * https://leetcode.com/problems/word-ladder/submissions/1774883557/
     *
     */

    class Solution1_1 {
        private Set<String> wordSet = new HashSet<>(), visited = new HashSet<>();
        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            int count = 0;
            for(String word : wordList) wordSet.add(word);
            if(!wordSet.contains(endWord)) return 0;
            Deque<String> q = new LinkedList<>();
            q.addLast(beginWord);

            while(q.size() != 0){
                int size = q.size();
                count++;
                for(int s= 0; s<size; s++){
                    String curr = q.removeFirst();
                    char[] currArray = curr.toCharArray();
                    for(int i =0; i<currArray.length; i++){
                        char temp = currArray[i];
                        // all 26 chars except existing char
                        for(char c = 'a'; c<='z'; c++){
                            if(temp == c) continue;
                            currArray[i] = c;
                            String newStr = new String(currArray);
                            if(newStr.equals(endWord)) return count+1;

                            if(isSafe(newStr, wordSet, visited)){
                                q.addLast(newStr);
                                visited.add(newStr);
                            }
                            currArray[i] = temp;
                        }
                    }
                }
            }
            return 0;
        }

        private boolean isSafe(String str, Set<String> wordSet, Set<String> visited){
            if(wordSet.contains(str) && !visited.contains(str)) return true;
            return false;
        }
    }

    /**
     * dfs
     * https://leetcode.com/problems/all-paths-from-source-to-target/description/
     */
    class Solution2_1 {
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
