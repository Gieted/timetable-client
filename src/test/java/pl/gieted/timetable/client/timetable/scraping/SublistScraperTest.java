package pl.gieted.timetable.client.timetable.scraping;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.gieted.timetable.client.timetable.Group;
import pl.gieted.timetable.client.timetable.GroupFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import static pl.gieted.timetable.client.Resources.*;

import static org.assertj.core.api.Assertions.assertThat;

public class SublistScraperTest {
    SublistScraper scraper = new SublistScraper(new GroupFactory());

    @Test
    void scrapes_sublist_correctly() throws IOException {
        String rootListHtml = Files.readString(resources.resolve("group-lists/sublist.html"));
        Group groupMock = Mockito.mock(Group.class);

        List<Group> options = scraper.scrape(rootListHtml, groupMock);

        assertThat(options.stream().map(Group::getName).collect(Collectors.toList())).contains(
                "INŻ I F11",
                "INŻ I F12"
        );

        assertThat(options.stream().map(Group::getId).collect(Collectors.toList())).contains(
                "27179",
                "27180"
        );
    }
}
