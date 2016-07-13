package com.doctusoft.gwtmock;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;

public class MockEventTargetElement extends EventTarget {
    
    Element element;

    public MockEventTargetElement(Element element) {
        super();
        this.element = element;
    }

    @SuppressWarnings("unchecked")
    public <T> T cast() {
        return (T) element;
    }

}
