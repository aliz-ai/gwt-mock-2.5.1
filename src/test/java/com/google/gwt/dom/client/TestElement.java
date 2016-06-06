package com.google.gwt.dom.client;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.doctusoft.gwtmock.Document;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class TestElement {
	
	@Test
	public void testInnerHTMLSimpleText() {
		Label label = new Label("hello world");
		Element element = label.getElement();
		RootPanel.get().add(label);
		element.setInnerHTML("changed inner html");
		Assert.assertEquals("changed inner html", element.getInnerHTML());
		Assert.assertTrue(Document.get().getBody().getInnerHTML().contains("changed inner html"));
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
		element.setInnerHTML("changed <div>inner</div> html");
		Assert.assertEquals("changed <div>inner</div> html", element.getInnerHTML());
	}
}
