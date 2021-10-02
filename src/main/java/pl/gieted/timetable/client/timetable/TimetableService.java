package pl.gieted.timetable.client.timetable;

import org.jetbrains.annotations.NotNull;
import pl.gieted.timetable.client.Range;
import pl.gieted.timetable.client.timetable.scraping.ScrapingTimetableRepository;
import pl.gieted.timetable.client.timetable.scraping.TimetableUpdateException;

import javax.inject.Inject;
import java.util.List;

public class TimetableService {
    private final ScrapingTimetableRepository scrapingRepository;

    @Inject
    public TimetableService(ScrapingTimetableRepository scrapingRepository) {
        this.scrapingRepository = scrapingRepository;
    }

    public @NotNull List<@NotNull Group> getRootList() throws TimetableUpdateException {
        return scrapingRepository.getRootList();
    }

    public @NotNull List<@NotNull Group> getSublist(@NotNull Group parent) throws TimetableUpdateException {
        return scrapingRepository.getSublist(parent);
    }

    public @NotNull Timetable getTimetable(@NotNull Group group, @NotNull Range weeksRange)
            throws TimetableUpdateException {

        return scrapingRepository.getTimetable(group, weeksRange);
    }

    public int getCurrentWeek(@NotNull Group group) throws TimetableUpdateException {
        return scrapingRepository.getCurrentWeek(group);
    }
}
