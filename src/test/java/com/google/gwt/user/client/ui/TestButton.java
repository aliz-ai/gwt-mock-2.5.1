package com.google.gwt.user.client.ui;

import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class TestButton {
	
	@Test
	public void testButtonClick() {
		Button button = new Button("hello world");
		button.getElement().setId("button");
		ClickHandler clickHandler = Mockito.mock(ClickHandler.class);
		button.addClickHandler(clickHandler);
		RootPanel.get().add(button);
		ButtonElement x = (ButtonElement) Document.get().getElementById("button");
		x.click();
		Mockito.verify(clickHandler).onClick(Mockito.isA(ClickEvent.class));
	}
}
