package pl.gieted.timetable.client.timetable.scraping.fetching;

public class NetworkException extends TimetableFetchingException {
    public NetworkException() {
    }

    @SuppressWarnings("unused")
    public NetworkException(String message) {
        super(message);
    }
}
