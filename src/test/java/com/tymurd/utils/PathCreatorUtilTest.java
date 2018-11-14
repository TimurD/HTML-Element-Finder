package com.tymurd.utils;

import com.tymurd.finder.utils.PathCreatorUtil;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PathCreatorUtilTest {

    private static final String TEST_PATH =
            "<div class=\"panel-body\">" +
                    "<a id=\"make-everything-ok-button\" class=\"btn btn-success\" href=\"#ok\" title=\"Make-Button\"" +
                    " rel=\"next\" onclick=\"javascript:window.okDone(); return false;\">" +
                    "Make everything OK" +
                    "</a>" +
                    "</div>";
    private static final String EXPECTED_RESULT = "html/body/div.panel-body/a#make-everything-ok-button.btn btn-success";

    @Test
    public void pathGenerationTest() {
        Elements testPathElement = Jsoup.parse(TEST_PATH).select("a");
        assertEquals(EXPECTED_RESULT, PathCreatorUtil.getPath(testPathElement.first()));
    }
}