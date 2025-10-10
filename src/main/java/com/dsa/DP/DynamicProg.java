package com.dsa.DP;

public class DynamicProg {
    /**
     * https://leetcode.com/discuss/post/3045604/dp-problems-list-topic-wise-questions-by-hkdi/
     *
     * There are three standard styles:
     *
     * 1 Top-down recursion with memoization
     * base case of 0 or 1
     *
     * 2 Bottom-up iteration, simple for loop 0->n-1 (more optimal)
     * STANDARD DP APPROACH
     *
     * 3 Bottom-up recursion (less common, for educational purposes)
     * (suboptimal, but very helpful for visualization of bottom up dp)
     *
     *
     * Usually, solving and fully understanding a dynamic programming problem is a 4 step process:
     *
     * 1 Start with the recursive backtracking solution
     * 2 Optimize by using a memoization table (top-down2 dynamic programming)
     * 3 Remove the need for recursion (bottom-up dynamic programming)
     * 4 Apply final tricks to reduce the time / memory complexity
     *
     *
     * LIS
     * https://leetcode.com/problems/longest-increasing-subsequence/submissions/1788855329/
     * https://www.geeksforgeeks.org/problems/maximum-sum-increasing-subsequence4749/1
     *
     * Knapsack
     *
     * https://leetcode.com/problems/partition-equal-subset-sum/submissions/1788209880/
     * 
     * Climbing stairs
     * https://leetcode.com/problems/climbing-stairs/submissions/1793183251/
     * similar decode ways
     * 
     * House Robber
     * similar 
     * https://leetcode.com/problems/delete-and-earn/submissions/1793117750/
     *
     * Bursting balloon
     * https://leetcode.com/problems/burst-balloons/submissions/1790748229/
     */

    /**
     * basically a for loop starting from 0
     */
    class BottomUpIteration {
        public int rob(int[] nums) {
            int n = nums.length;

            if (n == 0) return 0;
            if (n == 1) return nums[0];

            int[] dp = new int[n];

            // Base cases
            dp[0] = nums[0];
            dp[1] = Math.max(nums[0], nums[1]);

            // Fill dp array
            for (int i = 2; i < n; i++) {
                dp[i] = Math.max(dp[i - 1], nums[i] + dp[i - 2]);
            }

            return dp[n - 1];
        }
    }


    /**
     * basically recur without memo, same as above
     */
    class BottomUpRecur {
        public int rob(int[] nums) {
            int n = nums.length;
            if (n == 0) return 0;
            if (n == 1) return nums[0];

            // Base values
            int[] dp = new int[n];
            dp[0] = nums[0];
            dp[1] = Math.max(nums[0], nums[1]);

            // Start the recursion from index 2
            fillDP(nums, dp, 2);
            return dp[n - 1];
        }

        private void fillDP(int[] nums, int[] dp, int index) {
            if (index >= nums.length) return;

            dp[index] = Math.max(dp[index - 1], dp[index - 2] + nums[index]);

            // Recursive call to fill the next index
            fillDP(nums, dp, index + 1);
        }
    }


    class Solution1_1 {
        /**
         * 1 recur
         * 2 recur with memo
         * method signature changes to int from void
         * count is unnecessary now
         3 memo -> dp

         https://leetcode.com/problems/climbing-stairs/description/
         */
        private int[] dp;
        public int climbStairs(int n) {
            dp = new int[n+1];
            recur(0, n);
            return dp[0];
        }

        // index should match, >n is not considered

        private int recur(int index, int n){
            if(index > n) return 0;
            // System.out.println(index);

            if(dp[index] != 0) return dp[index];

            if(index == n) return 1;

            int val = recur(index+1, n);
            // starting from 1, and taking 2 steps
            // there is only one way of reaching 3
            val += recur(index+2, n);
            dp[index] = val;
            // System.out.println("after "+index +", "+Arrays.toString(dp));
            return val;
        }
    }


    /**
     * https://leetcode.com/problems/longest-common-subsequence/submissions/1787232091/
     *
     * Knapsack dp[i][j] =
     *
     * Recurrence : recur(index, arr, curr + arr[index], target)
     *                 + recur(index+1, arr, curr, target);
     *                 note : if(curr == target) return 1;
     *
     * Unbounded [reuse]         : max(dp[i−1][j], dp[i][j−w[i-1]]+ v[i-1])
     * Bounded or 0/1 [no reuse] : max(dp[i−1][j], dp[i-1][j−w[i-1]]+ v[i-1])
     *
     * dp[i][j] = Math.max(
     *                 dp[i - 1][j],                            // Don't take
     *                 dp[i - 1][j - weights[i - 1]] + values[i - 1] // Take
     *             );
     *
     *
     * 3 templates : min no, no of ways, reuse or not
     * reuse
     * no reuse recurrence:
     *
     * mostly coin change 2 is used,
     * https://leetcode.com/problems/coin-change/submissions/1788014113/
     * https://leetcode.com/problems/coin-change-ii/submissions/1788045779/
     * https://leetcode.com/problems/partition-equal-subset-sum/submissions/1788209880/
     *
     * target sum same as partition equal subset sum (no reuse)
     * https://leetcode.com/problems/partition-equal-subset-sum/submissions/1788209880/
     *
     * String
     * without l -> min edit dist, wildcard matching
     * of type l -> ????
     *
     *
     *
     * https://www.geeksforgeeks.org/problems/longest-common-substring1452/1
     *
     *
     * LCS
     * https://leetcode.com/problems/delete-operation-for-two-strings/submissions/1786917537/
     *
     * https://leetcode.com/problems/longest-common-subsequence/submissions/1788884941/
     * https://leetcode.com/problems/longest-palindromic-subsequence/submissions/1789491174/
     * https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/
     *
     * https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/
     *
     * longest palindromic subsequence
     * w/o l -> reverse and find lcs, dp[i][j] = dp[i-1][j-1]+1;
     * using l -> dp[i][j] = dp[i+1][j-1]+2; else max(dp[i][j-1], dp[i+1][j])
     *
     * longest palindromic substring needs l?? YES
     *
     * use similar template for
     * longest common                  -> [i-1][j-1]+1
     * and separate for longest palindrome  substring
     * -> use len [i+1][j-1]+2; [i][j-1],
     * [i+1][j]
     *
     * https://leetcode.com/problems/minimum-falling-path-sum/submissions/1789845197/
     *
     */

    /**
     * MCM
     *
     * What is “Painter’s Partition” / “Painters Partition” problem
     *
     * You have an array boards[], each board has some “length” or “cost.”
     * You have k painters.
     * Each painter must paint a contiguous slice of boards (you can't skip).
     * All painters can work in parallel.
     * The time taken by a painter = sum of lengths assigned to him.
     * The objective is to minimize the maximum time any painter spends.
     * In other words, partition the array into k contiguous segments so that
     * the maximum segment sum is minimized.
     *
     *
     * One common method is binary search + greedy check, or DP (e.g. DP over partitions)
     *
     */
}


