package pl.gieted.timetable.client.timetable.scraping.fetching;

import pl.gieted.timetable.client.timetable.scraping.TimetableUpdateException;

public abstract class TimetableFetchingException extends TimetableUpdateException {
    public TimetableFetchingException() { }

    public TimetableFetchingException(String message) {
        super(message);
    }
}
