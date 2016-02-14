package com.doctusoft.gwtmock;

import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;

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
		if (element == null) {
			throw new UnsupportedOperationException("not yet supported" + tag);
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
		}
		return body;
	}
	
}
