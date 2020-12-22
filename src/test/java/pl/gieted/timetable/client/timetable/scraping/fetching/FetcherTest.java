package pl.gieted.timetable.client.timetable.scraping.fetching;

import okhttp3.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


public class FetcherTest {
    OkHttpClient okHttpClientMock = mock(OkHttpClient.class);
    Call callMock = mock(Call.class);
    Fetcher fetcher = new Fetcher(okHttpClientMock);

    @Test
    void throws_IncorrectResponseException_when_response_body_is_empty() throws IOException {
        when(okHttpClientMock.newCall(any())).thenReturn(callMock);

        Request request = new Request.Builder()
                .url("http://example")
                .build();

        Response response = new Response.Builder()
                .request(request)
                .code(200)
                .protocol(Protocol.HTTP_1_0)
                .body(null)
                .message("")
                .build();

        when(callMock.execute()).thenReturn(response);

        assertThatThrownBy(() -> fetcher.fetchRootListHtml()).isInstanceOf(IncorrectResponseException.class);
    }

    @Test
    void throws_NetworkException_when_okhttp_throws_IOException() throws IOException {
        when(okHttpClientMock.newCall(any())).thenReturn(callMock);
        when(callMock.execute()).thenThrow(IOException.class);

        assertThatThrownBy(() -> fetcher.fetchRootListHtml()).isInstanceOf(NetworkException.class);
    }
}
