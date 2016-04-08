package com.doctusoft.gwtmock;

import java.io.PrintWriter;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.BRElement;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.FormElement;
import com.google.gwt.dom.client.FrameElement;
import com.google.gwt.dom.client.HRElement;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.dom.client.TableColElement;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.dom.client.TableSectionElement;
import com.google.gwt.dom.client.Text;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.dom.client.UListElement;

public class Document extends com.google.gwt.dom.client.Document {
	
	public static Document Instance = new Document();
	
	protected Set<Element> elements = Sets.newHashSet();
	
	public Element documentElement = new Element();
	
	protected BodyElement body;
	
	private final ImmutableList<String> DummyTagNames = ImmutableList.of("i", "b", "h3", "small", "h5", "svg", "circle");
	
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
		if (UListElement.TAG.equals(tag)) {
			element = new UListElement();
		}
		if (AnchorElement.TAG.equals(tag)) {
			element = new AnchorElement();
		}
		if (ImageElement.TAG.equals(tag)) {
			element = new ImageElement();
		}
		if (LIElement.TAG.equals(tag)) {
			element = new LIElement();
		}
		if (TableElement.TAG.equals(tag)) {
			element = new TableElement();
		}
		if (TableColElement.TAG_COLGROUP.equals(tag)) {
			element = new TableColElement(TableColElement.TAG_COLGROUP);
		}
		if (TableColElement.TAG_COL.equals(tag)) {
			element = new TableColElement(TableColElement.TAG_COL);
		}
		if (TableSectionElement.TAG_THEAD.equals(tag)) {
			element = new TableSectionElement(TableSectionElement.TAG_THEAD);
		}
		if (TableSectionElement.TAG_TBODY.equals(tag)) {
			element = new TableSectionElement(TableSectionElement.TAG_TBODY);
		}
		if (TableSectionElement.TAG_TFOOT.equals(tag)) {
			element = new TableSectionElement(TableSectionElement.TAG_TFOOT);
		}
		if (TableCellElement.TAG_TD.equals(tag)) {
			element = new TableCellElement(TableCellElement.TAG_TD);
		}
		if (TableCellElement.TAG_TH.equals(tag)) {
			element = new TableCellElement(TableCellElement.TAG_TH);
		}
		if (TableRowElement.TAG.equals(tag)) {
			element = new TableRowElement();
		}
		if (DummyTagNames.contains(tag)) {
			element = new SpanElement();
		}
		if (FormElement.TAG.equals(tag)) {
			element = new FormElement();
		}
		if (SelectElement.TAG.equals(tag)) {
			element = new SelectElement();
		}
		if (OptionElement.TAG.equals(tag)) {
			element = new OptionElement();
		}
		if (LabelElement.TAG.equals(tag)) {
			element = new LabelElement();
		}
		if (ParagraphElement.TAG.equals(tag)) {
			element = new ParagraphElement();
		}
		if (TextAreaElement.TAG.equals(tag)) {
			element = new TextAreaElement();
		}
		if (HRElement.TAG.equals(tag)) {
			element = new HRElement();
		}
		if (BRElement.TAG.equals(tag)) {
			element = new BRElement();
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
		for (Element element : elements) {
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
	
	public void printFormatted(Node node, String indent, PrintWriter pw) {
		if (node instanceof Element) {
			printFormatted((Element) node, indent, pw);
		}
		if (node instanceof Text) {
			pw.print(((Text)node).getData());
		}
	}
	public void printFormatted(Element element, String indent, PrintWriter pw) {
		String tagName = element.getTagName();
		pw.print(indent + "<" + tagName);
		for (Entry<String, String> attribute : element.attributes.entrySet()) {
			pw.print(" " + attribute.getKey() + "=\"" + attribute.getValue() + "\"");
		}
		pw.println(">");
		if (!Strings.isNullOrEmpty(element.getInnerText())) {
			pw.println(indent + element.getInnerText());
		}
		for (Node node : element.getChildNodes()) {
			printFormatted(node, indent + "  ", pw);
		}
		pw.println(indent + "</" + tagName + ">");
	}
}
