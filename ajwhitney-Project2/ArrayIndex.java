import java.util.LinkedList;

public class ArrayIndex {
    private LinkedList<String> list[];

    public ArrayIndex() {
        list = new LinkedList[5000];
    }

    /**
     * gets the value from the array
     * @param index
     * @return
     */
    public LinkedList<String> get(int index) {
        return list[index - 1];
    }

    /**
     * sets the value in the array
     * @param index
     * @param value
     * @return
     */
    public void set(int index, String value) {
        LinkedList<String> temp = list[index - 1];
        if(temp == null) {
            temp = new LinkedList<>();
        }
        temp.add(value);
        list[index - 1] = temp;
    }

    /**
     * removes the value from the array
     * @param index
     */
    public void remove(int index) {
        list[index - 1] = null;
    }
}
