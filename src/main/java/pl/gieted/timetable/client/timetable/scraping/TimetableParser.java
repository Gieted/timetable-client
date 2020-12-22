package pl.gieted.timetable.client.timetable.scraping;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;
import pl.gieted.timetable.client.timetable.Timetable;
import pl.gieted.timetable.client.timetable.Timetable_EntryFactory;
import pl.gieted.timetable.client.timetable.Timetable_WeekFactory;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

public class TimetableParser {
    private final @NotNull Timetable_EntryFactory entryFactory;
    private final @NotNull Timetable_WeekFactory weekFactory;


    @Inject
    public TimetableParser(@NotNull Timetable_EntryFactory entryFactory, @NotNull Timetable_WeekFactory weekFactory) {

        this.entryFactory = entryFactory;
        this.weekFactory = weekFactory;
    }

    public @NotNull Timetable.Week parse(@NotNull String timetableCvs) {
        ICalendar calendar = Biweekly.parse(timetableCvs).first();
        List<VEvent> events = calendar.getEvents();
        ImmutableList<Timetable.Entry> entries = events.stream()
                .filter(entry -> entry.getDateStart() != null && entry.getDateEnd() != null)
                .map(event -> {
                    String title = event.getSummary().getValue();
                    Date startDate = event.getDateStart().getValue();
                    Date endDate = event.getDateEnd().getValue();

                    return entryFactory.create(title, startDate, endDate);
                }).collect(ImmutableList.toImmutableList());

        return weekFactory.create(entries);
    }
}
