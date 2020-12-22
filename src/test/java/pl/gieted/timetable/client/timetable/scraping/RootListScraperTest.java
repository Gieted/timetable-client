package pl.gieted.timetable.client.timetable.scraping;

import org.junit.jupiter.api.Test;
import pl.gieted.timetable.client.Predicates;
import pl.gieted.timetable.client.timetable.Group;
import pl.gieted.timetable.client.timetable.GroupFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static pl.gieted.timetable.client.Resources.*;

public class RootListScraperTest {
    RootListScraper scraper = new RootListScraper(new GroupFactory());

    @Test
    void scrapes_root_list_correctly() throws IOException, ParsingException {
        String rootListHtml = Files.readString(resources.resolve("group-lists/root/correct.html"));

        List<Group> options = scraper.scrape(rootListHtml);

        assertThat(options.stream().map(Group::getName).collect(Collectors.toList())).contains(
                "Biofizyka I stopnia",
                "Biofizyka II stopnia",
                "Chemia I stopnia"
        );

        assertThat(options.stream().map(Group::getId).collect(Collectors.toList())).contains(
                "28638",
                "28639",
                "40376"
        );

        assertThat(options.stream().map(Group::getParent).collect(Collectors.toList())).allMatch(Objects::isNull);
        assertThat(options.stream().map(Group::hasChildren).collect(Collectors.toList())).allMatch(Predicates::isTrue);

        assertThat(options.stream().map(Group::isSelectable).collect(Collectors.toList())).contains(
                false,
                false,
                true
        );
    }

    @Test
    void throws_ParsingException_when_provided_string_is_not_html() throws IOException {
        String incorrectRootList = Files.readString(resources.resolve("group-lists/root/not_html.txt"));

        assertThatThrownBy(() -> scraper.scrape(incorrectRootList)).isInstanceOf(ParsingException.class);
    }

    @Test
    void throws_ParsingException_when_html_is_valid_but_does_not_contain_any_list() throws IOException {
        String incorrectRootList = Files.readString(resources.resolve("group-lists/root/no_list.html"));

        assertThatThrownBy(() -> scraper.scrape(incorrectRootList)).isInstanceOf(ParsingException.class);
    }

    @Test
    void throws_ParsingException_when_list_does_not_contain_any_elements() throws IOException {
        String incorrectRootList = Files.readString(resources.resolve("group-lists/root/no_elements.html"));

        assertThatThrownBy(() -> scraper.scrape(incorrectRootList)).isInstanceOf(ParsingException.class);
    }

    @Test
    void throws_ParsingException_when_any_of_elements_does_not_have_id_attribute() throws IOException {
        String incorrectRootList = Files.readString(resources.resolve("group-lists/root/no_id_attr.html"));

        assertThatThrownBy(() -> scraper.scrape(incorrectRootList)).isInstanceOf(ParsingException.class);
    }
}
