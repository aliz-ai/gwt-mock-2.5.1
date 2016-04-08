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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import net.htmlparser.jericho.Attribute;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;

import com.doctusoft.gwtmock.Document;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.DomEvent.Type;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;

/**
 * All HTML element interfaces derive from this class.
 */
public class Element extends Node {
	
	public Map<String, String> attributes = Maps.newHashMap();
	
	protected Document document;
	
	public EventListener __listener = null;
	public int __eventBits = 0;
	
	public void fireEvent(Type<?> eventType) {
		if ((__eventBits & Event.getTypeInt(eventType.getName())) != 0) {
			__listener.onBrowserEvent(new Event(eventType.getName()));
		}
	}
	
	public String innerText = "";
	
	protected String tagName = null;
	
	protected Style style = new Style();
	
	/**
	 * Constant returned from {@link #getDraggable()}.
	 */
	public static final String DRAGGABLE_AUTO = "auto";
	
	/**
	 * Constant returned from {@link #getDraggable()}.
	 */
	public static final String DRAGGABLE_FALSE = "false";
	
	/**
	 * Constant returned from {@link #getDraggable()}.
	 */
	public static final String DRAGGABLE_TRUE = "true";
	
	private String innerHtml;
	
	/**
	 * Assert that the given {@link Node} is an {@link Element} and automatically
	 * typecast it.
	 */
	public static Element as(JavaScriptObject o) {
		assert is(o);
		return (Element) o;
	}
	
	/**
	 * Assert that the given {@link Node} is an {@link Element} and automatically
	 * typecast it.
	 */
	public static Element as(Node node) {
		assert is(node);
		return (Element) node;
	}
	
	/**
	 * Determines whether the given {@link JavaScriptObject} can be cast to an {@link Element}. A <code>null</code> object will cause this method to
	 * return <code>false</code>.
	 */
	public static boolean is(JavaScriptObject o) {
		if (o instanceof Node) {
			return is((Node) o);
		}
		return false;
	}
	
	/**
	 * Determine whether the given {@link Node} can be cast to an {@link Element}.
	 * A <code>null</code> node will cause this method to return <code>false</code>.
	 */
	public static boolean is(Node node) {
		return (node != null) && (node.getNodeType() == Node.ELEMENT_NODE);
	}
	
	public Element() {
	}
	
