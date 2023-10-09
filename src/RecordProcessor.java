import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RecordProcessor {
    public Map<String, List<Record>> groupSentences(final List<String> lines) {
        final Map<String, List<Record>> sentenceGroups = new LinkedHashMap<>();

        for (final String line : lines) {
            if (line.isEmpty()) {
                continue;
            }
            final Record record = splitSentence(line);
            boolean addedToGroup = false;
            final Map<String, List<Record>> partialMatches = new HashMap<>();

            // Iterate through existing groups
            for (final String key : sentenceGroups.keySet()) {
                final List<Record> sentenceGroup = sentenceGroups.get(key);

                // Check if the sentence differs by exactly one word
                boolean fullMatch = true;
                for (final Record sentence: sentenceGroup) {
                    if (!differByOneWord(record.entry, splitSentence(sentence.toString()).entry)) {
                        fullMatch = false;
                    } else {
                        partialMatches.putIfAbsent(record.entry, new ArrayList<>());
                        partialMatches.get(record.entry).add(record);
                        partialMatches.get(record.entry).add(sentence);
                    }
                }

                if (fullMatch) {
                    sentenceGroup.add(record);
                    addedToGroup = true;
                    partialMatches.clear();
                }
            }

            sentenceGroups.putAll(partialMatches);

            // If not added to any existing group, create a new group
            if (!addedToGroup) {
                final List<Record> newGroup = new ArrayList<>();
                newGroup.add(record);
                sentenceGroups.putIfAbsent(record.entry, newGroup);
            }
        }

        return sentenceGroups;
    }

    public boolean differByOneWord(final String sentence1, final String sentence2) {
        final String[] words1 = sentence1.split(" ");
        final String[] words2 = sentence2.split(" ");

        if (words1.length != words2.length) {
            return false;
        }

        int diffCount = 0;

        for (int i = 0; i < words1.length; i++) {
            if (!words1[i].equals(words2[i])) {
                diffCount++;
                if (diffCount > 1) {
                    return false;
                }
            }
        }

        return diffCount == 1;
    }

    public Record splitSentence(final String sentence) {
        // Find the index of the third space character
        int thirdSpaceIndex = -1;
        int spaceCount = 0;

        for (int i = 0; i < sentence.length(); i++) {
            if (sentence.charAt(i) == ' ') {
                spaceCount++;
                if (spaceCount == 2) {
                    thirdSpaceIndex = i;
                    break;
                }
            }
        }

        // Split the sentence at the third space
        final String part1 = sentence.substring(0, thirdSpaceIndex);
        final String part2 = sentence.substring(thirdSpaceIndex + 1);

        // Convert the first part into date time format
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Date date = null;
        try {
            date = dateFormat.parse(part1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Record(date, part2);
    }
}
