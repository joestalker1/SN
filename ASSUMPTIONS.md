# Minimal Triangle Path

1. In order to solve this task, I used the dynamic programming top-down approach.
2. Time complexity is O(m * n) where m and n are the numbers of rows and columns in the input matrix.
   Memory complexity is O(m*n) as well. It's possible to improve the memory complexity till O(n)
3. I used recursive function findEval to find a minimal path and in order to get eliminated 
   the StackOverflow exception, I use the cats Eval monad that uses heap instead of stack.
