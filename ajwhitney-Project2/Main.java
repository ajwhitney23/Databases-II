import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                case "CREATE":
                    try {
                        qm.initializeIndex();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "SELECT":
                    String[] results = input.split(" WHERE ");

                    if(results[1].contains( "RandomV = ")) {
                        String[] vals = results[1].split("= ");
                        qm.equal(Integer.parseInt(vals[1]));
                    } else if(results[1].contains( "RandomV > ")) {
                        int val1 = 0;
                        int val2 = 0;
                        Pattern lb = Pattern.compile("(?<=> )\\d+");
                        Pattern ub = Pattern.compile("(?<=< )\\d+");
                        Matcher m = lb.matcher(results[1]);
                        if(m.find()) {
                            val1 = Integer.parseInt(m.group());
                        }
                        m = ub.matcher(results[1]);
                        if(m.find()) {
                            val2 = Integer.parseInt(m.group());
                        }
                        qm.range(val1, val2);
                    } else if(results[1].contains( "RandomV != ")) {
                        String[] vals = results[1].split("!= ");
                        qm.inequality(Integer.parseInt(vals[1]));
                    } else {
                        System.out.println("COMMAND NOT SUPPORTED \n INVALID SELECT STATEMENT...");
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
