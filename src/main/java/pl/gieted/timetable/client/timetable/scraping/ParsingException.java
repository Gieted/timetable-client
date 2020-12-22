package pl.gieted.timetable.client.timetable.scraping;

public class ParsingException extends TimetableUpdateException {
    public ParsingException() {
    }

    public ParsingException(String message) {
        super(message);
    }
}
