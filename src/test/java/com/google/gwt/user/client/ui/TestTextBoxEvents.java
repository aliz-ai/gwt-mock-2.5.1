package com.google.gwt.user.client.ui;

import static org.mockito.Mockito.never;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mockito;

import com.doctusoft.gwtmock.GWTMock;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

public class TestTextBoxEvents {

    @Before
    public void setup() {
        GWTMock.reset();
    }
    
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

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void testTextBoxValueChange() {
        TextBox field1 = new TextBox();
        field1.ensureDebugId("field1");
        RootPanel.get().add(field1);

        TextBox field2 = new TextBox();
        field2.ensureDebugId("field2");
        RootPanel.get().add(field2);

        ValueChangeHandler<String> valueChangeHandler = Mockito.mock(ValueChangeHandler.class);
        field1.addValueChangeHandler(valueChangeHandler);

        // See that events not fired
        {
            field1.setValue("v1");
            Mockito.verify(valueChangeHandler, never()).onValueChange(Mockito.isA(ValueChangeEvent.class));
        }

        // Set value in wrapper trigger events immediately
        {
            ArgumentCaptor<ValueChangeEvent> valueChangeArguments = ArgumentCaptor.forClass(ValueChangeEvent.class);
            field1.setValue("v2", true);
            Mockito.verify(valueChangeHandler).onValueChange(valueChangeArguments.capture());
            Assert.assertEquals("v2", valueChangeArguments.getValue().getValue());
        }

        Mockito.reset(valueChangeHandler);
        InputElement i1 = (InputElement) Document.get().getElementById("gwt-debug-field1");
        InputElement i2 = (InputElement) Document.get().getElementById("gwt-debug-field2");

        // Set value using API, will not trigger value events until focus lost
        {
            i1.setValue("v3");
            Mockito.verify(valueChangeHandler, never()).onValueChange(Mockito.isA(ValueChangeEvent.class));

            i2.focus();
            Mockito.verify(valueChangeHandler, never()).onValueChange(Mockito.isA(ValueChangeEvent.class));
            
            Assert.assertEquals("v3", field1.getValue());
        }
        
        Mockito.reset(valueChangeHandler);
        // Simulate set value in native element (like Selenium 'sendKeys') will not trigger value events until focus lost
        {
            FocusHandler focusHandler1 = Mockito.mock(FocusHandler.class);
            field1.addFocusHandler(focusHandler1);
            BlurHandler blurHandler1 = Mockito.mock(BlurHandler.class);
            field1.addBlurHandler(blurHandler1);

            
            GWTMock.type(i1, "v4");
            Assert.assertEquals("v4", field1.getValue());
            Mockito.verify(valueChangeHandler, never()).onValueChange(Mockito.isA(ValueChangeEvent.class));
            Mockito.verify(focusHandler1).onFocus(Mockito.isA(FocusEvent.class));
            
            ArgumentCaptor<ValueChangeEvent> valueChangeArguments = ArgumentCaptor.forClass(ValueChangeEvent.class);
            i2.focus();
            Mockito.verify(valueChangeHandler).onValueChange(valueChangeArguments.capture());
            Assert.assertEquals("v4", valueChangeArguments.getValue().getValue());

            InOrder inOrder = Mockito.inOrder(valueChangeHandler, blurHandler1);
            inOrder.verify(valueChangeHandler).onValueChange(Mockito.isA(ValueChangeEvent.class));
            inOrder.verify(blurHandler1).onBlur(Mockito.isA(BlurEvent.class));
        }

    }
}
