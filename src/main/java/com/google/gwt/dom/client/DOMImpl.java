/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.dom.client;

import java.util.List;

import com.google.common.base.MoreObjects;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;

public class DOMImpl {
	
	static final DOMImpl impl = new DOMImpl();
	
	public void buttonClick(ButtonElement button) {
		button.fireEvent(ClickEvent.getType());
	}
	
	public ButtonElement createButtonElement(Document doc, String type) {
		return (ButtonElement) ((com.doctusoft.gwtmock.Document) doc).createMockElement(ButtonElement.TAG);
	}
	
	public InputElement createCheckInputElement(Document doc) {
		InputElement ie = (InputElement) com.doctusoft.gwtmock.Document.Instance.createMockElement("input");
		ie.setAttribute("type", "checkbox");
		ie.setValue("on");
		return ie;
	}
	
	/*-{
	 var e = doc.createElement("INPUT");
	 e.type = 'checkbox';
	 e.value = 'on';
	 return e;
	}-*/
	
	public Element createElement(Document doc, String tag) {
		return ((com.doctusoft.gwtmock.Document) doc).createMockElement(tag);
	}
	
	public NativeEvent createHtmlEvent(Document doc, String type,
			boolean canBubble, boolean cancelable) {
		// TODO
		return new NativeEvent();
	}
	
	public InputElement createInputElement(Document doc, String type) {
		return (InputElement) ((com.doctusoft.gwtmock.Document) doc).createMockElement("input");
	}
	
	public InputElement createInputRadioElement(Document doc, String name) {
		InputElement result = createInputElement(doc, name);
		result.setAttribute("type", "radio");
		return result;
	}
	
	public NativeEvent createKeyCodeEvent(Document document,
			String type, boolean ctrlKey, boolean altKey, boolean shiftKey,
			boolean metaKey, int keyCode) {
		return null;
	}
	
	@Deprecated
	public NativeEvent createKeyEvent(Document doc, String type,
			boolean canBubble, boolean cancelable, boolean ctrlKey, boolean altKey,
			boolean shiftKey, boolean metaKey, int keyCode, int charCode) {
		return null;
	}
	
	public NativeEvent createKeyPressEvent(Document document,
			boolean ctrlKey, boolean altKey, boolean shiftKey, boolean metaKey,
			int charCode) {
		return null;
	}
	
	public NativeEvent createMouseEvent(Document doc, String type,
			boolean canBubble, boolean cancelable, int detail, int screenX,
			int screenY, int clientX, int clientY, boolean ctrlKey, boolean altKey,
			boolean shiftKey, boolean metaKey, int button, Element relatedTarget) {
		NativeEvent clickEvent = new NativeEvent();
		clickEvent.type = "click";
		return clickEvent;
	}
	
	public ScriptElement createScriptElement(Document doc, String source) {
		ScriptElement elem = (ScriptElement) createElement(doc, "script");
		elem.setText(source);
		return elem;
	}
	
	public SelectElement createSelectElement(Document doc, boolean multiple) {
		SelectElement select = (SelectElement) createElement(doc, "select");
		if (multiple) {
			select.setMultiple(true);
		}
		return select;
	}
	
	public native void cssClearOpacity(Style style) /*-{
																	style.opacity = '';
																	}-*/;
	
	public String cssFloatPropertyName() {
		return "cssFloat";
	}
	
	public native void cssSetOpacity(Style style, double value) /*-{
																					style.opacity = value;
																					}-*/;
	
	public void dispatchEvent(Element target, NativeEvent evt) {
	}
	
	public native boolean eventGetAltKey(NativeEvent evt) /*-{
																			return !!evt.altKey;
																			}-*/;
	
	public native int eventGetButton(NativeEvent evt) /*-{
																		return evt.button || 0;
																		}-*/;
	
	public int eventGetCharCode(NativeEvent evt) {
		return 0;
	}
	
	public native int eventGetClientX(NativeEvent evt) /*-{
																		return evt.clientX || 0;
																		}-*/;
	
	public native int eventGetClientY(NativeEvent evt) /*-{
																		return evt.clientY || 0;
																		}-*/;
	
	public native boolean eventGetCtrlKey(NativeEvent evt) /*-{
																				return !!evt.ctrlKey;
																				}-*/;
	
	public native EventTarget eventGetCurrentTarget(NativeEvent event) /*-{
																								return event.currentTarget;
																								}-*/;
	
	public final native int eventGetKeyCode(NativeEvent evt) /*-{
																				return evt.keyCode || 0;
																				}-*/;
	
	public native boolean eventGetMetaKey(NativeEvent evt) /*-{
																				return !!evt.metaKey;
																				}-*/;
	
	public int eventGetMouseWheelVelocityY(NativeEvent evt) {
		return 0;
	}
	
	public EventTarget eventGetRelatedTarget(NativeEvent nativeEvent) {
		return null;
	}
	
	public native double eventGetRotation(NativeEvent evt) /*-{
																				return evt.rotation;
																				}-*/;
	
	public native double eventGetScale(NativeEvent evt) /*-{
																			return evt.scale;
																			}-*/;
	
