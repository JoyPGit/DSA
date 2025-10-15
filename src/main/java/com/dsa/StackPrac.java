package com.dsa;

import java.util.*;

public class StackPrac {
    /**
     * min stack -> node stores current val and min
     * https://leetcode.com/problems/min-stack/submissions/1799953172/
     *
     * https://leetcode.com/problems/asteroid-collision/submissions/1796146157/
     * https://leetcode.com/problems/largest-rectangle-in-histogram/submissions/1795304422/
     * <p>
     * https://leetcode.com/problems/evaluate-reverse-polish-notation/submissions/1797591941/
     * FOLLOW UP
     * https://leetcode.com/problems/simplify-path/description/
     * <p>
     *
     * String
     * https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/submissions/
     * <p>
     *
     * https://leetcode.com/problems/maximum-frequency-stack/description/
     * https://leetcode.com/problems/implement-stack-using-queues/description/
     * https://leetcode.com/problems/min-stack/description/
     * https://leetcode.com/problems/design-a-stack-with-increment-operation/description/
     *
     * TODO
     * https://leetcode.com/problems/sum-of-subarray-minimums/description/
     * https://leetcode.com/problems/smallest-subsequence-of-distinct-characters/description/
     */


    class BasicCalculator {
        /**
         * "2*(5+5*2)/3+(6/2+8)"
         * <p>
         * else { // operator
         * // Handle unary minus (e.g., "-2" or "(-3)")
         * if ((i == 0) || (s.charAt(i - 1) == '(')) {
         * operands.push(0);
         * }
         * <p>
         * while (!operators.isEmpty() &&
         * precedence.get(operators.peek()) >= precedence.get(c)) {
         * compute(operands, operators);
         * }
         * operators.push(c);
         * }
         */

        private Map<Character, Integer> precedence;

        public int calculate(String s) {
            s = s.replaceAll(" ", "");
            Deque<Integer> operands = new LinkedList<>();
            Deque<Character> operators = new LinkedList<>();

            precedence = new HashMap<>();
            precedence.put('+', 1);
            precedence.put('-', 1);
            precedence.put('*', 2);
            precedence.put('/', 2);
            precedence.put('(', 0);

            int n = s.length();
            for (int i = 0; i < n; i++) {
                char c = s.charAt(i);

                if (Character.isDigit(c)) {
                    int j = i;
                    while (j < n && Character.isDigit(s.charAt(j))) {
                        j++;
                    }
                    int num = Integer.parseInt(s.substring(i, j));
                    // push onto operands — as last
                    operands.addLast(num);
                    i = j - 1;

                } else if (c == '(') {
                    operators.addLast(c);

                } else if (c == ')') {
                    while (operators.getLast() != '(') {
                        compute(operands, operators);
                    }
                    // remove '('
                    operators.removeLast();

                } else { // c is an operator +, -, *, /

                    // if no operand before operator only bracket or start
                    if ((s.charAt(i) == '+' || s.charAt(i) == '-')
                            && (i == 0 || s.charAt(i - 1) == '(')) {
                        operands.addLast(0);
                    }

                    while (!operators.isEmpty() &&
                            precedence.get(operators.getLast()) >= precedence.get(c)) {
                        compute(operands, operators);
                    }
                    operators.addLast(c);
                }
                System.out.println("index " + i + ", " + operands);
                System.out.println(operators);
            }

            while (!operators.isEmpty()) {
                compute(operands, operators);
            }

            // final result is the only operand remaining
            return operands.removeLast();
        }

        private void compute(Deque<Integer> operands, Deque<Character> operators) {
            int b = operands.removeLast();
            int a = operands.removeLast();
            char op = operators.removeLast();

            int result;
            switch (op) {
                case '+':
                    result = a + b;
                    break;
                case '-':
                    result = a - b;
                    break;
                case '*':
                    result = a * b;
                    break;
                case '/':
                    result = a / b;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operator: " + op);
            }

            operands.addLast(result);
        }
    }

    class Solution {
        /**
         * slash -> /// -> / compress
         * . ->  > 2   push (/..hidden)
         * ->  == 2  pop
         * ->  == 1  ignore
         */
        public String simplifyPath(String path) {
            int n = path.length();
            Deque<String> q = new LinkedList<>();
            for (int i = 0; i < n; i++) {
                char curr = path.charAt(i);
                if (curr == '/') {
                    int j = i;
                    while (j < n && path.charAt(j) == '/')
                        j++;
                    i = j - 1;
                    continue;
                } else if (curr == '.') {
                    int j = i, dots = 0;
                    // for . -> / is reqd
                    while (j < n && path.charAt(j) != '/') {
                        if (path.charAt(j) == '.')
                            dots++;
                        j++;
                        if (dots > 2) {
                            q.addLast(path.substring(i, j));
                            i = j - 1;
                        } else if (dots == 2 && q.size() != 0) {
                            q.removeLast();
                            i = j - 1;
                        }
                        // else if(j-i == 1)
                        continue;
                    }
                } else {
                    int j = i;
                    while (j < n && path.charAt(j) != '.'
                            && path.charAt(j) != '/')
                        j++;
                    q.addLast(path.substring(i, j));
                    i = j - 1;
                }
            }

            StringBuilder res = new StringBuilder();
            // System.out.println(q);
            if (q.size() == 0) return "/";
            while (q.size() != 0) {
                res.append("/");
                res.append(q.removeFirst());
            }
            return res.toString();
        }
    }

    class ValidParentheses {
        /**
         if ')' -> if q.size = 0 pop ')' add to set
                -> else if top is '(' pop once
         if still '(' exists -> remove those
         */
        // https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/solutions/
        // 7273096/simple-pop-for-balance-add-to-set-by-swa-fvlw/
        public String minRemoveToMakeValid(String s) {
            int n = s.length();
            Deque<Integer> stack = new ArrayDeque<>();
            boolean[] remove = new boolean[n];

            // First pass: mark invalid ')'
            for (int i = 0; i < n; i++) {
                char c = s.charAt(i);
                if (c == '(') {
                    stack.push(i);
                } else if (c == ')') {
                    if (stack.isEmpty()) {
                        remove[i] = true;
                    } else {
                        stack.pop();
                    }
                }
                // letters: do nothing
            }

            // Any '(' indices left in stack are unmatched
            while (!stack.isEmpty()) {
                remove[stack.pop()] = true;
            }

            // Build result skipping removed positions
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                if (!remove[i]) {
                    sb.append(s.charAt(i));
                }
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        StackPrac stackPrac = new StackPrac();
//        stackPrac.new BasicCalculator().calculate("2*((5+5*2)/3+(6/2+8))");
        System.out.println(stackPrac.new ValidParentheses().minRemoveToMakeValid("))(("));
    }
}



