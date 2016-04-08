/*
 * Copyright 2012 Google Inc.
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
package com.google.gwt.core.shared;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gwt.event.dom.client.TouchEvent.TouchSupportDetector;
import com.google.gwt.i18n.client.CurrencyList;
import com.google.gwt.i18n.client.constants.NumberConstantsImpl;
import com.google.gwt.i18n.client.impl.CldrImpl;
import com.google.gwt.i18n.client.impl.LocaleInfoImpl;
import com.google.gwt.i18n.client.impl.cldr.DateTimeFormatInfoImpl;
import com.google.gwt.i18n.client.impl.cldr.DateTimeFormatInfoImpl_en;
import com.google.gwt.layout.client.LayoutImpl;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.client.SafeHtmlTemplates.Template;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellBasedWidgetImpl;
import com.google.gwt.user.cellview.client.CellBasedWidgetImplStandardBase;
import com.google.gwt.user.client.impl.DOMImpl;
import com.google.gwt.user.client.impl.DOMImplMozilla;
import com.google.gwt.user.client.impl.HistoryImpl;
import com.google.gwt.user.client.ui.FileUpload.FileUploadImpl;
import com.google.gwt.user.client.ui.ResizeLayoutPanel;
import com.google.gwt.user.client.ui.ScrollImpl;
import com.google.gwt.user.client.ui.TreeItem.TreeItemImpl;
import com.google.gwt.user.client.ui.impl.PopupImpl;
import com.google.web.bindery.event.shared.SimpleEventBus;

/**
 * Supports core functionality that in some cases requires direct support from
 * the compiler and runtime systems such as runtime type information and
 * deferred binding.
 */
public final class GWT {
	
	public interface CustomGWTCreateSupplier {
		/**
		 * return the desired mock object if applies
		 */
		public Object create(Class<?> classLiteral);
	}
	
	private static List<CustomGWTCreateSupplier> customSuppliers = Lists.newArrayList();
	
	public static void addCustomSupplier(CustomGWTCreateSupplier customSupplier) {
		customSuppliers.add(customSupplier);
	}
	
	public static void cleanCustomSuppliers() {
		customSuppliers.clear();
	}
	
	/**
	 * Always <code>null</code> in Production Mode; in Development Mode provides
	 * the implementation for certain methods.
	 */
	// private static GWTBridge sGWTBridge = null;
	
	/**
	 * Instantiates a class via deferred binding.
	 * 
	 * <p>
	 * The argument to {@link #create(Class)}&#160;<i>must</i> be a class literal because the Production Mode compiler must be able to statically determine the requested type at compile-time. This can be tricky because using a {@link Class} variable may appear to work correctly in Development Mode.
	 * </p>
	 * 
	 * @param classLiteral
	 *           a class literal specifying the base class to be
	 *           instantiated
	 * @return the new instance, which must be cast to the requested class
	 */
	public static <T> T create(Class<?> classLiteral) {
		// if (sGWTBridge == null) {
		// /*
		// * In Production Mode, the compiler directly replaces calls to this method
		// * with a new Object() type expression of the correct rebound type.
		// */
		// throw new UnsupportedOperationException(
		// "ERROR: GWT.create() is only usable in client code!  It cannot be called, "
		// + "for example, from server code.  If you are running a unit test, "
		// + "check that your test case extends GWTTestCase and that GWT.create() "
		// + "is not called from within an initializer or constructor.");
		// } else {
		// return sGWTBridge.<T> create(classLiteral);
		// }
		Object result = null;
		for (CustomGWTCreateSupplier supplier : customSuppliers) {
			result = supplier.create(classLiteral);
			if (result != null) {
				return (T) result;
			}
		}
		if (LocaleInfoImpl.class.equals(classLiteral)) {
			return (T) new LocaleInfoImpl();
		}
		if (CldrImpl.class.equals(classLiteral)) {
			return (T) new CldrImpl();
		}
		if (DateTimeFormatInfoImpl.class.equals(classLiteral)) {
			return (T) new DateTimeFormatInfoImpl_en();
		}
		if (CellBasedWidgetImpl.class.equals(classLiteral)) {
			return (T) new CellBasedWidgetImplStandardBase();
		}
		if (SimpleEventBus.class.equals(classLiteral)) {
			return (T) new SimpleEventBus();
		}
		if (com.google.gwt.user.cellview.client.AbstractCellTable.Impl.class.equals(classLiteral)) {
			return (T) new com.google.gwt.user.cellview.client.AbstractCellTable.Impl();
		}
		if (CurrencyList.class.equals(classLiteral)) {
			return (T) new CurrencyList();
		}
		if (NumberConstantsImpl.class.equals(classLiteral)) {
			return (T) new NumberConstants();
		}
		if (PopupImpl.class.equals(classLiteral)) {
			return (T) new PopupImpl();
		}
		if (ResizeLayoutPanel.Impl.class.equals(classLiteral)) {
			return (T) new ResizeLayoutPanel.ImplStandard();
		}
		if (TouchSupportDetector.class.equals(classLiteral)) {
			return (T) new TouchSupportDetector();
		}
		if (ScrollImpl.class.equals(classLiteral)) {
			return (T) new ScrollImpl();
		}
		if (LayoutImpl.class.equals(classLiteral)) {
			return (T) new LayoutImpl();
		}
		if (TreeItemImpl.class.equals(classLiteral)) {
			return (T) new TreeItemImpl();
		}
		if (SafeHtmlTemplates.class.isAssignableFrom(classLiteral)) {
			return (T) createSafeHtmlTemplates(classLiteral);
		}
		if (HistoryImpl.class.equals(classLiteral)) {
			return (T) new HistoryImpl();
		}
		if (FileUploadImpl.class.equals(classLiteral)) {
			return (T) new FileUploadImpl();
		}
		if (DOMImpl.class.equals(classLiteral)) {
			return (T) new DOMImplMozilla();
		}
		if ((classLiteral.getModifiers() | Modifier.ABSTRACT) == 0) {
			try {
				return (T) classLiteral.getConstructor().newInstance();
			} catch (Exception e) {
				throw new RuntimeException("Exception while instantiating: " + classLiteral, e);
			}
		}
		return null;
	}
	
