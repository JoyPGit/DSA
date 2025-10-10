package com.dsa;

public class BinarySearchPrac {
    /**
     *
     * IMP : https://leetcode.com/problems/split-array-largest-sum/submissions/1790185101/
     *
     * https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/description/?envType=company&envId=tcs&favoriteSlug=tcs-three-months
     * https://leetcode.com/problems/search-in-rotated-sorted-array/description/?envType=company&envId=tcs&favoriteSlug=tcs-three-months
     *
     * use same template for 2 ques
     *     if lesser el, go right
     *     if larger or same el, update res go left
     *
     * remember boundary conditions and neighbors
     * https://leetcode.com/problems/find-peak-element/submissions/1791446026/?envType=study-plan-v2&envId=top-interview-150
     *
     * https://leetcode.com/problems/search-insert-position/submissions/1791154222/?envType=study-plan-v2&envId=top-interview-150
     * https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/description
     *
     * find min
     * https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/submissions/1791488490/
     * find peak
     * https://leetcode.com/problems/find-peak-element/submissions/1791468462/
     */

    /**
     *
     */
    // https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/description/?envType=company&envId=tcs&favoriteSlug=tcs-three-months
    class Solution1_2 {
        public int[] searchRange(int[] nums, int target) {
            int n = nums.length;
            int[] res = new int[]{-1, -1};
            res[0] = findFirst(nums, target);
            if(res[0] != -1) res[1] = findLast(nums, target);
            return res;
        }

        private int findFirst(int[] arr, int target){
            int lo = 0, hi = arr.length-1, res = -1;
            while(lo<=hi){
                int mid = lo + (hi-lo)/2;
                if(arr[mid] == target) {
                    res = mid;
                    hi = mid-1;
                }
                else if(arr[mid] > target) hi = mid-1;
                else lo = mid+1;
            }
            return res;
        }

        private int findLast(int[] arr, int target){
            int lo = 0, hi = arr.length-1, res = -1;
            while(lo<=hi){
                int mid = lo + (hi-lo)/2;
                // System.out.println(mid+", "+lo+", "+hi);

                if(arr[mid] == target) {
                    res = mid;
                    lo = mid+1;
                }
                else if(arr[mid] > target) hi = mid-1;
                else lo = mid+1;
            }
            return res;
        }
    }

    /**
     * rotated sorted array
     * either search or find min
     */
    // https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/submissions/1791488490
    class Solution1_3 {
        /**
         peak -> move towards sorted + boundary condns
         min -> move towards unsorted + boundary condns
         target/search -> move towards sorted but use target for sorting check

         */
        public int findMin(int[] nums) {
            int n = nums.length, lo = 0, hi = n-1;
            if(n == 1) return nums[0];

            while(lo<=hi){
                int mid = lo + (hi-lo)/2;
                if(mid-1>=0 && mid+1<n){
                    // min check
                    if(nums[mid] < nums[mid-1] && nums[mid] < nums[mid+1])
                        return nums[mid];
                    else if(nums[mid] <= nums[hi]) hi = mid-1;
                    else lo = mid+1;
                }
                else{
                    if(mid == 0){
                        if(nums[mid] < nums[mid+1]) return nums[mid];
                        else return nums[mid+1];
                    }
                    if(mid == n-1){
                        if(nums[mid] < nums[mid-1]) return nums[mid];
                        else return nums[mid-1];
                    }
                }
            }
            return 0;
        }
    }

    // https://leetcode.com/problems/search-a-2d-matrix/submissions/1791490448/?envType
    // =study-plan-v2&envId=top-interview-150
    class Solution1_4 {
        /**
         * lo = 0, hi = m*n-1, row = mid/n, col = mid%n
         * @param matrix
         * @param target
         * @return
         */
        public boolean searchMatrix(int[][] matrix, int target) {
            int m = matrix.length, n = matrix[0].length;
            int lo = 0, hi = m*n-1;

            while(lo<=hi) {
                int mid = lo + (hi-lo)/2;
                int row = mid/n, col = mid%n;

                if(matrix[row][col] > target) hi = mid-1;
                else if(matrix[row][col] < target) lo = mid+1;
                else if(matrix[row][col] == target) return true;
            }
            return false;
        }
    }
}
