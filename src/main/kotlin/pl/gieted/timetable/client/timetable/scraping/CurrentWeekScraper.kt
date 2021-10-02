package pl.gieted.timetable.client.timetable.scraping

import org.jsoup.Jsoup
import javax.inject.Inject

class CurrentWeekScraper @Inject constructor() : Scraper<Int> {

    override fun scrape(timetableHtml: String): Int {
        val document = Jsoup.parse(timetableHtml)
        val a = document.selectFirst("#files .data a") ?: throw ParsingException("Cannot parse current week")

        return """w=(\d+)""".toRegex().find(a.attr("href"))?.groups?.get(1)?.value?.toInt()
            ?: throw ParsingException("Cannot parse current week")
    }
}
