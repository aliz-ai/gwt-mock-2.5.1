package com.google.gwt.user.client.ui;

import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.doctusoft.gwtmock.GWTMock;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;

public class TestListBox {

    @Before
    public void setup() {
        GWTMock.reset();
    }
    
    @Test
    public void tesListBox() {
        ListBox lb = new ListBox();
        lb.ensureDebugId("listBox1");
        RootPanel.get().add(lb);

        lb.addItem("item0");
        lb.addItem("item1");
        lb.addItem("item2");
        lb.addItem("item3" , "3");

        Assert.assertEquals("getItemCount", 4, lb.getItemCount());
        
        Assert.assertEquals("getItemText", "item0", lb.getItemText(0));
        Assert.assertEquals("getValue", "item0", lb.getValue(0));
        
        Assert.assertEquals("getItemText", "item1", lb.getItemText(1));
        Assert.assertEquals("getValue", "item1", lb.getValue(1));
        
        Assert.assertEquals("getItemText", "item3", lb.getItemText(3));
        Assert.assertEquals("getValue", "3", lb.getValue(3));

        Assert.assertEquals("getSelectedIndex", 0, lb.getSelectedIndex());
        lb.setSelectedIndex(2);
        Assert.assertEquals("getSelectedIndex", 2, lb.getSelectedIndex());
        //Assert.assertTrue("isItemSelected", lb.isItemSelected(2));

    }
    
    @Test
    public void testListBoxUserInput() {
        ListBox lb = new ListBox();
        lb.ensureDebugId("listBox2");
        RootPanel.get().add(lb);

        lb.addItem("item0", "v0");
        lb.addItem("item1", "v1");

        ChangeHandler changeHandler = Mockito.mock(ChangeHandler.class);
        lb.addChangeHandler(changeHandler);

        selectByVisibleText(Document.get().getElementById("gwt-debug-listBox2"), "item1");

        Mockito.verify(changeHandler).onChange(Mockito.isA(ChangeEvent.class));

        Assert.assertEquals("getSelectedIndex", 1, lb.getSelectedIndex());
    }
    
    /**
     * Select all options that display text matching the argument.
     * Simulates browser user input behavior on ListBox element.
     * 
     * Will trigger ChangeEvent event. 
     *
     * @param text The visible text to match against
     * @throws NoSuchElementException If no matching option elements are found
     */
    private static void selectByVisibleText(Element element, String text) {
        SelectElement selectElement = (SelectElement)element;
        selectElement.focus();
        for (int i = 0; i < selectElement.getOptions().getLength(); i++) {
          if (text.equals(selectElement.getOptions().getItem(i).getText()))  {
              selectElement.setSelectedIndex(i);
              selectElement.fireEvent(ChangeEvent.getType());
              return;
          }
        }
        throw new NoSuchElementException("Cannot locate option with text: " + text);
    }
}
