package pl.gieted.timetable.client.timetable.scraping;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pl.gieted.timetable.client.timetable.Group;
import pl.gieted.timetable.client.timetable.GroupFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SublistScraper {
    private final GroupFactory groupFactory;

    @Inject
    public SublistScraper(GroupFactory groupFactory) {
        this.groupFactory = groupFactory;
    }

    public @NotNull List<@NotNull Group> scrape(@NotNull String listHtml, @NotNull Group parent) {
        Document document = Jsoup.parse(listHtml);
        Elements elements = document.select("li");

        return elements.stream().map(element -> {
            String name = element.text();

            String imgSrc = element.select("img").attr("src");
            boolean hasChildren = imgSrc.contains("plus");

            boolean selectable = !element.select("> a").isEmpty();

            String onClick = element.select("img").attr("onclick");
            Pattern idPattern = Pattern.compile("(\\d+)");
            Matcher idMatcher = idPattern.matcher(onClick);
            if (idMatcher.find()) {
                String id = idMatcher.group(1);

                return groupFactory.create(name, id, parent, hasChildren, selectable);
            } else {
                String href = element.select("a").attr("href");
                Pattern backupPattern = Pattern.compile("id=(\\d+)");
                Matcher backupMatcher = backupPattern.matcher(href);
                if (backupMatcher.find()) {
                    String id = backupMatcher.group(1);

                    return groupFactory.create(name, id, parent, hasChildren, selectable);
                } else {
                    throw new AssertionError();
                }
            }

        }).collect(Collectors.toList());
    }
}