	public native int eventGetScreenX(NativeEvent evt) /*-{
																		return evt.screenX || 0;
																		}-*/;
	
	public native int eventGetScreenY(NativeEvent evt) /*-{
																		return evt.screenY || 0;
																		}-*/;
	
	public native boolean eventGetShiftKey(NativeEvent evt) /*-{
																				return !!evt.shiftKey;
																				}-*/;
	
	public EventTarget eventGetTarget(NativeEvent evt) {
		return null;
	}
	
	public final String eventGetType(NativeEvent evt) {
		return evt.type;
	};
	
	public void eventPreventDefault(NativeEvent evt) {
	}
	
	public native void eventSetKeyCode(NativeEvent evt, char key) /*-{
																						evt.keyCode = key;
																						}-*/;
	
	public native void eventStopPropagation(NativeEvent evt) /*-{
																				evt.stopPropagation();
																				}-*/;
	
	public String eventToString(NativeEvent evt) {
		return "";
	}
	
	public native int getAbsoluteLeft(Element elem) /*-{
																	var left = 0;
																	var curr = elem;
																	// This intentionally excludes body which has a null offsetParent.    
																	while (curr.offsetParent) {
																	left -= curr.scrollLeft;
																	curr = curr.parentNode;
																	}
																	while (elem) {
																	left += elem.offsetLeft;
																	elem = elem.offsetParent;
																	}
																	return left;
																	}-*/;
	
	public native int getAbsoluteTop(Element elem) /*-{
																	var top = 0;
																	var curr = elem;
																	// This intentionally excludes body which has a null offsetParent.    
																	while (curr.offsetParent) {
																	top -= curr.scrollTop;
																	curr = curr.parentNode;
																	}
																	while (elem) {
																	top += elem.offsetTop;
																	elem = elem.offsetParent;
																	}
																	return top;
																	}-*/;
	
	public String getAttribute(Element elem, String name) {
		return MoreObjects.firstNonNull(elem.getAttribute(name), "");
	}
	
	public int getBodyOffsetLeft(Document doc) {
		return 0;
	}
	
	public int getBodyOffsetTop(Document doc) {
		return 0;
	}
	
	public native JsArray<Touch> getChangedTouches(NativeEvent evt) /*-{
																							return evt.changedTouches;
																							}-*/;
	
	public native Element getFirstChildElement(Element elem) /*-{
																				var child = elem.firstChild;
																				while (child && child.nodeType != 1)
																				child = child.nextSibling;
																				return child;
																				}-*/;
	
	public native String getInnerHTML(Element elem) /*-{
																	return elem.innerHTML;
																	}-*/;
	
	public native String getInnerText(Element node) /*-{
																	// To mimic IE's 'innerText' property in the W3C DOM, we need to recursively
																	// concatenate all child text nodes (depth first).
																	var text = '', child = node.firstChild;
																	while (child) {
																	// 1 == Element node
																	if (child.nodeType == 1) {
																	text += this.@com.google.gwt.dom.client.DOMImpl::getInnerText(Lcom/google/gwt/dom/client/Element;)(child);
																	} else if (child.nodeValue) {
																	text += child.nodeValue;
																	}
																	child = child.nextSibling;
																	}
																	return text;
																	}-*/;
	
	public Element getNextSiblingElement(Element elem) {
		Element parent = elem.getParentElement();
		if (parent == null)
			return null;
		List<Element> siblingList = parent.getChildElements();
		int index = siblingList.indexOf(elem);
		if (index == siblingList.size() - 1)
			return null;
		return siblingList.get(index + 1);
	}
	/*-{
	 var sib = elem.nextSibling;
	 while (sib && sib.nodeType != 1)
	   sib = sib.nextSibling;
	 return sib;
	}-*/;
	
	public native int getNodeType(Node node) /*-{
															return node.nodeType;
															}-*/;
	
	/**
	 * Returns a numeric style property (such as zIndex) that may need to be
	 * coerced to a string.
	 */
	public String getNumericStyleProperty(Style style, String name) {
		return getStyleProperty(style, name);
	}
	
	public native Element getParentElement(Node node) /*-{
																		var parent = node.parentNode;
																		if (!parent || parent.nodeType != 1) {
																		parent = null;
																		}
																		return parent;
																		}-*/;
	
	public native Element getPreviousSiblingElement(Element elem) /*-{
																						var sib = elem.previousSibling;
																						while (sib && sib.nodeType != 1)
																						sib = sib.previousSibling;
																						return sib;
																						}-*/;
	
	public int getScrollLeft(Document doc) {
		return doc.getViewportElement().getScrollLeft();
	}
	
	public native int getScrollLeft(Element elem) /*-{
																	return elem.scrollLeft || 0;
																	}-*/;
	
	public int getScrollTop(Document doc) {
		return doc.getViewportElement().getScrollTop();
	}
	
	public String getStyleProperty(Style style, String name) {
		// TODO
		return "";
	}
	