	/**
	 * Adds a name to this element's class property. If the name is already
	 * present, this method has no effect.
	 * 
	 * @param className
	 *           the class name to be added
	 * @return <code>true</code> if this element did not already have the specified class name
	 * @see #setClassName(String)
	 */
	public final boolean addClassName(String className) {
		assert (className != null) : "Unexpectedly null class name";
		
		className = className.trim();
		assert (className.length() != 0) : "Unexpectedly empty class name";
		
		// Get the current style string.
		String oldClassName = getClassName();
		int idx = indexOfName(oldClassName, className);
		
		// Only add the style if it's not already present.
		if (idx == -1) {
			if (oldClassName.length() > 0) {
				setClassName(oldClassName + " " + className);
			} else {
				setClassName(className);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Removes keyboard focus from this element.
	 */
	public final void blur() {
		// TODO blur?
	}
	
	/**
	 * Dispatched the given event with this element as its target. The event will
	 * go through all phases of the browser's normal event dispatch mechanism.
	 * 
	 * Note: Because the browser's normal dispatch mechanism is used, exceptions
	 * thrown from within handlers triggered by this method cannot be caught by
	 * wrapping this method in a try/catch block. Such exceptions will be caught
	 * by the {@link com.google.gwt.core.client.GWT#setUncaughtExceptionHandler(com.google.gwt.core.client.GWT.UncaughtExceptionHandler) uncaught exception handler} as usual.
	 * 
	 * @param evt
	 *           the event to be dispatched
	 */
	public final void dispatchEvent(NativeEvent evt) {
		DOMImpl.impl.dispatchEvent(this, evt);
	}
	
	/**
	 * Gives keyboard focus to this element.
	 */
	public final void focus() {
		// TODO focus?
	}
	
	/**
	 * Gets an element's absolute bottom coordinate in the document's coordinate
	 * system.
	 */
	public final int getAbsoluteBottom() {
		return getAbsoluteTop() + getOffsetHeight();
	}
	
	/**
	 * Gets an element's absolute left coordinate in the document's coordinate
	 * system.
	 */
	public final int getAbsoluteLeft() {
		// TODO no emulated layouting yet
		return 0;
	}
	
	/**
	 * Gets an element's absolute right coordinate in the document's coordinate
	 * system.
	 */
	public final int getAbsoluteRight() {
		return getAbsoluteLeft() + getOffsetWidth();
	}
	
	/**
	 * Gets an element's absolute top coordinate in the document's coordinate
	 * system.
	 */
	public final int getAbsoluteTop() {
		// TODO no emulated layouting yet
		return 0;
	}
	
	/**
	 * Retrieves an attribute value by name. Attribute support can be
	 * inconsistent across various browsers. Consider using the accessors in {@link Element} and its specific subclasses to retrieve attributes and
	 * properties.
	 * 
	 * @param name
	 *           The name of the attribute to retrieve
	 * @return The Attr value as a string, or the empty string if that attribute
	 *         does not have a specified or default value
	 */
	public final String getAttribute(String name) {
		return attributes.get(name);
	}
	
	/**
	 * The class attribute of the element. This attribute has been renamed due to
	 * conflicts with the "class" keyword exposed by many languages.
	 * 
	 * @see <a href="http://www.w3.org/TR/1999/REC-html401-19991224/struct/global.html#adef-class">W3C HTML Specification</a>
	 */
	public final String getClassName() {
		return MoreObjects.firstNonNull(attributes.get("class"), "");
	}
	
	/**
	 * Returns the inner height of an element in pixels, including padding but not
	 * the horizontal scrollbar height, border, or margin.
	 * 
	 * @return the element's client height
	 */
	public final int getClientHeight() {
		// TODO no layouting yet
		return 0;
	}
	
	/**
	 * Returns the inner width of an element in pixels, including padding but not
	 * the vertical scrollbar width, border, or margin.
	 * 
	 * @return the element's client width
	 */
	public final int getClientWidth() {
		// TODO no layouting yet
		return 0;
	}
	
	/**
	 * Specifies the base direction of directionally neutral text and the
	 * directionality of tables.
	 */
	public final String getDir() {
		return attributes.get("dir");
	}
	
	/**
	 * Returns the draggable attribute of this element.
	 * 
	 * @return one of {@link #DRAGGABLE_AUTO}, {@link #DRAGGABLE_FALSE}, or {@link #DRAGGABLE_TRUE}
	 */
	public final String getDraggable() {
		return attributes.get("draggable");
	}
	
	/**
	 * Returns a NodeList of all descendant Elements with a given tag name, in the
	 * order in which they are encountered in a preorder traversal of this Element
	 * tree.
	 * 
	 * @param name
	 *           The name of the tag to match on. The special value "*" matches
	 *           all tags
	 * @return A list of matching Element nodes
	 */
	public final NodeList<Element> getElementsByTagName(String name) {
		List<Element> result = Lists.newArrayList();
		for (Node node : getChildNodes()) {
			if (node instanceof Element) {
				Element child = (Element) node;
				if (name.equals(child.getTagName())) {
					result.add(child);
				}
				if (!child.getChildNodes().getList().isEmpty()) {
					result.addAll(child.getElementsByTagName(name).getList());
				}
			}
		}
		return new NodeList<Element>(result);
	}
	
	/**
	 * The first child of element this element. If there is no such element, this
	 * returns null.
	 */
	public final Element getFirstChildElement() {
		for (Node node : getChildNodes()) {
			if (node instanceof Element) {
				return (Element) node;
			}
		}
		return null;
	}
	
	/**
	 * The element's identifier.
	 * 
	 * @see <a href="http://www.w3.org/TR/1999/REC-html401-19991224/struct/global.html#adef-id">W3C HTML Specification</a>
	 */
	public final String getId() {
		return getAttribute("id");
	}
	
	/**
	 * All of the markup and content within a given element.
	 */
	public final String getInnerHTML() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		for (Node node : getChildNodes().getList()) {
			Document.Instance.printFormatted(node, "", pw);
		}
		return sw.getBuffer().toString();
	}
	
	/**
	 * The text between the start and end tags of the object.
	 */
	public final String getInnerText() {
		return innerText;
	}
	
	/**
	 * Language code defined in RFC 1766.
	 */
	public final String getLang() {
		return attributes.get("lang");
	}
	
	/**
	 * The element immediately following this element. If there is no such
	 * element, this returns null.
	 */
	public final Element getNextSiblingElement() {
		return DOMImpl.impl.getNextSiblingElement(this);
	}
	
	/**
	 * The height of an element relative to the layout.
	 */
	public final int getOffsetHeight() {
		// TODO no layouting yet
		return 0;
	}
	
	/**
	 * The number of pixels that the upper left corner of the current element is
	 * offset to the left within the offsetParent node.
	 */
	public final int getOffsetLeft() {
		// TODO no layouting yet
		return 0;
	}
	
	/**
	 * Returns a reference to the object which is the closest (nearest in the
	 * containment hierarchy) positioned containing element.
	 */
	public final Element getOffsetParent() {
		return getParentElement();
	}
	
	/**
	 * The number of pixels that the upper top corner of the current element is
	 * offset to the top within the offsetParent node.
	 */
	public final int getOffsetTop() {
		// TODO no layouting yet
		return 0;
	}
	
	/**
	 * The width of an element relative to the layout.
	 */
	public final int getOffsetWidth() {
		// TODO no layouting yet
		return 0;
	}
	
	/**
	 * The element immediately preceeding this element. If there is no such
	 * element, this returns null.
	 */
	public final Element getPreviousSiblingElement() {
		return DOMImpl.impl.getPreviousSiblingElement(this);
	}
	
	/**
	 * Gets a boolean property from this element.
	 * 
	 * @param name
	 *           the name of the property to be retrieved
	 * @return the property value
	 */
	public final boolean getPropertyBoolean(String name) {
		return "true".equalsIgnoreCase(getPropertyString(name));
	}
	
	/**
	 * Gets a double property from this element.
	 * 
	 * @param name
	 *           the name of the property to be retrieved
	 * @return the property value
	 */
	public final double getPropertyDouble(String name) {
		String value = getPropertyString(name);
		if (value == null) {
			return 0.0;
		}
		return Double.valueOf(value);
	}
	
	/**
	 * Gets an integer property from this element.
	 * 
	 * @param name
	 *           the name of the property to be retrieved
	 * @return the property value
	 */
	public final int getPropertyInt(String name) {
		String value = getPropertyString(name);
		if (value == null) {
			return 0;
		}
		return Integer.valueOf(value);
	}
	
	/**
	 * Gets a JSO property from this element.
	 *
	 * @param name
	 *           the name of the property to be retrieved
	 * @return the property value
	 */
	public final native JavaScriptObject getPropertyJSO(String name) /*-{
																							return this[name] || null;
																							}-*/;
	
	/**
	 * Gets an object property from this element.
	 *
	 * @param name
	 *           the name of the property to be retrieved
	 * @return the property value
	 */
	public final native Object getPropertyObject(String name) /*-{
																					return this[name] || null;
																					}-*/;
	
	/**
	 * Gets a property from this element.
	 * 
	 * @param name
	 *           the name of the property to be retrieved
	 * @return the property value
	 */
	public final String getPropertyString(String name) {
		String attributeValue = attributes.get(name);
		// TODO implement getproperty for special properties
		if ("value".equals(name)) {
			attributeValue = MoreObjects.firstNonNull(attributeValue, "");
		}
		return attributeValue;
	}
	
	/**
	 * The height of the scroll view of an element.
	 */
	public final int getScrollHeight() {
		// TODO no layouting yet
		return 0;
	}
	
	/**
	 * The number of pixels that an element's content is scrolled from the left.
	 * 
	 * <p>
	 * If the element is in RTL mode, this method will return a negative value of the number of pixels scrolled from the right.
	 * </p>
	 */
	public final int getScrollLeft() {
		// TODO no layouting yet
		return 0;
	}
	
	/**
	 * The number of pixels that an element's content is scrolled from the top.
	 */
	public final int getScrollTop() {
		// TODO no layouting yet
		return 0;
	}
	
	/**
	 * The width of the scroll view of an element.
	 */
	public final int getScrollWidth() {
		// TODO no layouting yet
		return 0;
	}
	
	/**
	 * Gets a string representation of this element (as outer HTML).
	 * 
	 * We do not override {@link #toString()} because it is final in {@link com.google.gwt.core.client.JavaScriptObject}.
	 * 
	 * @return the string representation of this element
	 */
	public final String getString() {
		String tagName = getTagName();
		return "<" + tagName + ">" + getInnerText() + "</" + tagName + ">";
	}
	
	/**
	 * Gets this element's {@link Style} object.
	 */
	public final Style getStyle() {
		return style;
	}
	
	/**
	 * The index that represents the element's position in the tabbing order.
	 * 
	 * @see <a href="http://www.w3.org/TR/1999/REC-html401-19991224/interact/forms.html#adef-tabindex">W3C HTML Specification</a>
	 */
	public final int getTabIndex() {
		return 0;
	}
	
	/**
	 * Gets the element's full tag name, including the namespace-prefix if
	 * present.
	 * 
	 * @return the element's tag name
	 */
	public final String getTagName() {
		if (tagName != null) {
			return tagName;
		}
		TagName tagName = getClass().getAnnotation(TagName.class);
		if (tagName == null) {
			throw new RuntimeException("no tagname for " + this.getClass());
		}
		return tagName.value()[0];
	}
	
	/**
	 * The element's advisory title.
	 */
	public final native String getTitle() /*-{
														return this.title;
														}-*/;
	
	/**
	 * Determines whether an element has an attribute with a given name.
	 *
	 * <p>
	 * Note that IE, prior to version 8, will return false-positives for names that collide with element properties (e.g., style, width, and so forth).
	 * </p>
	 * 
	 * @param name
	 *           the name of the attribute
	 * @return <code>true</code> if this element has the specified attribute
	 */
	public final boolean hasAttribute(String name) {
		return DOMImpl.impl.hasAttribute(this, name);
	}
	
	/**
	 * Determines whether this element has the given tag name.
	 * 
	 * @param tagName
	 *           the tag name, including namespace-prefix (if present)
	 * @return <code>true</code> if the element has the given tag name
	 */
	public final boolean hasTagName(String tagName) {
		assert tagName != null : "tagName must not be null";
		return tagName.equals(getTagName());
	}
	
	/**
	 * Removes an attribute by name.
	 */
	public final void removeAttribute(String name) {
		attributes.remove(name);
	}
	/*-{
	  this.removeAttribute(name);
	}-*/;
	
	/**
	 * Removes a name from this element's class property. If the name is not
	 * present, this method has no effect.
	 * 
	 * @param className
	 *           the class name to be removed
	 * @return <code>true</code> if this element had the specified class name
	 * @see #setClassName(String)
	 */
	public final boolean removeClassName(String className) {
		assert (className != null) : "Unexpectedly null class name";
		
		className = className.trim();
		assert (className.length() != 0) : "Unexpectedly empty class name";
		
		// Get the current style string.
		String oldStyle = getClassName();
		int idx = indexOfName(oldStyle, className);
		
		// Don't try to remove the style if it's not there.
		if (idx != -1) {
			// Get the leading and trailing parts, without the removed name.
			String begin = oldStyle.substring(0, idx).trim();
			String end = oldStyle.substring(idx + className.length()).trim();
			
			// Some contortions to make sure we don't leave extra spaces.
			String newClassName;
			if (begin.length() == 0) {
				newClassName = end;
			} else if (end.length() == 0) {
				newClassName = begin;
			} else {
				newClassName = begin + " " + end;
			}
			
			setClassName(newClassName);
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the index of the first occurrence of name in a space-separated list of names,
	 * or -1 if not found.
	 *
	 * @param nameList
	 *           list of space delimited names
	 * @param name
	 *           a non-empty string. Should be already trimmed.
	 */
	static int indexOfName(String nameList, String name) {
		int idx = nameList.indexOf(name);
		
		// Calculate matching index.
		while (idx != -1) {
			if (idx == 0 || nameList.charAt(idx - 1) == ' ') {
				int last = idx + name.length();
				int lastPos = nameList.length();
				if ((last == lastPos)
						|| ((last < lastPos) && (nameList.charAt(last) == ' '))) {
					break;
				}
			}
			idx = nameList.indexOf(name, idx + 1);
		}
		
		return idx;
	}
	
	/**
	 * Replace one class name with another.
	 *
	 * @param oldClassName
	 *           the class name to be replaced
	 * @param newClassName
	 *           the class name to replace it
	 */
	public final void replaceClassName(String oldClassName, String newClassName) {
		removeClassName(oldClassName);
		addClassName(newClassName);
	}
	
	/**
	 * Scrolls this element into view.
	 * 
	 * <p>
	 * This method crawls up the DOM hierarchy, adjusting the scrollLeft and scrollTop properties of each scrollable element to ensure that the specified element is completely in view. It adjusts each scroll position by the minimum amount necessary.
	 * </p>
	 */
	public final void scrollIntoView() {
		DOMImpl.impl.scrollIntoView(this);
	}
	
	/**
	 * Adds a new attribute. If an attribute with that name is already present in
	 * the element, its value is changed to be that of the value parameter.
	 * 
	 * @param name
	 *           The name of the attribute to create or alter
	 * @param value
	 *           Value to set in string form
	 */
	public final void setAttribute(String name, String value) {
		attributes.put(name, value);
	}
	
	/**
	 * The class attribute of the element. This attribute has been renamed due to
	 * conflicts with the "class" keyword exposed by many languages.
	 * 
	 * @see <a href="http://www.w3.org/TR/1999/REC-html401-19991224/struct/global.html#adef-class">W3C HTML Specification</a>
	 */
	public final void setClassName(String className) {
		attributes.put("class", className);
	}
	
	/**
	 * Specifies the base direction of directionally neutral text and the
	 * directionality of tables.
	 */
	public final native void setDir(String dir) /*-{
																this.dir = dir;
																}-*/;
	
	/**
	 * Changes the draggable attribute to one of {@link #DRAGGABLE_AUTO}, {@link #DRAGGABLE_FALSE}, or {@link #DRAGGABLE_TRUE}.
	 * 
	 * @param draggable
	 *           a String constants
	 */
	public final void setDraggable(String draggable) {
		DOMImpl.impl.setDraggable(this, draggable);
	}
	
	/**
	 * The element's identifier.
	 * 
	 * @see <a href="http://www.w3.org/TR/1999/REC-html401-19991224/struct/global.html#adef-id">W3C HTML Specification</a>
	 */
	public final void setId(String id) {
		setAttribute("id", id);
	}
	
	protected List<Element> getChildNodesWithTagName(String tagName) {
		List<Element> result = Lists.newArrayList();
		for (Node node : getChildNodes().getList()) {
			if (node instanceof Element && ((Element) node).getTagName().equals(tagName)) {
				result.add((Element) node);
			}
		}
		return result;
	}
	
	public List<Element> getChildElements() {
		List<Element> result = Lists.newArrayList();
		for (Node node : getChildNodes().getList()) {
			if (node instanceof Element) {
				result.add((Element) node);
			}
		}
		return result;
	}
	
	private static void addParsedElements(Element target, Segment container, List<net.htmlparser.jericho.Element> elements) {
		int index = container.getBegin();
		final int begin = index;
		for (net.htmlparser.jericho.Element element : elements) {
			int elementStart = element.getBegin();
			if (elementStart > index) {
				// insert text node
				Text text = new Text();
				text.setData(container.subSequence(index - begin, elementStart - begin).toString());
				target.appendChild(text);
			}
			Element mockElement = Document.Instance.createMockElement(element.getName());
			target.appendChild(mockElement);
			for (Attribute attribute : element.getAttributes()) {
				mockElement.setAttribute(attribute.getName(), attribute.getValue());
			}
			addParsedElements(mockElement, element, element.getChildElements());
			index = element.getEnd();
		}
		if (container.getEnd() > index) {
			Text text = new Text();
			text.setData(container.subSequence(index - begin, container.getEnd() - begin).toString());
			target.appendChild(text);
		}
	}
	
	private void removeAllChildren() {
		for (Node node : getChildNodes().getList()) {
			node.setParentNode(null);
		}
		getChildNodes().getList().clear();
	}
	
	/**
	 * All of the markup and content within a given element.
	 */
	public final void setInnerHTML(String html) {
		this.innerHtml = html;
		removeAllChildren();
		Source source = new Source(html);
		addParsedElements(this, source, source.getChildElements());
	}
	
	/**
	 * All of the markup and content within a given element.
	 */
	public final void setInnerSafeHtml(SafeHtml html) {
		setInnerHTML(html.asString());
	}
	
	/**
	 * The text between the start and end tags of the object.
	 */
	public final void setInnerText(String text) {
		innerText = text;
	}
	
	/**
	 * Language code defined in RFC 1766.
	 */
	public final native void setLang(String lang) /*-{
																	this.lang = lang;
																	}-*/;
	
	/**
	 * Sets a boolean property on this element.
	 * 
	 * @param name
	 *           the name of the property to be set
	 * @param value
	 *           the new property value
	 */
	public final void setPropertyBoolean(String name, boolean value) {
		// TODO handle special stuff
		setAttribute(name, Boolean.toString(value));
	}
	/*-{
	  this[name] = value;
	}-*/;
	
	/**
	 * Sets a double property on this element.
	 * 
	 * @param name
	 *           the name of the property to be set
	 * @param value
	 *           the new property value
	 */
	public final native void setPropertyDouble(String name, double value) /*-{
																									this[name] = value;
																									}-*/;
	
	/**
	 * Sets an integer property on this element.
	 * 
	 * @param name
	 *           the name of the property to be set
	 * @param value
	 *           the new property value
	 */
	public final void setPropertyInt(String name, int value) {
		attributes.put(name, Integer.toString(value));
	};
	
	/**
	 * Sets a JSO property on this element.
	 *
	 * @param name
	 *           the name of the property to be set
	 * @param value
	 *           the new property value
	 */
	public final native void setPropertyJSO(String name, JavaScriptObject value) /*-{
																											this[name] = value;
																											}-*/;
	
	/**
	 * Sets an object property on this element.
	 *
	 * @param name
	 *           the name of the property to be set
	 * @param value
	 *           the new property value
	 */
	public final native void setPropertyObject(String name, Object value) /*-{
																									this[name] = value;
																									}-*/;
	
	/**
	 * Sets a property on this element.
	 * 
	 * @param name
	 *           the name of the property to be set
	 * @param value
	 *           the new property value
	 */
	public final void setPropertyString(String name, String value) {
		attributes.put(name, value);
	}
	
	/**
	 * The number of pixels that an element's content is scrolled to the left.
	 */
	public final void setScrollLeft(int scrollLeft) {
		DOMImpl.impl.setScrollLeft(this, scrollLeft);
	}
	
	/**
	 * The number of pixels that an element's content is scrolled to the top.
	 */
	public final void setScrollTop(int scrollTop) {
		// nothing
	}
	/*-{
	  this.scrollTop = scrollTop;
	}-*/;
	
	/**
	 * The index that represents the element's position in the tabbing order.
	 * 
	 * @see <a href="http://www.w3.org/TR/1999/REC-html401-19991224/interact/forms.html#adef-tabindex">W3C HTML Specification</a>
	 */
	public final void setTabIndex(int tabIndex) {
		setAttribute("tabindex", Integer.toString(tabIndex));
	}
	/*-{
	 this.tabIndex = tabIndex;
	}-*/;
	
	/**
	 * The element's advisory title.
	 */
	public final native void setTitle(String title) /*-{
																	// Setting the title to null results in the string "null" being displayed
																	// on some browsers.
																	this.title = title || '';
																	}-*/;
	
	public Document getDocument() {
		return document;
	}
	
	public void setDocument(Document document) {
		this.document = document;
	}
}
