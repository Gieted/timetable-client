package pl.gieted.timetable.client.timetable.scraping;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;
import pl.gieted.timetable.client.Range;
import pl.gieted.timetable.client.timetable.Timetable;
import pl.gieted.timetable.client.timetable.TimetableFactory;
import pl.gieted.timetable.client.timetable.Group;
import pl.gieted.timetable.client.timetable.scraping.fetching.Fetcher;
import pl.gieted.timetable.client.timetable.scraping.fetching.NetworkException;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScrapingTimetableRepository {
    private final @NotNull Fetcher fetcher;
    private final @NotNull RootListScraper rootListScraper;
    private final @NotNull TimetableParser timetableParser;
    private final @NotNull TimetableFactory timetableFactory;
    private final @NotNull SublistScraper sublistScraper;

    @Inject
    public ScrapingTimetableRepository(@NotNull Fetcher fetcher,
                                       @NotNull RootListScraper rootListScraper,
                                       @NotNull TimetableParser timetableParser,
                                       @NotNull TimetableFactory timetableFactory,
                                       @NotNull SublistScraper sublistScraper) {

        this.fetcher = fetcher;
        this.rootListScraper = rootListScraper;
        this.timetableParser = timetableParser;
        this.timetableFactory = timetableFactory;
        this.sublistScraper = sublistScraper;
    }

    public @NotNull List<@NotNull Group> getRootList() throws TimetableUpdateException {
        String html = fetcher.fetchRootListHtml();

        return rootListScraper.scrape(html);
    }

    public @NotNull List<@NotNull Group> getSublist(@NotNull Group parent) throws TimetableUpdateException {
        String html = fetcher.fetchSublistHtml(parent.getId());

        return sublistScraper.scrape(html, parent);
    }

    private @NotNull Timetable.Week getWeek(@NotNull Group group, int week) throws TimetableUpdateException {
        String html = fetcher.fetchTimetableCvs(group.getId(), week);

        return timetableParser.parse(html);
    }

    public @NotNull Timetable getTimetable(@NotNull Group group, @NotNull Range weeksRange)
            throws TimetableUpdateException {

        List<Timetable.Week> weeks = new ArrayList<>();
        for (int week : weeksRange) {
            weeks.add(getWeek(group, week));
        }

        return timetableFactory.create(ImmutableList.copyOf(weeks));
    }
}
