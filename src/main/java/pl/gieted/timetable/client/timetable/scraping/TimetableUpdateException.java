package pl.gieted.timetable.client.timetable.scraping;

public abstract class TimetableUpdateException extends Exception {
    public TimetableUpdateException() {
    }

    public TimetableUpdateException(String message) {
        super(message);
    }
}
