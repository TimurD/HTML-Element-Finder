package com.agileengine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.agileengine.utils.PathCreator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsoupCssSelectSnippet {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsoupCssSelectSnippet.class);

    private static final String CHARSET_NAME = "utf8";
    private static final String CSS_QUERY = "*[id=\"make-everything-ok-button\"]";
    private static final int ALLOWED_SIMILARITY_PERCENT = 50;
    private static final String PATH_ATTRIBUTE_KEY = "path";
    private static final String ID_ATTRIBUTE_KEY = "id";
    private static final String TAG_CONTENT_ATTRIBUTE_KEY = "tagContent";

    public static void main(String[] args) {
        Elements originalElements = findElementsByQuery(new File(args[0]), CSS_QUERY);
        String attrType = originalElements.get(0).nodeName();
        Elements sampleElements = findElementsByQuery(new File(args[1]), attrType);

        Map<String, String> originalElementsAttrs = generateMapWithAttributes(originalElements).get(0);
        List<Map<String, String>> sampleElementsAttrs = generateMapWithAttributes(sampleElements);

        Map<String, String> resultList = generateResultList(originalElementsAttrs, sampleElementsAttrs);

        LOGGER.info("Found " + resultList.size() + " element(s):");
        resultList.forEach((k, v) -> LOGGER.info("path = " + k + ", similarity = " + v));
    }

    private static Map<String, String> generateResultList(Map<String, String> originalElementsAttrs,
                                                          List<Map<String, String>> sampleElementsAttrs) {
        Map<String, String> resultList = new HashMap<>();
        originalElementsAttrs.remove(PATH_ATTRIBUTE_KEY);
        originalElementsAttrs.remove(ID_ATTRIBUTE_KEY);
        int elementsCount = originalElementsAttrs.size();
        for (Map<String, String> sampleElementsAttrsOpt : sampleElementsAttrs) {
            double similarityNumbers = 0;
            for (Map.Entry<String, String> entry : originalElementsAttrs.entrySet()) {
                if (entry.getValue().equals(sampleElementsAttrsOpt.get(entry.getKey()))) {
                    similarityNumbers++;
                }
                double similarityCof = similarityNumbers / elementsCount * 100;
                if (similarityCof >= ALLOWED_SIMILARITY_PERCENT) {
                    resultList.put(sampleElementsAttrsOpt.get(PATH_ATTRIBUTE_KEY), String.format("%.2f%%", similarityCof));
                }
            }
        }
        return resultList;
    }

    private static List<Map<String, String>> generateMapWithAttributes(Elements elements) {
        List<Map<String, String>> attributeValues = new ArrayList<>();
        elements.forEach(buttons ->
        {
            Map<String, String> stringifiedAttrs = new HashMap<>();
            stringifiedAttrs.put(TAG_CONTENT_ATTRIBUTE_KEY, buttons.text());
            stringifiedAttrs.put(PATH_ATTRIBUTE_KEY, PathCreator.getPath(buttons));
            buttons.attributes().asList().forEach(
                    attr -> stringifiedAttrs.put(attr.getKey(), attr.getValue()));
            attributeValues.add(stringifiedAttrs);
        });
        return attributeValues;
    }

    private static Elements findElementsByQuery(File htmlFile, String cssQuery) {
        try {
            Document doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());
            return doc.select(cssQuery);
        } catch (IOException e) {
            LOGGER.error("Error reading [{}] file", htmlFile.getAbsolutePath(), e);
            throw new RuntimeException(e);
        }
    }
}