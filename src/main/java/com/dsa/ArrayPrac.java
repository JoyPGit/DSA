package com.dsa;

public class ArrayPrac {
    /**
     * Always use index instead of map arr[i]++;
     * https://leetcode.com/problems/relative-sort-array/description/
     *
     * https://leetcode.com/problems/shortest-distance-to-a-character/description/
     *  tricky find dist b/w indexes
     *  https://leetcode.com/problems/shortest-distance-to-a-character/submissions/1796629783/
     *
     * https://leetcode.com/problems/merge-intervals/submissions/1794033624/
     *
     * tricky with followup
     * https://leetcode.com/problems/string-compression/submissions/1796522470/
     *
     */



    /**
     * [10, 5, 10] -> 0; [10, 10, 10] -> -1
     *
     * how to handle duplicates ? i!=first
     * update only if curr el is not equal to first
     *
     */
    // https://www.geeksforgeeks.org/find-second-largest-element-array/
    int secondLargest(int[] arr){
        int first = -1; int second = -1;
        for(int i : arr){
            if(i > first) {
                first = i;
                second = first;
            }
            else if(i > second && i!= first) second = i;
        }
        return second;
    }

    /**
     * https://leetcode.com/problems/find-the-town-judge/submissions/1796755998/
     */

     
}
