import java.text.SimpleDateFormat;
import java.util.Date;

public class Record {
    Date date;
    String entry;

    public Record(final Date date, final String entry) {
        this.date = date;
        this.entry = entry;
    }

    public String toString() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        final String formattedDate = dateFormat.format(date);

        return formattedDate + " " + this.entry;
    }
}
