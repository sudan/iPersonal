package org.personalized.dashboard.utils.htmltidy;


import org.jsoup.Jsoup;
import org.owasp.html.Sanitizers;

/**
 * Created by sudan on 18/7/15.
 */
public class DOMParser {

    public String removeMalformedTags(String html) {

        return (Sanitizers.FORMATTING.and(Sanitizers.BLOCKS).and(Sanitizers.IMAGES).sanitize(html));
    }

    public String extractSummary(String html) {
        html = Jsoup.parse(html).text();
        return html.substring(0, Math.min(html.length(), 50));
    }
}
