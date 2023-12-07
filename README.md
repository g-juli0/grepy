# grepy utility [Java]
## Gianna Julio
**CMPT440L - Formal Languages and Computability** Final Project

### Description
A version of the grep utility that searches files for regular expression pattern matches and produces dot graph file output for the automata used in the matching computation.

### Functionality
* Learns the alphabet from the input FILE.
* Converts the regular expression REGEX to an NFA.
* Converts the NFA to a DFA.
* Uses DFA computation to test each line of the file for accept/reject.
* File lines are delimited by newline characters.
* Accepted lines are printed to standard output.
* Outputs the NFA and DFA to the specified filenames in DOT language format.
    - http://en.wikipedia.org/wiki/DOT_language
    - http://www.graphviz.org/content/dot-language

### Example Calls
```
java -jar grepy [-n NFA-FILE] [-d DFA-FILE] REGEX FILE
java -jar grepy.jar 10* inputFile1.txt
java -jar grepy.jar -n samplenfa.dot -d sampledfa.dot 10* input1.txt -v
```


### Later Implementation
1. In addition to computing string inclusion using the DFA, also implement the decision via
a stack machine.
2. Visualize DFA and NFA as output of your program using libraries.
3. In addition to exporting the NFA and DFA to a DOT formatted file, allow the user to also
export the graphs to a picture file.

### Sample Test Cases
---
(0+1)*1

101001

101

00

01

1

a

---

a+b*

Ab

Bba

Bbab
