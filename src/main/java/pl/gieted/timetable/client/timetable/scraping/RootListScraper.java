package pl.gieted.timetable.client.timetable.scraping;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pl.gieted.timetable.client.timetable.Group;
import pl.gieted.timetable.client.timetable.GroupFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static pl.gieted.timetable.client.utils.LambdaExceptionUtil.rethrowFunction;

public class RootListScraper implements Scraper<List<Group>> {
    private final GroupFactory groupFactory;

    @Inject
    public RootListScraper(GroupFactory groupFactory) {
        this.groupFactory = groupFactory;
    }

    public @NotNull List<@NotNull Group> scrape(@NotNull String listHtml) throws ParsingException {
        Document document = Jsoup.parse(listHtml);
        Elements elements = document.select("li");

        if (elements.isEmpty()) {
            throw new ParsingException();
        }

        return elements.stream().map(rethrowFunction(element -> {
            Elements span = element.select("span");
            String name = span.text();
            String id = element.attr("id");

            if (id.isEmpty()) {
                throw new ParsingException();
            }

            String imgSrc = element.select("img").attr("src");
            boolean hasChildren = imgSrc.contains("plus");
            boolean selectable = !element.select("> span > a").isEmpty();

            return groupFactory.create(name, id, null, hasChildren, selectable);
        })).collect(Collectors.toList());
    }
}
