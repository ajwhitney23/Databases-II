public class Frame {
    private char[] content;
    private boolean dirty;
    private boolean pinned;
    private int blockID;

    /**
     * initialize the frame with content size of 4000
     */
    public void initialize() {
        content = new char[4000];
        dirty = false;
        pinned = false;
        blockID = -1;
    }

    /**
     * getter for content of frame
     * @return content
     */
    public char[] getContent() {
        return content;
    }

    /**
     * setter for content of frame
     * @param newContent
     */
    public void setContent(char[] newContent) {
        this.content = newContent;
    }

    /**
     * getter for dirty flag
     * @return dirty
     */
    public boolean getDirty() {
        return dirty;
    }

    /**
     * setter for dirty flag
     * @param bool
     */
    public void setDirty(boolean bool) {
        this.dirty = bool;
    }

    /**
     * getter for pinned flag
     * @return pinned
     */
    public boolean getPinned() {
        return pinned;
    }

    /**
     * setter for pinned flag
     * @param bool
     */
    public void setPinned(boolean bool) {
        this.pinned = bool;
    }

    /**
     * getter for blockID
     * @return blockID
     */
    public int getBlockID() {
        return blockID;
    }

    /**
     * setter for blockID
     * @param val
     */
    public void setBlockID(int val) {
        this.blockID = val;
    }

    /**
     * getter for record specified
     * @param val which record number for frame
     * @return content of record as a string
     */
    public String getRecord(int val) {
        char[] record = new char[40];
        int start = val * 40;
        for(int i = 0; i < 40; i++) {
            record[i] = content[start + i];
        }
        return new String(record);
    }

    /**
     * setter for record specified
     * @param val which record number for frame
     * @param newRecord the new record
     */
    public void setRecord(int val, char[] newRecord) {
        int start = val * 40;
        for(int i = 0; i < 40; i++) {
            content[start + i] = newRecord[i];
        }
        dirty = true;
    }

}
