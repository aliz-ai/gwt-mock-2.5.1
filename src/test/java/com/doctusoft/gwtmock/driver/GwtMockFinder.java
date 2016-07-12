package com.doctusoft.gwtmock.driver;

import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.Text;

public abstract class GwtMockFinder {

    public static String GWT_DEBUG_ID_PREFIX = "gwt-debug-";

    public static String by(String baseID) {
        if (baseID.startsWith(GWT_DEBUG_ID_PREFIX)) {
            return baseID;
        } else {
            return GWT_DEBUG_ID_PREFIX + baseID;
        }
    }

    public static String by(String baseID, String id) {
        if (baseID.startsWith(GWT_DEBUG_ID_PREFIX)) {
            return baseID;
        } else if (baseID.endsWith("-" + id)) {
            return GWT_DEBUG_ID_PREFIX + baseID;
        } else {
            return GWT_DEBUG_ID_PREFIX + baseID + "-" + id;
        }
    }

    public static Element findElement(String paramString) {
        Element element = null;

        String[] prams = paramString.split("=");
        if (prams.length > 1) {
            final String text = paramString.substring(paramString.indexOf('=') + 1);
            GwtMockFinder finder;
            if (prams[0].equals("text")) {
                finder = new GwtMockFinder() {
                    @Override
                    boolean matchElement(Element element) {
                        for (Node node : element.getChildNodes()) {
                            if (node instanceof Text) {
                                if (text.equals(((Text) node).getData())) {
                                    return true;
                                }
                            }
                        }
                        return false;
                    }
                };
//            } else if (prams[0].equals("link")) {
//                return By.linkText(text);
//            } else if (prams[0].equals("id")) {
//                return By.id(text);
//            } else if (prams[0].equals("name")) {
//                return By.name(text);
//            } else if (prams[0].equals("css")) {
//                return By.cssSelector(text);
//            } else if (prams[0].equals("class")) {
//                return By.className(text);
//            } else if (prams[0].equals("xpath")) {
//                return By.xpath(text);
            } else {
                throw new Error("Unsupported Element Locator '" + prams[0] + "'");
            }
            element = find(Document.get().getBody().getChildElements(), finder);
        } else {
            element = Document.get().getElementById(by(paramString));
        }
        return element;
    }

    private static Element find(List<Element> elements, GwtMockFinder finder) {
        for (Element element : elements) {
            if (finder.matchElement(element)) {
                return element;
            }
            Element child = find(element.getChildElements(), finder);
            if (child != null) {
                return child;
            }
        }
        return null;
    }

    abstract boolean matchElement(Element element);
}
