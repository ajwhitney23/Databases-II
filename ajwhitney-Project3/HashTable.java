import java.util.HashMap;
import java.util.LinkedList;

public class HashTable {
    private HashMap<String, LinkedList<Integer>> table;

    public  HashTable() {
        table = new HashMap<>();
    }

    /**
     * gets the value from the hash map
     * @param key
     * @return
     */
    public int getSum(String key) {
        LinkedList<Integer> randomVs = table.get(key);
        if(randomVs == null) {
            return 0;
        }
        int sum = 0;
        for(int randomV : randomVs) {
            sum += randomV;
        }
        return sum;
    }

    /**
     * gets the value from the hash map
     * @param key
     * @return
     */
    public int getAvg(String key) {
        LinkedList<Integer> randomVs = table.get(key);
        if(randomVs == null) {
            return 0;
        }
        int sum = 0;
        int num = 0;
        for(int randomV : randomVs) {
            sum += randomV;
            num++;
        }
        return sum/num;
    }


    /**
     * sets the value from the hash map
     * @param key
     * @param value
     */
    public void set(String key, int value) {
        LinkedList<Integer> temp = table.get(key);
        if (temp == null) {
            temp = new LinkedList<>();
        }
        temp.add(value);
        table.put(key, temp);
    }

    /**
     * removes the value from the hash map
     * @param key
     */
    public void remove(int key) {
        table.remove(key);
    }



}
