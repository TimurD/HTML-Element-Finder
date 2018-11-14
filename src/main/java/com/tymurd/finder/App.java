package com.tymurd.finder;

import com.tymurd.finder.finders.HtmlElementFinder;
import com.tymurd.finder.models.SimilarElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        HtmlElementFinder htmlElementFinder = new HtmlElementFinder();
        printResult(htmlElementFinder.findSimilarElement(args[0], args[1], args[2]));
    }

    private static void printResult(List<SimilarElement> resultList) {
        LOGGER.info(String.format("Found %d element(s):", resultList.size()));
        resultList.forEach(se -> LOGGER.info(se.toString()));
    }
}