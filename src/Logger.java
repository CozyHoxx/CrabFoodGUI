import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    String logFile = "Log.txt";
    String formatString = "%-10s %-13s %-12s %-14s %-15s %-15s%n";
    PrintWriter pw = null;

    public Logger() {

    }

    public void log(Customer c, int i) {

        try {
            File file = new File(logFile);
            if (file.exists()) {
                pw = new PrintWriter(new BufferedWriter(new FileWriter(logFile, true)));
            } else {
                pw = new PrintWriter(logFile);
            }
            int totalTime = c.getFinishedCookingTime() - c.getOrderTimeInt() + c.getDeliveryTime();
            pw.print(String.format(formatString, "|" + i, "|" + c.getOrderTimeStr(), "|" + c.getOrderTimeStr(), "|" + c.getFinishedCookingTime(), "|" + c.getDeliveryTime(), "|" + totalTime));
            pw.close();
        } catch (Exception e) {
        }
    }

    public void endLog() {
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(logFile,true)));
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            pw.println("Logged on : " + date + "\n");
            pw.close();
        } catch (Exception e) {
        }
    }

    public void startLog() {
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(logFile,true)));
            pw.print(String.format(formatString, "|Customer", "|Arrival Time", "|Order Time", "|Finished Time", "|Delivery Time", "|Total Time"));
            pw.close();
        } catch (Exception e) {
        }
    }

    public File getFile(){
        return new File(logFile);
    }
}
