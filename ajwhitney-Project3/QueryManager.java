import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class QueryManager {
    private HashIndex hashIndex;
    private HashTable hashTable;

    public QueryManager() {

    }

    /**
     * initializes the index on the specified dataset
     * @param ds 
     * @throws IOException
     */
    private void initializeIndex(String ds) throws IOException {
        hashIndex = new HashIndex();
        for (int i = 1; i < 100; i++) {
            String fileString = "Project3Dataset-"+ds+"/"+ds + i + ".txt";
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
                    record[k] = input[offset + k];
                }
                for (int k = 0; k < 4; k++) {
                    vVal[k] = input[offset + 33 + k];
                }
                String val = new String(vVal);
                int randomV = Integer.parseInt(val);
                hashIndex.set(randomV, new String(record));
            }
        }
    }

    /**
     * performs the hash based join
     */
    public void hashBasedJoin() throws IOException {
        LinkedList<String> output = new LinkedList<>();
        initializeIndex("A");
        for (int i = 1; i < 100; i++) {
            String fileString = "Project3Dataset-B/B" + i + ".txt";
            FileReader fr = new FileReader(fileString);
            BufferedReader br = new BufferedReader(fr);
            char input[] = br.readLine().toCharArray();
            br.close();
            fr.close();
            for (int j = 0; j < 100; j++) {
                int offset = j * 40;
                char[] record = new char[40];
                char[] vVal = new char[4];
                for (int k = 0; k < 40; k++) {
                    record[k] = input[offset + k];
                }
                for (int k = 0; k < 4; k++) {
                    vVal[k] = input[offset + 33 + k];
                }
                String val = new String(vVal);
                int randomV = Integer.parseInt(val);
                LinkedList<String> result;
                result = hashIndex.get(randomV);
                if(result != null) {
                    String part2 = (new String(record)).substring(0, 19);
                    for(String s : result) {
                        String part1 = s.substring(0, 21);
                        output.add(part1 + part2);
                    }
                }
            }
        }
        long elapsedTime = System.nanoTime() - Main.startTime;
        for(String s : output) {
            System.out.println(s);
        }
        System.out.println("Query Time: " + (elapsedTime / 1000000) + "ms");
    }

    /**
     * performs the loop join, counts the number of occurances
     * @throws IOException
     */
    public void loopJoin() throws IOException {
        int returnNum = 0;
        for (int i = 1; i < 100; i++) {
            FileManager fm = new FileManager();
            String fileString = "Project3Dataset-A/A" + i + ".txt";
            FileReader fr = new FileReader(fileString);
            BufferedReader br = new BufferedReader(fr);
            char[] input = br.readLine().toCharArray();
            br.close();
            fr.close();
            for (int j = 0; j < 100; j++) {
                int offset = j * 40;
                char[] record = new char[40];
                for (int k = 0; k < 40; k++) {
                    record[k] = input[offset + k];
                }
                fm.add(new String(record));
            }
            for (int j = 1; j < 100; j++) {
                String fileString2 = "Project3Dataset-B/B" + i + ".txt";
                FileReader fr2 = new FileReader(fileString2);
                BufferedReader br2 = new BufferedReader(fr2);
                char input2[] = br2.readLine().toCharArray();
                br2.close();
                fr2.close();
                for (int k = 0; k < 100; k++) {
                    int offset = j * 40;
                    char vVal[] = new char[4];
                    for (int l = 0; l < 4; l++) {
                        vVal[l] = input2[offset + 33 + l];
                    }
                    String val = new String(vVal);
                    int randomV = Integer.parseInt(val);
                    for (int l = 0; l < 99; l++) {
                        if(fm.getRandomV(l) > randomV) {
                            returnNum++;
                        }
                    }
                }
            }
        }
        long elapsedTime = System.nanoTime() - Main.startTime;
        System.out.println("Query Time: " + (elapsedTime / 1000000) + "ms");
        System.out.println("Number of Qualifying Records: " + returnNum);
    }

    /**
     * performs the hash based aggregation
     * @param ds
     * @param aggFunc
     * @throws IOException
     */
    public void hashBasedAggregation(String ds, String aggFunc) throws IOException {
        ArrayList<String> col2 = new ArrayList<>();
        hashTable = new HashTable();
        for (int i = 1; i < 100; i++) {
            String fileString = "Project3Dataset-"+ds+"/"+ds + i + ".txt";
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
                    record[k] = input[offset + k];
                }
                for (int k = 0; k < 4; k++) {
                    vVal[k] = input[offset + 33 + k];
                }
                String rec = new String(record);
                String val = new String(vVal);
                int randomV = Integer.parseInt(val);
                hashTable.set(rec.substring(12, 19), randomV);
                if(!(col2.contains(rec.substring(12,19)))) {
                    col2.add(rec.substring(12,19));
                }
            }
        }
        if(aggFunc.contains("SUM")) {
            for(String s : col2) {
                System.out.println(s + "\t" + hashTable.getSum(s));
            }
        } else {
            for(String s : col2) {
                System.out.println(s + "\t" + hashTable.getAvg(s));
            }
        }
        long elapsedTime = System.nanoTime() - Main.startTime;
        System.out.println("Query Time: " + (elapsedTime / 1000000) + "ms");
    }
}
