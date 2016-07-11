package com.google.gwt.dom.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.doctusoft.gwtmock.Document;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class TestElement {
    
    @Before
    public void setup() {
        RootPanel.get().clear();
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
	
	@Test
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
    public void testInnerHTMLWithComplexInnerElements() {
        Label label = new Label();
        Element element = label.getElement();
        RootPanel.get().add(label);
        element.setInnerHTML("L1.1<div>L2.1<div>L3.1</div>L2.2<div>L3.2</div>L2.3</div>L1.2");
        Assert.assertEquals("fragments converted to child nodes", 3, element.getChildCount()); 
        Assert.assertEquals("fragments converted to child nodes", 5, element.getChild(1).getChildCount()); 
        Assert.assertEquals("L1.1<div>L2.1  <div>L3.1  </div>L2.2  <div>L3.2  </div>L2.3</div>L1.2", element.getInnerHTML().replaceAll("\\R", ""));
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