	private static SafeHtmlTemplates createSafeHtmlTemplates(final Class<?> classLiteral) {
		return (SafeHtmlTemplates) Proxy.newProxyInstance(classLiteral.getClassLoader(), new Class<?>[] { classLiteral }, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if (method.getDeclaringClass().equals(classLiteral)) {
					String templateString = method.getAnnotation(Template.class).value();
					for (Object arg : args) {
						String content = null;
						if (arg instanceof String) {
							content = SafeHtmlUtils.htmlEscape((String) arg);
						} else if (arg instanceof SafeHtml) {
							content = ((SafeHtml) arg).asString();
						}
						templateString = templateString.replaceAll("\\{0\\}", content);
					}
					return SafeHtmlUtils.fromTrustedString(templateString);
				}
				return method.invoke(this, args);
			}
		});
	}
	
	static class NumberConstants implements NumberConstantsImpl {
		
		@Override
		public String notANumber() {
			return "NaN";
		}
		
		@Override
		public String currencyPattern() {
			return "";
		}
		
		@Override
		public String decimalPattern() {
			return "";
		}
		
		@Override
		public String decimalSeparator() {
			return ".";
		}
		
		@Override
		public String defCurrencyCode() {
			return "EUR";
		}
		
		@Override
		public String exponentialSymbol() {
			return "E";
		}
		
		@Override
		public String globalCurrencyPattern() {
			return "";
		}
		
		@Override
		public String groupingSeparator() {
			return "_";
		}
		
		@Override
		public String infinity() {
			return "Inf";
		}
		
		@Override
		public String minusSign() {
			return "-";
		}
		
		@Override
		public String monetaryGroupingSeparator() {
			return "_";
		}
		
		@Override
		public String monetarySeparator() {
			return ",";
		}
		
		@Override
		public String percent() {
			return "%";
		}
		
		@Override
		public String percentPattern() {
			return "";
		}
		
		@Override
		public String perMill() {
			return "/";
		}
		
		@Override
		public String plusSign() {
			return "+";
		}
		
		@Override
		public String scientificPattern() {
			return "";
		}
		
		@Override
		public String simpleCurrencyPattern() {
			return "";
		}
		
		@Override
		public String zeroDigit() {
			return "0";
		}
		
	}
	
	/**
	 * Returns the empty string when running in Production Mode, but returns a
	 * unique string for each thread in Development Mode (for example, different
	 * windows accessing the dev mode server will each have a unique id, and
	 * hitting refresh without restarting dev mode will result in a new unique id
	 * for a particular window.
	 *
	 * TODO(unnurg): Remove this function once Dev Mode rewriting classes are in
	 * gwt-dev.
	 */
	public static String getUniqueThreadId() {
		// if (sGWTBridge != null) {
		// return sGWTBridge.getThreadUniqueID();
		// }
		return "";
	}
	
	/**
	 * Get a human-readable representation of the GWT version used, or null if
	 * this is running on the client.
	 * 
	 * @return a human-readable version number, such as {@code "2.5"}
	 */
	public static String getVersion() {
		// return sGWTBridge == null ? null : sGWTBridge.getVersion();
		return "xx";
	}
	
	/**
	 * Returns <code>true</code> when running inside the normal GWT environment,
	 * either in Development Mode or Production Mode. Returns <code>false</code> if this code is running in a plain JVM. This might happen when running
	 * shared code on the server, or during the bootstrap sequence of a
	 * GWTTestCase test.
	 */
	public static boolean isClient() {
		// Replaced with "true" by GWT compiler.
		// return sGWTBridge != null && sGWTBridge.isClient();
		return false;
	}
	
	/**
	 * Returns <code>true</code> when running in production mode. Returns <code>false</code> when running either in development mode, or when running
	 * in a plain JVM.
	 */
	public static boolean isProdMode() {
		// Replaced with "true" by GWT compiler.
		return false;
	}
	
	/**
	 * Determines whether or not the running program is script or bytecode.
	 */
	public static boolean isScript() {
		// Replaced with "true" by GWT compiler.
		return false;
	}
	
	/**
	 * Logs a message to the development shell logger in Development Mode. Calls
	 * are optimized out in Production Mode.
	 */
	public static void log(String message) {
		log(message, null);
	}
	
	/**
	 * Logs a message to the development shell logger in Development Mode. Calls
	 * are optimized out in Production Mode.
	 */
	public static void log(String message, Throwable e) {
		// if (sGWTBridge != null) {
		// sGWTBridge.log(message, e);
		// }
		System.out.println(message);
		e.printStackTrace();
	}
	
	/**
	 * Called via reflection in Development Mode; do not ever call this method in
	 * Production Mode. May be called in server code to initialize server bridge.
	 */
	// public static void setBridge(GWTBridge bridge) {
	// sGWTBridge = bridge;
	// }
}
