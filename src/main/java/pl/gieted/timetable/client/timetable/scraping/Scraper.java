package pl.gieted.timetable.client.timetable.scraping;

import org.jetbrains.annotations.NotNull;

public interface Scraper<T> {
    @NotNull T scrape(@NotNull String html) throws ParsingException;
}
