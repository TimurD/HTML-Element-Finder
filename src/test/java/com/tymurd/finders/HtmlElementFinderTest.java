package com.tymurd.finders;

import com.tymurd.finder.finders.HtmlElementFinder;
import com.tymurd.finder.models.SimilarElement;

import org.junit.Test;

import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HtmlElementFinderTest {

    private static final String ORIGIN_FILE_PATH = "/samples/sample-0-origin.html";
    private static final String SAMPLE_FILE_PATH = "/samples/sample-1-evil-gemini.html";
    private static final String TARGET_ID_NAME = "make-everything-ok-button";
    private static final int EXPECTED_LIST_SIZE = 1;
    private static final Double EXPECTED_SIMILARITY_PERCENT = 80d;
    private static final String EXPECTED_PATH =
            "html/body/div#wrapper/div#page-wrapper/div.row/div.col-lg-8" +
                    "/div.panel panel-default/div.panel-body/a.btn btn-success";

    @Test
    public void findSimilarElementsTest() {
        SimilarElement similarElement = new SimilarElement(EXPECTED_PATH, EXPECTED_SIMILARITY_PERCENT);
        URL originUrl = getClass().getResource(ORIGIN_FILE_PATH);
        URL sampleUrl = getClass().getResource(SAMPLE_FILE_PATH);
        HtmlElementFinder htmlElementFinder = new HtmlElementFinder();
        List<SimilarElement> similarElements = htmlElementFinder
                .findSimilarElement(originUrl.getFile(), sampleUrl.getFile(), TARGET_ID_NAME);
        similarElements.forEach(System.out::println);
        assertEquals(EXPECTED_LIST_SIZE, similarElements.size());
        assertEquals(similarElement, similarElements.get(0));
    }
}