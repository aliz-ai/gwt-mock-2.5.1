package x;

import org.junit.Assert;
import org.junit.Test;

import com.doctusoft.gwtmock.Document;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class TestLabel {
	
	@Test
	public void x() {
		final Label label = new Label("xx");
		label.getElement().setId("label");
		Button button = new Button("hello world");
		button.getElement().setId("button");
		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				label.setText("changed text");
			}
		});
		RootPanel.get().add(button);
		ButtonElement x = (ButtonElement) Document.Instance.getElementById("button");
		x.click();
		Element y = Document.Instance.getElementById("label");
		Assert.assertEquals("changed text", y.getInnerText());
	}

}
