package com.google.gwt.user.client.ui.events;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import com.doctusoft.gwtmock.driver.BrowserMock;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;


public class TestEventBubbling {

    @Before
    public void setup() {
        BrowserMock.setUp();
    }

    @Test
    public void testClickEventBubbling() {
        Label field1 = new Label();
        field1.ensureDebugId("label1");

        FocusPanel content = new FocusPanel();
        content.add(field1);
        RootPanel.get().add(content);

        ClickHandler childClickHandler = Mockito.mock(ClickHandler.class);
        field1.addClickHandler(childClickHandler);

        ClickHandler parentClickHandler = Mockito.mock(ClickHandler.class);
        content.addClickHandler(parentClickHandler);

        BrowserMock.click(field1.getElement());

        InOrder inOrder = Mockito.inOrder(childClickHandler, parentClickHandler);
        inOrder.verify(childClickHandler).onClick(Mockito.isA(ClickEvent.class));
        inOrder.verify(parentClickHandler).onClick(Mockito.isA(ClickEvent.class));
    }
}
