package org.personalized.dashboard.utils.htmltidy;

import org.apache.commons.lang3.StringUtils;
import org.w3c.tidy.Tidy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by sudan on 18/7/15.
 */
public class DOMParser {

    public String removeMalformedTags(String html) throws UnsupportedEncodingException {

        if(StringUtils.isEmpty(html))
            return StringUtils.EMPTY;

        Tidy tidy = new Tidy();
        tidy.setDropEmptyParas(true);
        tidy.setXHTML(true);
        tidy.setXmlOut(true);
        tidy.setShowWarnings(false);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes("UTF-8"));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        tidy.parseDOM(inputStream, outputStream);
        return outputStream.toString("UTF-8");
    }
}
