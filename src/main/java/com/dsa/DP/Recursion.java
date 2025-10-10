package com.dsa.DP;

import java.util.Arrays;

public class Recursion {
    /**
     * 1 top-down
     * 2 bottom-up
     * add recursion logic here, then optimize with memoization
     * add dp logic in the other class
     *
     * https://leetcode.com/problems/stone-game
     * Memoization?
     * https://leetcode.com/problems/partition-array-into-two-arrays-to-minimize-sum-difference/submissions/1788244998/
     * https://leetcode.com/problems/longest-common-subsequence/submissions/1788882795/
     *
     * https://leetcode.com/problems/nested-list-weight-sum-ii/submissions/1789260440/?envType=problem-list-v2&envId=xn3wslgg
     */
    /**
     * 2 keys
     * LCS, word edit distance
     * coin change
     */

    /**
     * top-down
     * https://leetcode.com/problems/fibonacci-number/
     */
    class Solution1_1 {
        private int[] dp;
        public int fib(int n) {
            if(n == 0 || n == 1) return n;
            dp = new int[n+1];
            // return
            recur(n);
            return dp[n];
        }

        /**
         this doesn't calculate sub-problems until it reaches a base case
         but bottom up builds on the base case itself, so sub-problems are
         solved earlier
                        bottom-up       top-down
         Space Usage	O(n)    	    O(n) for memo +
                                        O(n) for recursion stack

         Performance	faster          slower (recursion)
                        (no recursion
                        overhead)
         */
        private int recur(int n){
            if(n == 0 || n == 1) return n;
            if(dp[n] != 0) return dp[n];
            dp[n] = recur(n-1) + recur(n-2);
            return dp[n];
        }
    }

    /**
     * top down
     */
    class Solution1_2 {
        public boolean canJump(int[] nums) {
            int n = nums.length;
            boolean[] dp = new boolean[n];
            dp[n - 1] = true; // last index can always reach itself

            for (int i = n - 2; i >= 0; i--) {
                // find max index that can be reached
                int furthestJump = Math.min(i + nums[i], n - 1);
                // check for linkage, if index can be reached, break
                // as we are solving a sub-problem
                for (int j = i + 1; j <= furthestJump; j++) {
                    if (dp[j]) {
                        dp[i] = true;
                        break;
                    }
                }
            }

            return dp[0]; // can we jump to the end from index 0?
        }
    }




    /**
     * bottom up
     * https://leetcode.com/problems/fibonacci-number
     *
     */
    class Solution2_1 {
        public int fib(int n) {
            if(n == 0 || n == 1) return n;
            int[] dp = new int[n+1];
            dp[0] = 0; dp[1] = 1;
            for(int i =2; i<=n; i++){
                dp[i] = dp[i-1] + dp[i-2];
            }
            return dp[n];
        }
    }

    /**
     * fix this
     * dp[index] holds the best value from n-1 till this i
     * (from right to i)
     * while currCount is being passed from left to right(bottom up)
     * count can be tracked via [1 + recur()]
     *
     * class Solution {
     *     private int count = Integer.MAX_VALUE;
     *     private int[] dp;
     *     public int jump(int[] nums) {
     *         int n = nums.length;
     *         this.dp = new int[n];
     *         Arrays.fill(dp, Integer.MAX_VALUE);
     *         recur(0, nums, 0);
     *         System.out.println(Arrays.toString(dp));
     *         return dp[n-1];
     *     }
     *
     *     private int recur(int index, int[] arr, int currCount){
     *         System.out.println(index+", "+currCount);
     *         if(index > arr.length-1) return Integer.MAX_VALUE; // ???
     *
     *         if(dp[index] != Integer.MAX_VALUE) return dp[index];
     *
     *         if(index == arr.length-1){
     *             return currCount;
     *         }
     *
     *         int val = Integer.MAX_VALUE;
     *         for(int i =1; i<=arr[index]; i++){
     *             val = Math.min(val, recur(index+i, arr, currCount+1));
     *         }
     *         dp[index] = Math.min(dp[index], val);
     *         return dp[index];
     *     }
     * }
     *
     * https://leetcode.com/problems/jump-game-ii/submissions/1781968280/
     */
    class Solution2_2 {
        private int count = Integer.MAX_VALUE;
        private int[] dp;
        public int jump(int[] nums) {
            int n = nums.length;
            if(n == 1) return 0;
            this.dp = new int[n];
            Arrays.fill(dp, -1);
            recur(0, nums);
            // System.out.println(Arrays.toString(dp));
            return dp[0];
        }

        private int recur(int index, int[] arr){
            // System.out.println(index+", "+currCount);
            // System.out.println(Arrays.toString(dp));
            if(index > arr.length-1) return Integer.MAX_VALUE/2; // ???

            // why is dp not hainvg the best val?
            if(dp[index] != -1) return dp[index];

            if(index == arr.length-1){
                // System.out.println("target, "+currCount);
                return 0;
            }

            int val = Integer.MAX_VALUE/2;
            for(int i =1; i<=arr[index]; i++){
                val = Math.min(val, 1+recur(index+i, arr));
            }
            dp[index] = val;
            return dp[index];
        }
    }

    public static void main(String[] args) {
        new Recursion().new Solution1_2().canJump(new int[]{2,3,1,1,4});
    }
}
