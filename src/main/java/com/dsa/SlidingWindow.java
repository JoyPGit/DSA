package com.dsa;

import java.util.*;

public class SlidingWindow {
    /**
     * FOLLOWUP, use count of all chars??
     * https://leetcode.com/problems/find-all-anagrams-in-a-string/submissions/1797705950/
     * https://leetcode.com/problems/minimum-window-substring/description/
     *
     * https://leetcode.com/problems/sliding-window-maximum/submissions/1622212361/
     *
     * swaps
     * https://leetcode.com/problems/minimum-swaps-to-group-all-1s-together/submissions/1801036680/
     * https://leetcode.com/problems/minimum-swaps-to-group-all-1s-together-ii/submissions/1801218261/
     */


    /**
     * sliding window template
     * add and then shrink
     * https://leetcode.com/problems/longest-substring-without-repeating-characters/submissions/1802585128/
     *
     * similar
     */
    class Solution1_1 {
        /** sliding window add and then shrink */
        public int lengthOfLongestSubstring(String s) {
            int n = s.length(), max = 0, left =0;
            int[] arr = new int[128];
            for(int i =0; i<n; i++){
                char curr = s.charAt(i);
                arr[curr]++;

                // shrink till arr[curr] == 1, no duplicate
                while(arr[curr] > 1){
                    arr[s.charAt(left)]--;
                    left++;
                }
                max = Math.max(max, i-left+1);
            }
            return max;
        }
    }

    /**
     * If need to find substring with distinct chars ot at max k chars, size can be
     * counted via map, use map instead of arr[128]
     * https://leetcode.com/problems/longest-substring-with-at-most-two-distinct-characters/submissions/1802645593/
     *
     * no need to shrink via while as size is fixed at k
     * https://leetcode.com/problems/maximum-sum-of-distinct-subarrays-with-length-k/submissions/1802649540/
     *
     *
     * similar problems
     * https://leetcode.com/problems/longest-subarray-of-1s-after-deleting-one-element/solutions/708112/java-c-python-sliding-window-at-most-one-0/
     */

    /**
     * template for matching character count in word
     * arr[c] > 0 count++;
     * arr[c]--;
     *
     * arr[left]++
     * if(arr[left] > 0) count++;
     * add if count == 0
     */
    class Solution2_1 {
        /**
         use count variable
         if (base[c-'a'] != 0) -> exists
         if left is non 0 count++; why?
         it was present in p, was decremented and now after increment
         its value got > 0
         had it not been present, it's value would still be -ve

         Used arr[128]
         https://leetcode.com/problems/find-all-anagrams-in-a-string/submissions/1802468480/

         // https://leetcode.com/problems/find-all-anagrams-in-a-string/submissions/1802417452/

         */
        public List<Integer> findAnagrams(String s, String p) {
            int m = p.length(), n = s.length(), k = m, left = 0;
            int count = m;
            int[] base = new int[26], arr = new int[26];
            List<Integer> res = new ArrayList<>();
            if(n<m) return res;

            for(char c : p.toCharArray()) base[c-'a']++;

            for(int i =0; i<k; i++){
                char curr = s.charAt(i);
                if(base[curr - 'a'] != 0) count--; // exists
                base[curr - 'a']--;
            }
            if(count == 0) res.add(0);
            // if(isMatch(base, arr)) res.add(0);

            for(int i = k; i<n; i++){
                char curr = s.charAt(i), l = s.charAt(left);
                // same as above, if exists decrement
                if(base[curr - 'a'] != 0) count--;
                base[curr - 'a']--;
                base[l-'a']++;
                // non zero left
                if(base[l-'a'] != 0) count++;
                // arr[s.charAt(left)-'a']--;
                left++;
                if(count == 0) res.add(left);
                // if(isMatch(base, arr)) res.add(left);
            }

            return res;
        }

        private boolean isMatch(int[] a, int[] b){
            for(int i =0; i<a.length; i++){
                if(a[i] != b[i]) return false;
            }
            return true;
        }

        class Solution1_2 {
            /**
             arr[128] will hold count, no map needed
             if count is 0, shrink
             if present then while incrementing (processing left) the val must be > 0
             had char not been present in t, the value would be 0
             [c-'a']--; then [charAt(left) -'a']++ == net zero

             use arr[126] and arr[c]; not arr[c-'a']
             if exists, decrement, for left if exists increment
             ADD AND THEN SHRINK
             */
            public String minWindow(String s, String t) {
                int n = s.length(), m = t.length(), left = 0, count = m, k = m;
                // s = s.toLowerCase(); t = t.toLowerCase();
                int min = Integer.MAX_VALUE, start = 0;
                if(n<m) return "";
                int[] arr = new int[128];
                for(char c : t.toCharArray()) arr[c]++;

                for(int i =0; i<n; i++){
                    char curr = s.charAt(i);
                    if(arr[curr] > 0) count--;
                    arr[curr]--;

                    while(count == 0 && left<n) {
                        if(i-left+1 < min) {
                            min = i-left+1;
                            start = left;
                        }

                        char l = s.charAt(left);
                        arr[l]++;
                        if(arr[l] > 0) count++;
                        left++;
                    }
                }

                if(min == Integer.MAX_VALUE) return "";
                return s.substring(start, start + min);
            }
        }
    }

