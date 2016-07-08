package com.google.gwt.user.client.ui;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.doctusoft.gwtmock.Document;
import com.doctusoft.gwtmock.GWTMock;

public class TestLabel {
	
    @Before
    public void setup() {
        GWTMock.reset();
    }
    
	@Test
	public void testLabelPresent() {
		RootPanel.get().add(new Label("hello world"));
		Assert.assertTrue("The label is rendered into the DOM", Document.get().getBody().getInnerHTML().contains("hello world"));
	}
	
	@Test
	public void testTextAttribute() {
		Label label = new Label("hello world");
		RootPanel.get().add(label);
		Assert.assertEquals("hello world", label.getText());
		label.setText("changed");
		Assert.assertEquals("changed", label.getText());
	}
}
