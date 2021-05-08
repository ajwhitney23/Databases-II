Andrew Whitney (ajwhitney) (525692403)

Section I. (Compile & Execution)

- open terminal/powershell/cmd in the project directory
- type to compile program: javac Main.java QueryManager.java HashIndex.java HashTable.java FileManager.java
- to run program: java Main
- now program is running, available commands (specified in pdf):
    - SELECT A.Col1, A.Col2, B.Col1, B.Col2 FROM A, B WHERE A.RandomV = B.RandomV
    - SELECT count(*) FROM A, B WHERE A.RandomV > B.RandomV
    - SELECT Col2, [AggregationFunction(ColumnID)] FROM [Dataset] GROUP BY Col2
        - [AggregationFunction(ColumnID)] : SUM(RandomV) or AVG(RandomV)
        - [Dataset] : A or B
- type EXIT to exit program

Section II. (What is functional)

Project is fully functional within the bound of the project pdf.
Output works correctly, printing execution time at the end since output was getting cutoff for being too large for my terminal.

Section III. (Design Decisions)

Main function to handle user input.
QueryManger to handle index's and different queries.
HashIndex to handle insertions and retrievals on hash index.
HashTable to handle table regarding Section 4
FileManager to simulate holding 1 file at a time.
