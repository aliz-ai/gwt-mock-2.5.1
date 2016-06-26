/*
 * Created on May 27, 2016
 * @author vlads
 */
package com.doctusoft.gwtmock;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.safehtml.shared.SafeHtml;

/**
 * Used for binary code compatibility
 * 
 * Wraps all calls to element
 *
 */
public class ElementDelegate extends com.google.gwt.user.client.Element  {

    private final com.google.gwt.dom.client.Element element;
    
    /**
     * Convert dom.client.Element to old user.client.Element to be able to run code in JVM
     * We may want to replace this with dynamic proxy in future.
     */
    public static com.google.gwt.user.client.Element create(com.google.gwt.dom.client.Element element) {
        return new ElementDelegate(element);
    }
    
    private ElementDelegate( com.google.gwt.dom.client.Element element) {
        this.element = element;
    }
    
    // Generated Delegate code.
    
    public boolean addClassName(String className) {
        return element.addClassName(className);
    }

    public void blur() {
        element.blur();
    }

    public void dispatchEvent(NativeEvent evt) {
        element.dispatchEvent(evt);
    }

    public void focus() {
        element.focus();
    }

    public int getAbsoluteBottom() {
        return element.getAbsoluteBottom();
    }

    public int getAbsoluteLeft() {
        return element.getAbsoluteLeft();
    }

    public int getAbsoluteRight() {
        return element.getAbsoluteRight();
    }

    public int getAbsoluteTop() {
        return element.getAbsoluteTop();
    }

    public String getAttribute(String name) {
        return element.getAttribute(name);
    }

    public String getClassName() {
        return element.getClassName();
    }

    public int getClientHeight() {
        return element.getClientHeight();
    }

    public int getClientWidth() {
        return element.getClientWidth();
    }

    public String getDir() {
        return element.getDir();
    }

    public String getDraggable() {
        return element.getDraggable();
    }

    public String getId() {
        return element.getId();
    }

    public String getInnerHTML() {
        return element.getInnerHTML();
    }

    public String getInnerText() {
        return element.getInnerText();
    }

    public String getLang() {
        return element.getLang();
    }

    public Element getNextSiblingElement() {
        return element.getNextSiblingElement();
    }

    public int getOffsetHeight() {
        return element.getOffsetHeight();
    }

    public int getOffsetLeft() {
        return element.getOffsetLeft();
    }

    public Element getOffsetParent() {
        return element.getOffsetParent();
    }

    public int getOffsetTop() {
        return element.getOffsetTop();
    }

    public int getOffsetWidth() {
        return element.getOffsetWidth();
    }

    public Element getPreviousSiblingElement() {
        return element.getPreviousSiblingElement();
    }

    public boolean getPropertyBoolean(String name) {
        return element.getPropertyBoolean(name);
    }

    public double getPropertyDouble(String name) {
        return element.getPropertyDouble(name);
    }

    public int getPropertyInt(String name) {
        return element.getPropertyInt(name);
    }

    public JavaScriptObject getPropertyJSO(String name) {
        return element.getPropertyJSO(name);
    }

    public Object getPropertyObject(String name) {
        return element.getPropertyObject(name);
    }

    public String getPropertyString(String name) {
        return element.getPropertyString(name);
    }

    public int getScrollHeight() {
        return element.getScrollHeight();
    }

    public int getScrollLeft() {
        return element.getScrollLeft();
    }

    public int getScrollTop() {
        return element.getScrollTop();
    }

    public int getScrollWidth() {
        return element.getScrollWidth();
    }

    public String getString() {
        return element.getString();
    }

    public Style getStyle() {
        return element.getStyle();
    }

    public int getTabIndex() {
        return element.getTabIndex();
    }

    public String getTagName() {
        return element.getTagName();
    }

    public String getTitle() {
        return element.getTitle();
    }

    public boolean hasAttribute(String name) {
        return element.hasAttribute(name);
    }

    public boolean hasTagName(String tagName) {
        return element.hasTagName(tagName);
    }

    public void removeAttribute(String name) {
        element.removeAttribute(name);
    }

    public boolean removeClassName(String className) {
        return element.removeClassName(className);
    }

    public void replaceClassName(String oldClassName, String newClassName) {
        element.replaceClassName(oldClassName, newClassName);
    }

    public void scrollIntoView() {
        element.scrollIntoView();
    }

    public void setAttribute(String name, String value) {
        element.setAttribute(name, value);
    }

    public void setClassName(String className) {
        element.setClassName(className);
    }

    public void setDir(String dir) {
        element.setDir(dir);
    }

    public void setDraggable(String draggable) {
        element.setDraggable(draggable);
    }

    public void setId(String id) {
        element.setId(id);
    }

    public void setInnerHTML(String html) {
        element.setInnerHTML(html);
    }

    public void setInnerSafeHtml(SafeHtml html) {
        element.setInnerSafeHtml(html);
    }

    public void setInnerText(String text) {
        element.setInnerText(text);
    }

    public void setLang(String lang) {
        element.setLang(lang);
    }

    public void setPropertyBoolean(String name, boolean value) {
        element.setPropertyBoolean(name, value);
    }

    public void setPropertyDouble(String name, double value) {
        element.setPropertyDouble(name, value);
    }

    public void setPropertyInt(String name, int value) {
        element.setPropertyInt(name, value);
    }

    public void setPropertyJSO(String name, JavaScriptObject value) {
        element.setPropertyJSO(name, value);
    }

    public void setPropertyObject(String name, Object value) {
        element.setPropertyObject(name, value);
    }

    public void setPropertyString(String name, String value) {
        element.setPropertyString(name, value);
    }

    public void setScrollLeft(int scrollLeft) {
        element.setScrollLeft(scrollLeft);
    }

    public void setScrollTop(int scrollTop) {
        element.setScrollTop(scrollTop);
    }

    public void setTabIndex(int tabIndex) {
        element.setTabIndex(tabIndex);
    }

    public void setTitle(String title) {
        element.setTitle(title);
    }
    
}
