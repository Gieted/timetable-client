package pl.gieted.timetable.client.timetable.scraping;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public abstract class ScrapingModule {

    @Provides
    public static OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }
}