    /**
     *  SWAP TO GROUP 1s
     *
     * 1 WHENEVER A QUES OF MIN SWAPS AND THE DATA SET IS CONTIGUOUS
     * USE SLIDING WINDOW
     * 2 HERE WE FIND THE NO OF 1s, THIS DETERMINES THE SIZE OF THE WINDOW
     * 3 NOW COUNT NO OF 0s, THIS IS THE MIN NO OF SWAPS
     * 4 KEEP TRACK OF MIN NO OF 0s
     * 5 IF OUTGOING INDEX IS ZERO zero--; IF INCOMING IS ZERO zero++
      * 6 IMP BOUNDARY CONDNS : j = count+1 till n-count
     *
     *
     * https://leetcode.com/discuss/interview-question/414660/
     *
     * MICROSOFT
     * https://www.geeksforgeeks.org/minimum-swaps-required-group-1s-together/
     * https://www.youtube.com/watch?v=VXi_-2CmitM
     *
     * https://leetcode.com/discuss/interview-question/344778/
     * find-the-minimum-number-of-swaps-required-such-that-all-the-0s-and-all-the-1s
     * -are-together
     */

    /**
     * FLIP/SWAP
     *
     * Minimum Swaps to Group All 1’s Together
     * You want to group all 1’s together by swapping.
     * https://leetcode.com/problems/minimum-swaps-to-group-all-1s-together-ii/submissions/1801218261/
     *
     *
     * Example “flip-based” problems
     *
     * LeetCode 424. Longest Repeating Character Replacement
     * Given string s and integer k, you can replace up to k characters to make all characters in the
     * substring the same. Return the length of the longest substring you can get.
     * → Similar idea: you maintain counts of characters in window, and check if
     * (window size − max frequency char) <= k. If more replacements needed than k, shrink the window.

     * LeetCode 487. Max Consecutive Ones II
     * Flip at most one 0. Essentially k = 1 case.
     *
     * LeetCode 1004. Max Consecutive Ones III
     * (Exactly like your original problem) Given a binary array nums and integer k, return the maximum
     * number of consecutive 1s in the array if you can flip at most k 0s to 1s.
     * → Use sliding window: expand right, count zeroes, shrink when zeroes > k.
     *
     *
     * Approach template (“flip / replace up to k” sliding window)
     *
     * Here’s a generic approach you can adapt:
     *
     * Use two pointers, left and right, expanding the window by incrementing right.
     * Maintain some metric (e.g. count of “bad elements”, or count map,
     * or “how many flips needed”) inside the window.
     * As long as the metric is valid (i.e. ≤ k), update your answer (max window size).
     *
     * Once the metric becomes invalid (> k), shrink from left (increment left) while updating your metric,
     * until the window becomes valid again.
     * Continue expanding from right.
     *
     * For example, in your “flip zeroes to ones” problem:
     *
     * Metric = number of zeroes in current window.
     * Expand right; if nums[right] == 0, increment zero count.
     * While zeroes > k, shrink from left: if nums[left] == 0, decrement zero count; then left++.
     *
     * Update maxLen = max(maxLen, right – left + 1).
     *
     * In “character replacement” problem:
     * Metric = window size − (max count of a single character in window) → how many replacements needed.
     * Expand right, update char count, update max frequency in window.
     * While replacements needed > k, shrink from left (reduce counts), move left.
     *
     * Update answer.
     *
     * In practice, almost all textbooks, tutorials, and references use
     * <b>the expand then shrink if needed pattern.</b>
     * USe this template
     * while right < n:
     *     add nums[right]
     *     while window invalid:
     *         remove nums[left]
     *         left++
     *     update answer
     *     right++
     */

    class Solution2_2 {
        /**
         * template
         * use zeroCount
         * add and then shrink
         *
         * same for consecutive-ii
         * https://leetcode.com/problems/max-consecutive-ones-ii/submissions/1802492124/
         *
         * https://leetcode.com/problems/max-consecutive-ones-iii/submissions/1802489030
         *
         */
        public int longestOnes(int[] nums, int k) {
            int n = nums.length;
            int left = 0;
            int zeroCount = 0;
            int maxLen = 0;

            for (int right = 0; right < n; right++) {
                if (nums[right] == 0) {
                    zeroCount++;
                }

                // If too many zeroes, shrink from left
                while (zeroCount > k) {
                    if (nums[left] == 0) {
                        zeroCount--;
                    }
                    left++;
                }

                // Now [left..right] is valid: at most k zeroes
                maxLen = Math.max(maxLen, right - left + 1);
            }
            return maxLen;
        }
    }

    /**
     *  k distinct type
     *  use map size to check distinct
     *
     * https://leetcode.com/problems/maximum-sum-of-distinct-subarrays-with-length-k/submissions/1802649540/
     *
     * https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/submissions/1801018795/
     *
     * LeetCode 159. Longest Substring with At Most Two Distinct Characters
     * This is not exactly “flip”, but similar sliding window logic. Maintain window so
     * that you have at most 2 distinct chars; when more, shrink window.
     *
     */

}
