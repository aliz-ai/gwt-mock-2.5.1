package com.doctusoft.gwtmock.driver;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DomEvent.Type;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;

class DOMEventsMock {

    static final String MOCK_TYPED_VALUE = "mock.value.new";

    private static class DOMState {
        // Element In Focus (some browsers actually have working implementation document.activeElement)
        Element activeElement;
    }

    private static DOMState state = new DOMState();

    static DOMState getState() {
        return state;
    }

    static void reset() {
        state = new DOMState();
    }

    public static void click(Element element) {
        switchFocusTo(element);
        //element.fireEvent(ClickEvent.getType());
        fireEventBubbling(element, ClickEvent.getType());
    }

    public static void focus(Element element) {
        switchFocusTo(element);
    }

    /**
     * Use this method to simulate typing into an element, which will set its value.
     * Simulates browser behavior as in Selenium's sendKeys().
     * The new value will be available in calls to element.getValue(). But it will not trigger ValueChange events until focus lost.
     *
     * @param value
     *            characters to send to the element
     */
    public static void type(InputElement element, String value) {
        focus(element);
        element.setAttribute(MOCK_TYPED_VALUE, value);
        element.setValue(value);
    }

    private static void switchFocusTo(Element element) {
        Element activeElement = getState().activeElement;
        if (activeElement == element) {
            return;
        } else {
            if (activeElement != null && isAttached(activeElement)) {
                if (activeElement instanceof InputElement) {
                    mockOnBeforeBlur(((InputElement) activeElement));
                }
                activeElement.fireEvent(BlurEvent.getType());
            }
            element.fireEvent(FocusEvent.getType());
            getState().activeElement = element;
        }
    }

    private static boolean isAttached(Element elem) {
        return DOM.getEventListener(elem) != null;
    }

    /**
     * Fire ValueChange events if value was typed.
     */
    private static void mockOnBeforeBlur(InputElement elem) {
        String newValue = elem.getAttribute(MOCK_TYPED_VALUE);
        if (newValue != null) {
            elem.removeAttribute(MOCK_TYPED_VALUE);
            elem.fireEvent(ChangeEvent.getType());
        }
    }

    /**
     * The event will be fired to the nearest {@link EventListener} specified on any of the
     * element's parents. See Widget.addDomHandler and sinkEvents.
     *
     * @param starting
     *            element.
     * @param eventType
     */
    public static void fireEventBubbling(Element element, Type<?> eventType) {
        Event event = new Event(eventType.getName());
        // event.__eventTarget = new EventTarget.MockEventTargetElement(element);
        Element eventTarget = element;
        // while (!event.isPropagationStopped()) {
        Element currentEventTarget = getFirstAncestorWithListener(eventType, eventTarget);
        if (currentEventTarget == null) {
            return;
        }

        getEventListener(eventType, currentEventTarget).onBrowserEvent(event);
        //  continue if event.stopPropagation() was not called.
        if (currentEventTarget.getParentNode() != null) {
            eventTarget = currentEventTarget.getParentNode().cast();
        }
        // }
    }

    private static Element getFirstAncestorWithListener(Type<?> eventType, Element curElem) {
        while (curElem != null && getEventListener(eventType, curElem) == null) {
            if (curElem.getParentNode() != null) {
                curElem = curElem.getParentNode().cast();
            } else {
                return null;
            }
        }
        return curElem;
    }

    private static EventListener getEventListener(Type<?> eventType, Element elem) {
        if ((elem.__eventBits & Event.getTypeInt(eventType.getName())) != 0) {
            return elem.__listener;
        } else {
            return null;
        }
    }
    // end EventBubbling Fragment
}
