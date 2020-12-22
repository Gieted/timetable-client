package pl.gieted.timetable.client;

import dagger.Module;
import dagger.Provides;
import pl.gieted.timetable.client.javafx.JavaFxModule;
import pl.gieted.timetable.client.timetable.scraping.ScrapingModule;

import javax.inject.Named;

@Module(includes = {JavaFxModule.class, ScrapingModule.class})
public abstract class AppModule {

    @Provides
    @Named("applicationName")
    public static String applicationName() {
        return "Plan zajęć";
    }
}
