import java.util.LinkedList;

public class FileManager {
    private LinkedList<String> records;

    public FileManager() {
        records = new LinkedList<>();
    }

    public void add(String value) {
        records.add(value);
    }

    public int getRandomV(int recordNum) {
        return Integer.parseInt(records.get(recordNum).substring(33, 37));
    }
}

