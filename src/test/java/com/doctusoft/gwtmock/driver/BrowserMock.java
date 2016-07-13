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

    /**
     * @param elementLocator
     *            Element Debug ID with (optional "gwt-debug-" prefix will be added)
     *            or search criteria to find element in DOM. Example: "text=Action Two"
     */
    public static void click(String elementLocator) {
        Element element = GwtMockFinder.findElement(elementLocator);
        assertFound(element, elementLocator);
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

    public static void type(String elementLocator, String value) {
        Element element = GwtMockFinder.findElement(elementLocator);
        assertFound(element, elementLocator);
        type(element, value);
    }

    public static void type(Element element, String value) {
        browserEnter();
        Preconditions.checkArgument(isDisplayed(element), "Can't type in hidden element " + element);
        DOMEventsMock.type((InputElement) element, value);
        browserExit();
    }

    public static void setValue(String elementLocator, String value) {
        Element element = GwtMockFinder.findElement(elementLocator);
        assertFound(element, elementLocator);
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
    public static boolean isElementPresent(String elementIdentifier) {
        Element element = GwtMockFinder.findElement(elementIdentifier);
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

    public static boolean isDisplayed(String elementLocator) {
        return isDisplayed(GwtMockFinder.findElement(elementLocator));
    }

    public static void assertPresent(String elementLocator) {
        boolean present = isElementPresent(elementLocator);
        if (!present) {
            System.out.println(Document.get().getBody().getInnerHTML());
        }
        Assert.assertTrue(elementLocator + " should exists", present);
    }

    public static void assertNotPresent(String elementLocator) {
        boolean present = isElementPresent(elementLocator);
        if (present) {
            System.out.println(Document.get().getBody().getInnerHTML());
        }
        Assert.assertFalse(elementLocator + " should not be present", present);
    }

    public static void assertVisible(String elementLocator) {
        Assert.assertTrue(elementLocator + " should be visible", isDisplayed(elementLocator));
    }

    public static void assertNotVisible(String elementLocator) {
        Assert.assertFalse(elementLocator + " should not be visible", isDisplayed(elementLocator));
    }
}
