package com.google.gwt.user.client.ui;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.core.client.impl.SchedulerImpl;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.Command;

public class TestMenuBar {

    @Before
    public void setup() {
       // GWTMock.reset();
    }
    
    @Test
    public void testMenuBar() {
        Command command = Mockito.mock(Command.class);

        MenuBar bar = new MenuBar(true);
        MenuItem item = bar.addItem("Item One", command);
        item.ensureDebugId("menuItemOne");
        
        RootPanel.get().add(bar);
        
        Element element =  Document.get().getElementById("gwt-debug-menuItemOne");
        Assert.assertNotNull("Menu Item created", element);
    
        //TODO Events tests will be added later once the decision will be made on where we have a driver class
        //DOM fireEventBubbling(element, ClickEvent.getType());
        //SchedulerImpl.INSTANCE.executeDeferredCommands();
        //Mockito.verify(command).execute();
    }
    
}
