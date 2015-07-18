package org.personalized.dashboard.utils.htmltidy;


import org.owasp.html.Sanitizers;

/**
 * Created by sudan on 18/7/15.
 */
public class DOMParser {

    public String removeMalformedTags(String html) {

        return (Sanitizers.FORMATTING.and(Sanitizers.BLOCKS).and(Sanitizers.IMAGES).sanitize(html));
    }
}
