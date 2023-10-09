import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        final List<String> sentences = Utils.readInputFile();

        final RecordProcessor processor = new RecordProcessor();
        // Group
        final Map<String, List<Record>> sentenceGroups = processor.groupSentences(sentences);

        // Write data into an output file
        Utils.writeOutputToFile(sentenceGroups);
    }
}
