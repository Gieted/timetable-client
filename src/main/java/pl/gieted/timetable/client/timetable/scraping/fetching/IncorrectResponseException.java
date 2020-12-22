package pl.gieted.timetable.client.timetable.scraping.fetching;

public class IncorrectResponseException extends TimetableFetchingException {
    @SuppressWarnings("unused")
    public IncorrectResponseException() { }

    public IncorrectResponseException(String message) {
        super(message);
    }
}
