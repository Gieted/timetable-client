package pl.gieted.timetable.client.timetable.scraping;

import org.junit.jupiter.api.Test;
import pl.gieted.timetable.client.timetable.Timetable_EntryFactory;
import pl.gieted.timetable.client.timetable.Timetable_WeekFactory;

import java.io.IOException;
import java.nio.file.Files;
import static pl.gieted.timetable.client.Resources.*;

public class TimetableParserTest {
    TimetableParser parser = new TimetableParser(new Timetable_EntryFactory(), new Timetable_WeekFactory());

    @Test
    void parses_timetable_ics_correctly() throws IOException {
        String timetableHtml = Files.readString(resources.resolve("timetables/correct.ics"));

        parser.parse(timetableHtml);
    }
}
