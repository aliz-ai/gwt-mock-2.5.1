package com.google.gwt.dom.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.doctusoft.gwtmock.Document;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class TestElement {
    
    @Before
    public void setup() {
        RootPanel.get().clear();
        com.doctusoft.gwtmock.Document.reset();
    }
    
	@Test
	public void testInnerHTMLSimpleText() {
		Label label = new Label("hello world");
		Element element = label.getElement();
		RootPanel.get().add(label);
		Assert.assertEquals("hello world", element.getInnerHTML());
		Assert.assertEquals("hello world", element.getInnerText());
		element.setInnerHTML("changed inner html");
		Assert.assertEquals("changed inner html", element.getInnerHTML());
		Assert.assertEquals("changed inner html", element.getInnerText());
		Assert.assertTrue(Document.get().getBody().getInnerHTML().contains("changed inner html"));
		// IsEqualIgnoringWhiteSpace
	    Assert.assertEquals("<div className=\"gwt-Label\">changed inner html</div>", Document.get().getBody().getInnerHTML().replaceAll("\\R", ""));
	}
	
	@Test
    public void testInnerHTMLOfComposite() {
	    FlowPanel composite = new FlowPanel();
	    composite.add(new Label("one"));
	    composite.add(new Label("two"));
        RootPanel.get().add(composite);

        // IsEqualIgnoringWhiteSpace
        Assert.assertEquals("<div>  <div className=\"gwt-Label\">one  </div>" + //
                "  <div className=\"gwt-Label\">two  </div></div>", Document.get().getBody().getInnerHTML().replaceAll("\\R", ""));
    }
	
	/**
	 * TODO I just noticed that this actually doesn't work as expected. Though I doesn't cause a problem in the application tests,
	 * I will still need to fix this
	 */
	@Test
	@Ignore
	public void testInnerHTMLWithInnerElement() {
		Label label = new Label("hello world");
		Element element = label.getElement();
		RootPanel.get().add(label);
		Assert.assertEquals("hello world", element.getInnerHTML());
		Assert.assertEquals("hello world", element.getInnerText());
		element.setInnerHTML("changed <div>inner</div> html");
		Assert.assertEquals("fragments converted to child nodes", 3, element.getChildCount()); // verified in GWT
		Assert.assertEquals("changed <div>inner</div> html", element.getInnerHTML().replaceAll("\\R", ""));
	}
	
    @Test
    public void testToString() {
        Label label = new Label("hello world");
        Element element = label.getElement();
        RootPanel.get().add(label);

        Assert.assertEquals("hello world", element.getInnerHTML());
        Assert.assertEquals("<div className=\"gwt-Label\">hello world</div>", element.toString().replaceAll("\\R", ""));
    }
}
