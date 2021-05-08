import java.util.HashMap;
import java.util.LinkedList;

public class HashIndex {
    private HashMap<Integer, LinkedList<String>> map;

    public HashIndex() {
        map = new HashMap<>();
    }

    /**
     * gets the value from the hash map
     * @param key
     * @return
     */
    public LinkedList<String> get(int key) {
        return map.get(key);
    }

    /**
     * sets the value from the hash map
     * @param key
     * @param value
     */
    public void set(int key, String value) {
        LinkedList<String> temp = map.get(key);
        if (temp == null) {
            temp = new LinkedList<>();
        }
        temp.add(value);
        map.put(key, temp);
    }

    /**
     * removes the value from the hash map
     * @param key
     */
    public void remove(int key) {
        map.remove(key);
    }
}

