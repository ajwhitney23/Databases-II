import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static QueryManager qm = new QueryManager();
    public static long startTime;

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Program is ready and waiting for user command.");

        boolean running = true;
        while(running) {
            String input = scan.nextLine();
            startTime = System.nanoTime();
            String[] command = input.split(" ");

            switch(command[0]) {
                case "SELECT":
                    switch(command[1]) {
                        case "A.Col1,":
                            qm.hashBasedJoin();
                            break;
                        case "count(*)":
                            qm.loopJoin();
                            break;
                        case "Col2,":
                            qm.hashBasedAggregation(command[4], command[2]);
                            break;
                        default:
                            System.out.println("SELECT COMMAND NOT SUPPORTED");
                            break;
                    }
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

