package com.dsa;

import java.util.*;

public class HashMapPractice {
    /**
     * FOLLOW UP
     * https://leetcode.com/problems/stock-price-fluctuation/submissions/1794157763/
     * https://leetcode.com/problems/brick-wall/submissions/1466571011/
     *
     *
     * https://leetcode.com/problems/first-unique-character-in-a-string/submissions/1796689338/
     * https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/submissions/1790212809/

     */

    class StockPrice {
        /**
         1 if rare updates -> iterate list and find max/min
         2 if regular updates -> 2 maps
         */
        private int currTime, currPrice;
        private int max, min;
        private java.util.HashMap<Integer, Integer> timePriceMap;
        private java.util.HashMap<Integer, Integer> priceCount;
        private TreeSet<Integer> set; // quick max/min
        public StockPrice() {
            this.timePriceMap = new HashMap<>();
            this.priceCount = new HashMap<>();
            this.set = new TreeSet<>();
            this.currTime = 0;
            this.currPrice = 0;
            this.max = Integer.MIN_VALUE;
            this.min = Integer.MAX_VALUE;
        }

        /**
         * irrespective of ol/new timestamp, we update
         * for old, we adjust count and remove from set (max/min)
         * timePriceMap -> [1,10] -> [1,3]
         * countMap     -> [10, 1]
         * @param timestamp
         * @param price
         */
        public void update(int timestamp, int price) {
            // if old value
            if(timePriceMap.containsKey(timestamp)) {
                int oldPrice = timePriceMap.get(timestamp);
                priceCount.put(oldPrice, priceCount.get(oldPrice)-1);
                if(priceCount.get(oldPrice) == 0) {
                    priceCount.remove(oldPrice);
                    set.remove(oldPrice); // remove from treeset as well
                }
            }
            // for both old/new
            timePriceMap.put(timestamp, price);
            priceCount.put(price, priceCount.getOrDefault(price, 0)+1);
            set.add(price);

            if(timestamp > currTime) {
                currTime = timestamp;
                currPrice = price;
            }
        }

        public int current() {
            return this.currPrice;
        }

        public int maximum() {
            return this.set.last();
        }

        public int minimum() {
            return this.set.first();
        }
        /**
         * Your StockPrice object will be instantiated and called as such:
         * StockPrice obj = new StockPrice();
         * obj.update(timestamp,price);
         * int param_2 = obj.current();
         * int param_3 = obj.maximum();
         * int param_4 = obj.minimum();
         */
    }

    public static void main(String[] args) {
        HashMapPractice.StockPrice obj = new HashMapPractice().new StockPrice();
        obj.update(1, 10);
        obj.update(2, 5);
        System.out.println(obj.current());
        System.out.println(obj.maximum());
        obj.update(1, 3);
        System.out.println(obj.maximum());
        System.out.println(obj.minimum());
    }



}
