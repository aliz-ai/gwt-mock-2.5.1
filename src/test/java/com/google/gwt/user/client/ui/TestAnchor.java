package com.google.gwt.user.client.ui;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.doctusoft.gwtmock.GWTMock;
import com.doctusoft.gwtmock.driver.BrowserMock;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class TestAnchor {

    @Before
    public void setup() {
        GWTMock.reset();
    }

    @Test
    public void testAnchorPresent() {
        RootPanel.get().add(new Anchor("hello world"));
        Assert.assertTrue("The label is rendered into the DOM", com.google.gwt.dom.client.Document.get().getBody().getInnerHTML().contains("hello world"));
    }

    @Test
    public void testTextAttribute() {
        Anchor anchor = new Anchor("hello world");
        RootPanel.get().add(anchor);
        Assert.assertEquals("hello world", anchor.getText());
        anchor.setText("changed");
        Assert.assertEquals("changed", anchor.getText());
    }

    @Test
    public void testAnchorClick() {
        Anchor anchor = new Anchor("hello anchor");
        anchor.ensureDebugId("anchor1");

        ClickHandler clickHandler = Mockito.mock(ClickHandler.class);
        anchor.addClickHandler(clickHandler);
        RootPanel.get().add(anchor);

        BrowserMock.click("anchor1");
        Mockito.verify(clickHandler).onClick(Matchers.isA(ClickEvent.class));

    }

}
