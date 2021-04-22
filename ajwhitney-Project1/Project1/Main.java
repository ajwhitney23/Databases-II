import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static int commandNum;
    private static BufferPool bp = new BufferPool();

    /**
     * getter for the number of commands run... a way to keep track of "time"
     * @return commandNum
     */
    public static int getCommandNum() {
        return commandNum;
    }

    public static void main(String[] args) {

        int size = Integer.parseInt(args[0]);
        bp.initialize(size);

        Scanner scan = new Scanner(System.in);

        System.out.println("The program is ready for the next command");

        boolean running = true;
        while(running) {
            String input = scan.nextLine();
            String[] command = input.split(" ");
            switch(command[0]) {
                case "GET":
                    commandNum++;
                    try {
                        bp.get(Integer.parseInt(command[1]));
                    } catch (IOException e) {
                        System.out.println("Error reading/writing with file.");
                    }
                    break;
                case "SET":
                    commandNum++;
                    char[] inputChars = new char[40];
                    int numQM = 0;
                    int inputIndex = 0;
                    for(int i = 0; i < input.length(); i++) {
                        if(numQM == 1 && inputIndex != 40) {
                            inputChars[inputIndex] = input.charAt(i);
                            inputIndex++;
                        }
                        else if(input.charAt(i) == '\"') {
                            numQM++;
                        }
                    }
                    try {
                        bp.set(Integer.parseInt(command[1]), inputChars);
                    } catch (IOException e) {
                        System.out.println("Error reading/writing with file.");
                    }
                    break;
                case "PIN":
                    commandNum++;
                    try {
                        bp.pin(Integer.parseInt(command[1]));
                    } catch (IOException e) {
                        System.out.println("Error reading/writing with file.");
                    }
                    break;
                case "UNPIN":
                    commandNum++;
                    bp.unpin(Integer.parseInt(command[1]));
                    break;
                case "EXIT":
                    running = false;
                    break;
                default:
                    System.out.println("COMMAND NOT SUPPORTED");
                    break;
            }
        }
        scan.close();
    }
}
