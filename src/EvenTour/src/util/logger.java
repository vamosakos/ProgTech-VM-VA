package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class logger {
    public static void writeToLog (String msg) {
        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String fileName = "logger_"+formattedDate+".txt";
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            LocalDateTime currentDateTime = LocalDateTime.now();
            String format = currentDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

            writer.append(format+" : ");
            writer.append(msg+"\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Exception writing logs "+e.getMessage());
        }

    }
}
