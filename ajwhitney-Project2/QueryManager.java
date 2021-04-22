import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class QueryManager {
    private HashIndex hashIndex;
    private ArrayIndex arrayIndex;
    private int blocksSearched;

    /**
     * default constructor
     */
    public QueryManager() {

    }

    /**
     * initializes the index for the project2dataset, which is hardcoded
     * @throws IOException
     */
    public void initializeIndex() throws IOException {
        hashIndex = new HashIndex();
        arrayIndex = new ArrayIndex();
        for (int i = 1; i < 100; i++) {
            String fileString = "Project2Dataset/F" + i + ".txt";
            FileReader fr = new FileReader(fileString);
            BufferedReader br = new BufferedReader(fr);
            char input[] = br.readLine().toCharArray();
            br.close();
            fr.close();
            for (int j = 0; j < 100; j++) {
                int offset = j * 40;
                char vVal[] = new char[4];
                for (int k = 0; k < 4; k++) {
                    vVal[k] = input[offset + 33 + k];
                }
                String val = new String(vVal);
                int randomV = Integer.parseInt(val);
                String val2 = (i + "_" + offset);
                hashIndex.set(randomV, val2);
                arrayIndex.set(randomV, val2);
            }
        }
        System.out.println("The hash-based and array-based indexes are built successfully. Program is ready" +
                " and waiting for user command.");
    }

    /**
     * Searches the Datasets and gets the matching query
     * output is:
     * print out the record(s) matching the query
     * the index type used (or table scan)
     * time taken to answer the query (in ms)
     * # of data files read
     * @param value index to match
     * @throws IOException
     */
    public void equal(int value) throws IOException {
        blocksSearched = 0;
        LinkedList<String> results = new LinkedList<>();
        if (hashIndex != null) {
            LinkedList<String> locations = hashIndex.get(value);
            for (String s : locations) {
                blocksSearched++;
                String ss[] = s.split("_");
                String fileString = "Project2Dataset/F" + ss[0] + ".txt";
                FileReader fr = new FileReader(fileString);
                BufferedReader br = new BufferedReader(fr);
                char input[] = br.readLine().toCharArray();
                br.close();
                fr.close();
                int offset = Integer.parseInt(ss[1]);
                char res[] = new char[40];
                for (int i = 0; i < 40; i++) {
                    res[i] = input[offset + i];
                }
                results.add(new String(res));
            }
        } else {
            for (int i = 1; i < 100; i++) {
                blocksSearched++;
                String fileString = "Project2Dataset/F" + i + ".txt";
                FileReader fr = new FileReader(fileString);
                BufferedReader br = new BufferedReader(fr);
                char input[] = br.readLine().toCharArray();
                br.close();
                fr.close();
                for (int j = 0; j < 100; j++) {
                    int offset = j * 40;
                    char record[] = new char[40];
                    char vVal[] = new char[4];
                    for (int k = 0; k < 40; k++) {
                        if (k >= 33 && k < 37) {
                            vVal[k - 33] = input[offset + k];
                        }
                        record[k] = input[offset + k];
                    }
                    if (Integer.parseInt(new String(vVal)) == value) {
                        results.add(new String(record));
                    }
                }
            }
        }
        long elapsedTime = System.nanoTime() - Main.startTime;
        System.out.println("Matching Records: ");
        for (String s : results) {
            System.out.println(s);
        }
        if (hashIndex != null) {
            System.out.println("HashIndex used for Query");
        } else {
            System.out.println("Table Scan used for Query");
        }
        System.out.println("Query Time: " + (elapsedTime / 1000000) + "ms");
        System.out.println("Blocks Read: " + blocksSearched);
    }

    /**
     * Searches the Datasets and gets the records that are have an inequality
     * output is:
     * print out the record(s) matching the query
     * the index type used (or table scan)
     * time taken to answer the query (in ms)
     * # of data files read
     *
     * @param value index to evaluate inequality
     * @throws IOException
     */
    public void inequality(int value) throws IOException {
        blocksSearched = 0;
        LinkedList<String> results = new LinkedList<>();
        for (int i = 1; i < 100; i++) {
            blocksSearched++;
            String fileString = "Project2Dataset/F" + i + ".txt";
            FileReader fr = new FileReader(fileString);
            BufferedReader br = new BufferedReader(fr);
            char input[] = br.readLine().toCharArray();
            br.close();
            fr.close();
            for (int j = 0; j < 100; j++) {
                int offset = j * 40;
                char record[] = new char[40];
                char vVal[] = new char[4];
                for (int k = 0; k < 40; k++) {
                    if (k >= 33 && k < 37) {
                        vVal[k - 33] = input[offset + k];
                    }
                    record[k] = input[offset + k];
                }
                if (Integer.parseInt(new String(vVal)) != value) {
                    results.add(new String(record));
                }
            }
        }
        long elapsedTime = System.nanoTime() - Main.startTime;
        System.out.println("Matching Records: ");
        for (String s : results) {
            System.out.println(s);
        }
        System.out.println("Query Time: " + (elapsedTime / 1000000) + "ms");
        System.out.println("Blocks Read: " + blocksSearched);

    }

    /**
     * Searches the Datasets and gets the records within the range specified by the upper and lower bounds
     * output is:
     * print out the record(s) matching the query
     * the index type used (or table scan)
     * time taken to answer the query (in ms)
     * # of data files read
     *
     * @param lb
     * @param ub
     * @throws IOException
     */
    public void range(int lb, int ub) throws IOException {
        blocksSearched = 0;
        LinkedList<String> results = new LinkedList<>();
        if (arrayIndex != null) {
            LinkedList<String> locations = new LinkedList<>();
            for(int i = lb; i < ub; i++) {
                LinkedList<String> temp = arrayIndex.get(i);
                if(temp != null) {
                    for(String s : temp) {
                        locations.add(s);
                    }
                }
            }
            for (String s : locations) {
                blocksSearched++;
                String ss[] = s.split("_");
                String fileString = "Project2Dataset/F" + ss[0] + ".txt";
                FileReader fr = new FileReader(fileString);
                BufferedReader br = new BufferedReader(fr);
                char input[] = br.readLine().toCharArray();
                br.close();
                fr.close();
                int offset = Integer.parseInt(ss[1]);
                char res[] = new char[40];
                for (int i = 0; i < 40; i++) {
                    res[i] = input[offset + i];
                }
                results.add(new String(res));
            }
        } else {
            for (int i = 1; i < 100; i++) {
                blocksSearched++;
                String fileString = "Project2Dataset/F" + i + ".txt";
                FileReader fr = new FileReader(fileString);
                BufferedReader br = new BufferedReader(fr);
                char input[] = br.readLine().toCharArray();
                br.close();
                fr.close();
                for (int j = 0; j < 100; j++) {
                    int offset = j * 40;
                    char record[] = new char[40];
                    char vVal[] = new char[4];
                    for (int k = 0; k < 40; k++) {
                        if (k >= 33 && k < 37) {
                            vVal[k - 33] = input[offset + k];
                        }
                        record[k] = input[offset + k];
                    }
                    if (Integer.parseInt(new String(vVal)) > lb && Integer.parseInt(new String(vVal)) < ub) {
                        results.add(new String(record));
                    }
                }
            }
        }
        long elapsedTime = System.nanoTime() - Main.startTime;
        System.out.println("Matching Records: ");
        for (String s : results) {
            System.out.println(s);
        }
        if (hashIndex != null) {
            System.out.println("ArrayIndex used for Query");
        } else {
            System.out.println("Table Scan used for Query");
        }
        System.out.println("Query Time: " + (elapsedTime / 1000000) + "ms");
        System.out.println("Blocks Read: " + blocksSearched);
    }
}
