Consider an alpha-numeric string Str, print all the permutations of the string in a sorted manner. Sorting follows the given sequence:
`Digit -> Upper-Case alphabets -> Lower-Case alphabets`(In alphabetical order).

Read the Input from STDIN (standard input) and Print to STDOUT (standard output) with a trailing new line.
You should not write arbitrary strings while reading the input and while printing as these contribute to the standard output.

**Constraints**:
Str contains only English alphabets and digits, i.e. [a-z, A-Z, 0-9].

**Input Format**:
The input contains Str.

**Output Format**:
The output contains all the permutations of Str in a sorted manner separated by a comma (,).

**Sample Input 1**:
aB3

**Sample Output 1**:
3Ba,3aB,B3a,Ba3,a3B,aB3

**Explanation 1**:
Here, 3 is given priority over 'a' and 'B'. 'B' is given priority over 'a'.
Thus, after sorting the permutations are: 3Ba, 3aB, B3a, Ba3, a3B and aB3.

**Sample Input 2**:
dd4dd

**Sample Output 2**:
4dddd,d4ddd,dd4dd,ddd4d,dddd4

**Explanation 2**:
Here, 4 is given priority over 'd'.
Thus, after sorting the permutations are: 4dddd, d4ddd, dd4dd, ddd4d and dddd4.