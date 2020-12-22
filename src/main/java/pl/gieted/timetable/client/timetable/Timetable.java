package pl.gieted.timetable.client.timetable;

import com.google.auto.factory.AutoFactory;
import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@AutoFactory
public class Timetable {
    private final @NotNull ImmutableList<@NotNull Week> weeks;

    public Timetable(@NotNull ImmutableList<@NotNull Week> weeks) {
        this.weeks = weeks;
    }

    public @NotNull ImmutableList<@NotNull Week> getWeeks() {
        return weeks;
    }

    @AutoFactory
    public static class Week {
        private final @NotNull ImmutableList<@NotNull Entry> events;

        public Week(@NotNull ImmutableList<@NotNull Entry> events) {
            this.events = events;
        }

        public @NotNull ImmutableList<@NotNull Entry> getEvents() {
            return events;
        }
    }

    @AutoFactory
    public static class Entry {
        private final @NotNull String title;
        private final @NotNull Date startDate;
        private final @NotNull Date endDate;

        public Entry(@NotNull String title, @NotNull Date startDate, @NotNull Date endDate) {
            this.title = title;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public @NotNull String getTitle() {
            return title;
        }

        public @NotNull Date getStartDate() {
            return startDate;
        }

        public @NotNull Date getEndDate() {
            return endDate;
        }
    }
}
