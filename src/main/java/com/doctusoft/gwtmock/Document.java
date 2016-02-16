package com.doctusoft.gwtmock;

import java.io.PrintWriter;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.FrameElement;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.SpanElement;

public class Document extends com.google.gwt.dom.client.Document {
	
	public static final Document Instance = new Document();
	
	protected Set<Element> elements = Sets.newHashSet();
	
	protected BodyElement body;
	
	public Element createMockElement(String tag) {
		Element element = null;
		if (DivElement.TAG.equals(tag)) {
			element = new DivElement();
		}
		if (ButtonElement.TAG.equals(tag)) {
			element = new ButtonElement();
		}
		if (BodyElement.TAG.equals(tag)) {
			element = new BodyElement();
		}
		if (SpanElement.TAG.equals(tag)) {
			element = new SpanElement();
		}
		if (InputElement.TAG.equals(tag)) {
			element = new InputElement();
		}
		if (FrameElement.TAG.equals(tag)) {
			element = new FrameElement();
		}
		if (IFrameElement.TAG.equals(tag)) {
			element = new IFrameElement();
		}
		if (element == null) {
			throw new UnsupportedOperationException("not yet supported " + tag);
		}
		element.setDocument(this);
		elements.add(element);
		return element;
	}
	
	@Override
	public Element getElementById(String elementId) {
		Preconditions.checkNotNull(elementId);
		for (Element element: elements) {
			if (elementId.equals(element.getId())) {
				return element;
			}
		}
		return null;
	}
	
	@Override
	public BodyElement getBody() {
		if (body == null) {
			body = new BodyElement();
			elements.add(body);
		}
		return body;
	}
	
	public void printFormatted(PrintWriter pw) {
		printFormatted(body, "", pw);
		pw.flush();
	}
	
	public void printFormatted(Element element, String indent, PrintWriter pw) {
		String tagName = element.getTagName();
		pw.println(indent + "<" + tagName + ">");
		for (Node node : element.getChildNodes()) {
			if (node instanceof Element) {
				printFormatted((Element) node, indent + "  ", pw);
			}
		}
		pw.println(indent + "</" + tagName + ">");
	}
}
