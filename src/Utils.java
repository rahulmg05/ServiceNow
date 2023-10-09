import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Utils {

    public static void writeOutputToFile(final Map<String, List<Record>> sentenceGroups) {
        try {
            final String outputFilePath = "/tmp/OUTPUT";
            final BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));

            for (List<Record> records : sentenceGroups.values()) {
                Collections.sort(records, (a, b) -> a.date.compareTo(b.date));
                for (Record record : records) {
                    writer.write(record.toString());
                    writer.newLine();
                }
                writer.write("*************************************");
                writer.newLine();
            }

            writer.close();

            System.out.println("Output written successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readInputFile() {
        // Read data from a file
        final String inputFilePath = "/tmp/INPUT";
        final List<String> sentences = new ArrayList<>();

        try {
            final BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
            String line;

            while ((line = reader.readLine()) != null) {
                sentences.add(line);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sentences;
    }
}
