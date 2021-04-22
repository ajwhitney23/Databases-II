import java.io.*;
import java.util.HashMap;

public class BufferPool {
    private Frame[] buffers;
    private HashMap<Integer, Integer> frameMap; //<File, Frame>
    private int size;
    private boolean[] bitmap;
    private int[] lastUsed;
    private String outputString;
    private boolean broughtToBuffer = false;
    private boolean wasEvict = false;
    private int evictedFile = -1;

    /**
     * initializes the buffer pool and the frames inside of it
     * @param size how many frames will be in the buffer
     */
    public void initialize(int size) {
        this.size = size;
        buffers = new Frame[size];
        bitmap = new boolean[size];
        lastUsed = new int[size];
        for(int i = 0; i < size; i++) {
            buffers[i] = new Frame();
            buffers[i].initialize();
        }
        frameMap = new HashMap<>();
    }

    /**
     * helper for the output
     * @param result result of the call which will be printed
     * @param file which file is being interacted with
     */
    private void buildOutput(String result, int file) {
        outputString = result;
        String fileFrameString;
        if(broughtToBuffer) {
            fileFrameString = "Brought File " + file + " from disk; Placed in Frame " + (frameMap.get(file) + 1);
        } else {
            fileFrameString = "File " + file + " already in memory; Located in Frame " + (frameMap.get(file) + 1);
        }
        outputString = outputString + "; " + fileFrameString;
        if(wasEvict) {
            outputString = outputString + "; Evicted File " + evictedFile + " from Frame " + (frameMap.get(file) + 1);
        }
    }

    /**
     * main function for GET call
     * @param record which record number to get (not in specific frame)
     * @throws IOException throws if accessing disk fails (issue with project setup)
     */
    public void get(int record) throws IOException {
        outputString = null;
        broughtToBuffer = false;
        wasEvict = false;
        int file = ((record - 1) / 100) + 1;
        int recordNum = ((record -1) % 100);
        String result = getContent(file, recordNum);
        if(result != null) {
            buildOutput(result, file);
            System.out.println(outputString);
        } else {
            System.out.println("The corresponding block " + file + " cannot be accessed from disk because the memory buffers are full; Write was unsuccessful");
        }
    }

    /**
     * main function for SET call
     * @param record which record number to set (not in specific frame)
     * @param content content that will replace that record
     * @throws IOException throws if accessing disk fails (issue w/ project setup)
     */
    public void set(int record, char[] content) throws IOException {
        outputString = null;
        broughtToBuffer = false;
        wasEvict = false;
        int file = ((record - 1) / 100) + 1;
        int recordNum = ((record -1) % 100);
        boolean isSuccess = setContent(file, recordNum, content);
        if(isSuccess) {
            buildOutput("Write was successful", file);
            System.out.println(outputString);
        } else {
            System.out.println("The corresponding block " + file + " cannot be accessed from disk because the memory buffers are full; Write was unsuccessful");
        }
    }

    /**
     * main function for PIN call
     * @param fileID which file to pin in buffer pool
     * @throws IOException throws if accessing disk fails (issue w/ project setup)
     */
    public void pin(int fileID) throws IOException {
        outputString = null;
        broughtToBuffer = false;
        wasEvict = false;
        int searchRes = search(fileID);
        int frame = searchRes;
        if(frame == -1) {
            frame = toBuffer(fileID);
        }
        if(frame != -1) {
            if(buffers[frame].getPinned() == false) {
                buffers[frame].setPinned(true);
                outputString = "File "+fileID+" pinned in Frame "+(frame + 1)+"; Frame " +(frame + 1)+" was not already pinned";
                if(wasEvict) {
                    outputString = outputString + "; Evicted File " + evictedFile + " from Frame " + (frameMap.get(fileID) + 1);
                }
            } else {
                outputString = "File "+fileID+" pinned in Frame "+ (frame + 1) +"; Frame " +(frame + 1)+" was already pinned";
            }
            lastUsed[frame] = Main.getCommandNum();
        } else {
            outputString = "The corresponding block " + fileID + " cannot be pinned because the memory buffers are full";
        }
        System.out.println(outputString);
    }

