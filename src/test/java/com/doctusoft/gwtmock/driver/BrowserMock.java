package com.doctusoft.gwtmock.driver;

import org.junit.Assert;

import com.doctusoft.gwtmock.GWTMock;
import com.google.common.base.Preconditions;
import com.google.gwt.core.client.impl.SchedulerImpl;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.user.client.ui.UIObject;


public class BrowserMock {

    public static void setUp() {
        GWTMock.reset();
        DOMEventsMock.reset();
    }

    public static void click(String paramString) {
        Element element = GwtMockFinder.findElement(paramString);
        assertFound(element, paramString);
        click(element);
    }

    public static void click(Element element) {
        Preconditions.checkArgument(isDisplayed(element), "Can't Click on hidden element " + element);
        browserEnter();
        DOMEventsMock.click(element);
        browserExit();
    }

    public static void focus(Element element) {
        browserEnter();
        DOMEventsMock.focus(element);
        browserExit();
    }

    public static void type(String paramString, String value) {
        Element element = GwtMockFinder.findElement(paramString);
        assertFound(element, paramString);
        type(element, value);
    }

    public static void type(Element element, String value) {
        browserEnter();
        Preconditions.checkArgument(isDisplayed(element), "Can't type in hidden element " + element);
        DOMEventsMock.type((InputElement) element, value);
        browserExit();
    }

    public static void setValue(String paramString, String value) {
        Element element = GwtMockFinder.findElement(paramString);
        assertFound(element, paramString);
        setValue(element, value);
    }

    public static void setValue(Element element, String value) {
        if (element instanceof InputElement) {
            ((InputElement) element).setValue(value);
        } else if (element instanceof SelectElement) {
            ((SelectElement) element).setValue(value);
        }
    }

    private static void assertFound(Element element, String paramString) {
        if (element == null) {
            System.out.println(Document.get().getBody().getInnerHTML());
        }
        Assert.assertNotNull("Element not found " + paramString, element);
    }

    public static void executeDeferredCommands() {
        SchedulerImpl.INSTANCE.executeDeferredCommands();
    }

    private static void browserExit() {
        executeDeferredCommands();
    }

    private static void browserEnter() {
    }

    // --- asserts
    public static boolean isElementPresent(String paramString) {
        Element element = GwtMockFinder.findElement(paramString);
        return element != null;
    }

    public static boolean isDisplayed(Element element) {
        if (element != null) {
            while (element.hasParentElement()) {
                if (!UIObject.isVisible(element)) {
                    return false;
                }
                element = element.getParentElement();
            }
            return UIObject.isVisible(element);
        } else {
            return false;
        }
    }

    public static boolean isDisplayed(String paramString) {
        return isDisplayed(GwtMockFinder.findElement(paramString));
    }

    public static void assertPresent(String locator) {
        boolean present = isElementPresent(locator);
        if (!present) {
            System.out.println(Document.get().getBody().getInnerHTML());
        }
        Assert.assertTrue(locator + " should exists", present);
    }

    public static void assertNotPresent(String locator) {
        boolean present = isElementPresent(locator);
        if (present) {
            System.out.println(Document.get().getBody().getInnerHTML());
        }
        Assert.assertFalse(locator + " should not be present", present);
    }

    public static void assertVisible(String locator) {
        Assert.assertTrue(locator + " should be visible", isDisplayed(locator));
    }

    public static void assertNotVisible(String locator) {
        Assert.assertFalse(locator + " should not be visible", isDisplayed(locator));
    }
}