	public int getTabIndex(Element elem) {
		return Integer.valueOf((String) MoreObjects.firstNonNull(elem.getAttribute("tabindex"), "0"));
	}
	
	public native String getTagName(Element elem) /*-{
																	return elem.tagName;
																	}-*/;
	
	public native JsArray<Touch> getTargetTouches(NativeEvent evt) /*-{
																						return evt.targetTouches;
																						}-*/;
	
	public native JsArray<Touch> getTouches(NativeEvent evt) /*-{
																				return evt.touches;
																				}-*/;
	
	public boolean hasAttribute(Element elem, String name) {
		return elem.attributes.containsKey(name);
	}
	/*-{
	 return elem.hasAttribute(name);
	}-*/;
	
	public native String imgGetSrc(Element img) /*-{
																return img.src;
																}-*/;
	
	public void imgSetSrc(Element img, String src) {
		img.setAttribute("src", src);
	}
	
	public boolean isOrHasChild(Node parent, Node child) {
		return false;
	}
	
	public native void scrollIntoView(Element elem) /*-{
																	var left = elem.offsetLeft, top = elem.offsetTop;
																	var width = elem.offsetWidth, height = elem.offsetHeight;
																	
																	if (elem.parentNode != elem.offsetParent) {
																	left -= elem.parentNode.offsetLeft;
																	top -= elem.parentNode.offsetTop;
																	}
																	
																	var cur = elem.parentNode;
																	while (cur && (cur.nodeType == 1)) {
																	if (left < cur.scrollLeft) {
																	cur.scrollLeft = left;
																	}
																	if (left + width > cur.scrollLeft + cur.clientWidth) {
																	cur.scrollLeft = (left + width) - cur.clientWidth;
																	}
																	if (top < cur.scrollTop) {
																	cur.scrollTop = top;
																	}
																	if (top + height > cur.scrollTop + cur.clientHeight) {
																	cur.scrollTop = (top + height) - cur.clientHeight;
																	}
																	
																	var offsetLeft = cur.offsetLeft, offsetTop = cur.offsetTop;
																	if (cur.parentNode != cur.offsetParent) {
																	offsetLeft -= cur.parentNode.offsetLeft;
																	offsetTop -= cur.parentNode.offsetTop;
																	}
																	
																	left += offsetLeft - cur.scrollLeft;
																	top += offsetTop - cur.scrollTop;
																	cur = cur.parentNode;
																	}
																	}-*/;
	
	public void selectAdd(SelectElement select, OptionElement option,
			OptionElement before) {
		select.insertBefore(option, before);
	}
	/*-{
	 select.add(option, before);
	}-*/;
	
	public void selectClear(SelectElement select) {
		for (Element element : select.getChildElements()) {
			element.removeFromParent();
		}
	}
	/*-{
																			select.options.length = 0;
																			}-*/;
	
	public int selectGetLength(SelectElement select) {
		return selectGetOptions(select).getLength();
	}
	/*-{
	 return select.options.length;
	}-*/;
	
	public NodeList<OptionElement> selectGetOptions(SelectElement select) {
		return (NodeList) select.getChildNodes();
	}
	/*-{
	 return select.options;
	}-*/;
	
	public native void selectRemoveOption(SelectElement select, int index) /*-{
																									select.remove(index);
																									}-*/;
	
	public native void setDraggable(Element elem, String draggable) /*-{
																							elem.draggable = draggable;
																							}-*/;
	
	public native void setInnerText(Element elem, String text) /*-{
																					// Remove all children first.
																					while (elem.firstChild) {
																					elem.removeChild(elem.firstChild);
																					}
																					// Add a new text node.
																					if (text != null) {
																					elem.appendChild(elem.ownerDocument.createTextNode(text));
																					}
																					}-*/;
	
	public void setScrollLeft(Document doc, int left) {
		doc.getViewportElement().setScrollLeft(left);
	}
	
	public void setScrollLeft(Element elem, int left) {
		// nothing
	}
	/*-{
	 elem.scrollLeft = left;
	}-*/;
	
	public void setScrollTop(Document doc, int top) {
		doc.getViewportElement().setScrollTop(top);
	}
	
	public native String toString(Element elem) /*-{
																return elem.outerHTML;
																}-*/;
	
	public native int touchGetClientX(Touch touch)/*-{
																	return touch.clientX;
																	}-*/;
	
	public native int touchGetClientY(Touch touch)/*-{
																	return touch.clientY;
																	}-*/;
	
	public native int touchGetIdentifier(Touch touch)/*-{
																		return touch.identifier;
																		}-*/;
	
	public native int touchGetPageX(Touch touch)/*-{
																return touch.pageX;
																}-*/;
	
	public native int touchGetPageY(Touch touch)/*-{
																return touch.pageY;
																}-*/;
	
	public native int touchGetScreenX(Touch touch)/*-{
																	return touch.screenX;
																	}-*/;
	
	public native int touchGetScreenY(Touch touch)/*-{
																	return touch.screenY;
																	}-*/;
	
	public native EventTarget touchGetTarget(Touch touch)/*-{
																			return touch.target;
																			}-*/;
}