    /**
     * main function for UNPIN call
     * @param fileID which file to unpin
     */
    public void unpin(int fileID) {
        outputString = null;
        broughtToBuffer = false;
        wasEvict = false;
        int frame = search(fileID);
        if(frame == -1) {
            outputString = "The corresponding block " +fileID+ " cannot be unpinned because it is not in memory.";
        } else {
            if(buffers[frame].getPinned() == true) {
                buffers[frame].setPinned(false);
                outputString = "File "+fileID+" in Frame "+(frame + 1)+" is unpinned; Frame " +(frame + 1)+" was not already unpinned";
            } else {
                outputString = "File "+fileID+" in Frame "+(frame + 1)+" is unpinned; Frame " +(frame + 1)+" was already unpinned";
            }
            lastUsed[frame] = Main.getCommandNum();
        }
        System.out.println(outputString);
    }

    /**
     * searches the hashmap and see's if file is in a specific frame
     * @param fileID which file to search for
     * @return frame # it is located at, or -1 if not in any frames
     */
    private int search(int fileID) {
        if(frameMap.get(fileID) == null) {
            return -1;
        } else {
            return frameMap.get(fileID);
        }
    }

    /**
     * gets the content for the record specified
     * @param fileID which file to access
     * @param record which record in that file
     * @return String of the record (40 chars long)
     * @throws IOException issue w/ project files setup
     */
    private String getContent(int fileID, int record) throws IOException {
        String content;
        int frame = search(fileID);
        if(frame == -1) {
            frame = toBuffer(fileID);
        }
        if(frame != -1) {
            lastUsed[frame] = Main.getCommandNum();
            content = buffers[frame].getRecord(record);
        } else {
            return null;
        }

        return content;
    }

    /**
     * Sets the content of a record specified
     * @param fileID which file to access
     * @param record which record in that file
     * @param content new content to replace that record
     * @return true if successful, false if not
     * @throws IOException
     */
    private boolean setContent(int fileID, int record, char[] content) throws IOException {

        int frame = search(fileID);
        if(frame == -1) {
            frame = toBuffer(fileID);
        }
        if(frame != -1) {
            lastUsed[frame] = Main.getCommandNum();
            buffers[frame].setRecord(record, content);
            return true;
        }
        return false;
    }

    /**
     * Brings the file to the bufferpool into a frame. Will evict files if needed
     * @param fileID file to be brought to memory
     * @return which frame file is set to, returns -1 if no available buffer
     * @throws IOException issue w/ file setup
     */
    private int toBuffer(int fileID) throws IOException {
        int frame;
        frame = findEmpty();
        if(frame == -1) {
            frame = evict();
            if(frame == -1) {
                return -1;
            }
            wasEvict = true;
            evictedFile = buffers[frame].getBlockID();
            if(buffers[frame].getDirty() == true) {
                writeToDisk(evictedFile);
            }
            frameMap.remove(evictedFile);
            bitmap[frame] = true;
            frameMap.put(fileID, frame);
        }
        bitmap[frame] = true;
        frameMap.put(fileID, frame);

        String fileString = "F"+fileID+".txt";
        FileReader fr = new FileReader(fileString);
        BufferedReader br = new BufferedReader(fr);
        String input = br.readLine();
        br.close();
        buffers[frame].setContent(input.toCharArray());
        buffers[frame].setBlockID(fileID);

        broughtToBuffer = true;
        return frame;
    }

    private void writeToDisk(int evictedFile) throws IOException {
        String fileName = "F"+evictedFile+".txt";
        FileWriter fw = new FileWriter(fileName, false);
        fw.write(buffers[frameMap.get(evictedFile)].getContent());
        fw.close();
    }

    /**
     * finds an empty buffer in the bitmap
     * @return the first empty frame in the buffer
     */
    private int findEmpty() {
        for(int i = 0; i < size; i++) {
            if(!bitmap[i]) {
                return i;
            }
        }
        return -1;
    }


    /**
     * finds the frame that hasnt been used the longest and returns that as the one to be evicted.  
     * Will not return any that are pinned
     * @return frame to be evicted
     */
    private int evict() {
        int toBeEvicted = -1;
        int lastUsedInt = Integer.MAX_VALUE;
        for(int i = 0; i < size; i++) {
            if(lastUsed[i] < lastUsedInt && buffers[i].getPinned() == false) {
                toBeEvicted = i;
                lastUsedInt = lastUsed[i];
            }
        }
        return toBeEvicted;
    }
}

