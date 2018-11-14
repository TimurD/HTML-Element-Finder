package com.tymurd.finder.finder;

import com.tymurd.finder.utils.PathCreator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HtmlElementFinder {

    private static final Logger LOGGER = LoggerFactory.getLogger(HtmlElementFinder.class);

    private static final String CHARSET_NAME = "utf8";
    private static final Integer ALLOWED_SIMILARITY_PERCENT = 50;
    private static final String PATH_ATTRIBUTE_KEY = "path";
    private static final String ID_ATTRIBUTE_KEY = "id";
    private static final String TAG_CONTENT_ATTRIBUTE_KEY = "tagContent";


    public Map<String, String> findSimilarElement(String originFile, String sampleFile, String targetId) {
        String cssQuery = "*[id=\"" + targetId + "\"]";

        Elements originalElements = findElementsByQuery(new File(originFile), cssQuery);
        Map<String, String> originalElementsAttrs = getOriginalElementsAttrs(originalElements);
        String attrType = originalElements.get(0).nodeName();
        removeNotAffectingComparisonElements(originalElementsAttrs);

        Elements sampleElements = findElementsByQuery(new File(sampleFile), attrType);
        List<Map<String, String>> sampleElementsAttrsList = generateMapWithAttributes(sampleElements);

        return generateResultList(originalElementsAttrs, sampleElementsAttrsList);
    }

    private Map<String, String> generateResultList(Map<String, String> originalElementsAttrs,
                                                   List<Map<String, String>> sampleElementsAttrs) {
        Map<String, Double> resultList = new HashMap<>();
        int elementsCount = originalElementsAttrs.size();
        for (Map<String, String> sampleElementsAttrsOpt : sampleElementsAttrs) {
            double similarityNumbers = 0;
            for (Map.Entry<String, String> entry : originalElementsAttrs.entrySet()) {
                if (entry.getValue().equals(sampleElementsAttrsOpt.get(entry.getKey()))) {
                    similarityNumbers++;
                }
                resultList.put(sampleElementsAttrsOpt.get(PATH_ATTRIBUTE_KEY), similarityNumbers / elementsCount * 100);
            }
        }
        return resultList.entrySet().stream().filter(e -> e.getValue() > ALLOWED_SIMILARITY_PERCENT)
                .collect(Collectors.toMap(Map.Entry::getKey, e -> String.format("%.2f%%", e.getValue())));
    }

    private List<Map<String, String>> generateMapWithAttributes(Elements elements) {
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

    private Map<String, String> getOriginalElementsAttrs(Elements originalElements) {
        List<Map<String, String>> originalElementsAttrsList = generateMapWithAttributes(originalElements);
        if (!originalElementsAttrsList.isEmpty()) {
            return originalElementsAttrsList.get(0);
        } else {
            LOGGER.error("No item found with such ID");
            throw new RuntimeException();
        }
    }

    private void removeNotAffectingComparisonElements(Map<String, String> originalElementsAttrs) {
        originalElementsAttrs.remove(PATH_ATTRIBUTE_KEY);
        originalElementsAttrs.remove(ID_ATTRIBUTE_KEY);
    }

    private Elements findElementsByQuery(File htmlFile, String cssQuery) {
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
