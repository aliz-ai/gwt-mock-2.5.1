package com.google.gwt.user.client.ui;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;

public class TestTextBoxEvents {

    @Test
    public void testTextBoxFocus() {
        TextBox field1 = new TextBox();
        field1.ensureDebugId("field1");

        TextBox field2 = new TextBox();
        field2.ensureDebugId("field2");

        FlowPanel content = new FlowPanel();
        content.add(field1);
        content.add(field2);
        RootPanel.get().add(content);

        FocusHandler focusHandler1 = Mockito.mock(FocusHandler.class);
        field1.addFocusHandler(focusHandler1);
        BlurHandler blurHandler1 = Mockito.mock(BlurHandler.class);
        field1.addBlurHandler(blurHandler1);

        FocusHandler focusHandler2 = Mockito.mock(FocusHandler.class);
        field2.addFocusHandler(focusHandler2);
        BlurHandler blurHandler2 = Mockito.mock(BlurHandler.class);
        field2.addBlurHandler(blurHandler2);

        InputElement i1 = (InputElement) Document.get().getElementById("gwt-debug-field1");
        i1.click();

        InputElement i2 = (InputElement) Document.get().getElementById("gwt-debug-field2");
        i2.focus();
        i1.click();
        
        InOrder inOrder = Mockito.inOrder(focusHandler1, blurHandler1, focusHandler2, blurHandler2);

        inOrder.verify(focusHandler1).onFocus(Mockito.isA(FocusEvent.class));
        inOrder.verify(blurHandler1).onBlur(Mockito.isA(BlurEvent.class));
        inOrder.verify(focusHandler2).onFocus(Mockito.isA(FocusEvent.class));
        inOrder.verify(blurHandler2).onBlur(Mockito.isA(BlurEvent.class));

    }
}
