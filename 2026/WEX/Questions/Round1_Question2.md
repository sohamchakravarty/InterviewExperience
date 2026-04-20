You are given a sequence of push and pop operations. The push operation pushes a specified integer on a stack that is initially empty. For example, U 4 would denote that integer 4 would be pushed on the stack. The pop operation, denoted by O, pops an integer from the stack. When the stack is empty, the pop operation is a no-op (does nothing).

Your task is to read the sequence of operations from the input and print the resulting popped values separated by a single whitespace.

Read the input from STDIN and print the output to STDOUT. Do not print arbitrary strings anywhere in the program, as these contribute to the output and test cases will fail.

**Constraints**:
All operations (U, O) have to be in upper case if not print "Invalid Input" (without quotes).

All input operations should be considered as a single string, separated by a single space.

Input string should end with X.

**Input Format**:
The input will be the command accepted as a string, ending with "X".

**Output Format**:
The output will be the result after the execution of commands.

**Sample Input 1**:
`U 3 U 3 U 5 O O U 7 U 7 O X`

**Sample Output 1**:
`5 3 7`

**Explanation 1**:
Here, by following the commands and the given constraints, the stack will be filled with elements 5, 3, and 7. Hence, it will print 5 3 7 as an output.

**Sample Input 2**:
`O O U 88 U -9 O U -9 O O X`

**Sample Output 2**:
`-9 -9 88`

**Explanation 2**:
Here, by following the commands and the given constraints, the stack will be filled with elements -9, -9, and 88. Hence, it will print -9 -9 88 as an output.