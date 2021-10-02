package pl.gieted.timetable.client.timetable.scraping.fetching;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.io.IOException;

@SuppressWarnings("HttpUrlsUsage")
public class Fetcher {
    private final OkHttpClient client;

    @Inject
    public Fetcher(OkHttpClient client) {
        this.client = client;
    }

    private String makeRequest(String url) throws TimetableFetchingException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();

            if (body == null) {
                throw new IncorrectResponseException("Response body is empty");
            }

            return body.string();
        } catch (IOException e) {
            throw new NetworkException();
        }
    }

    public @NotNull String fetchRootListHtml() throws TimetableFetchingException {
        return makeRequest("http://plan.ii.us.edu.pl/left_menu.php");
    }

    public @NotNull String fetchSublistHtml(@NotNull String parentId) throws TimetableFetchingException {
        return makeRequest("http://plan.ii.us.edu.pl/left_menu_feed.php?type=1&branch=" + parentId);
    }

    public @NotNull String fetchTimetableCvs(@NotNull String id, int week) throws TimetableFetchingException {
        return makeRequest("http://plan.ii.us.edu.pl/plan.php?cvsfile=true&id=%s&w=%d".formatted(id, week));
    }

    public @NotNull String fetchTimetableHtml(@NotNull String id) throws TimetableFetchingException {
        return makeRequest("http://plan.ii.us.edu.pl/plan.php?type=0&winW=1904&winH=510&id=" + id);
    }
}
