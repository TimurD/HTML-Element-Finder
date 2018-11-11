package com.tymurd.finder.utils;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

public class PathCreator {

    private static final String CLASS_DELIMITER = ".";
    private static final String ID_DELIMITER = "#";
    private static final String TAG_DELIMITER = "/";
    private static final String ID_ATTRIBUTE_KEY = "id";
    private static final String CLASS_ATTRIBUTE_KEY = "class";

    public static String getPath(Element button) {
        StringBuilder path = new StringBuilder(StringUtils.EMPTY);
        for (Element element : button.parents()) {
            path.insert(0, element.nodeName() + getIdForTag(element.attr(ID_ATTRIBUTE_KEY)) +
                    getClassForTag(element.attr(CLASS_ATTRIBUTE_KEY)) + TAG_DELIMITER);
        }
        path.append(button.nodeName()).append(getIdForTag(button.attr(ID_ATTRIBUTE_KEY) +
                getClassForTag(button.attr(CLASS_ATTRIBUTE_KEY))));
        return path.toString();
    }

    private static String getIdForTag(String id) {
        return StringUtils.isEmpty(id) ? StringUtils.EMPTY : ID_DELIMITER + id;
    }

    private static String getClassForTag(String classAttr) {
        return StringUtils.isEmpty(classAttr) ? StringUtils.EMPTY : CLASS_DELIMITER + classAttr;
    }
}