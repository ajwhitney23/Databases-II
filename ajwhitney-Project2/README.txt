Andrew Whitney (ajwhitney) (525692403)

Section I. (Compile & Execution)

- open terminal/powershell/cmd in the project directory
- type to compile program: javac Main.java QueryManager.java HashIndex.java ArrayIndex.java
- to run program: java Main
- now program is running, available commands (specified in pdf):
    - CREATE INDEX ON Project2Dataset (RandomV)
    - SELECT * FROM Project2Dataset WHERE RandomV = [value]
    - SELECT * FROM Project2Dataset WHERE RandomV > [value1] AND RandomV < [value2]
    - SELECT * FROM Project2Dataset WHERE RandomV != [value]
- type EXIT to exit program

Section II. (What is functional)

Project is fully functional within the bound of the project pdf, queries must be fixed.
Output works correctly, some queries are fast and returning a 0ms execution time on my machine, may differ.

Section III. (Design Decisions)

Main function to handle user input.
QueryManger to handle index's and different queries.
ArrayIndex to handle insertions and retrievals on array index.
HashIndex to handle insertions and retrievals on hash index.

Didn't chose to abstract a Index interface since project was smaller in size and had no plans for refactoring or future
iterations.

References:
    This link was used to learn regular expressions to get the numbers for the range:
    https://devqa.io/extract-numbers-string-java-regular-expressions/