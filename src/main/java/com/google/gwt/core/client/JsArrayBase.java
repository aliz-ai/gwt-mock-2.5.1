package com.google.gwt.core.client;

import java.util.List;

import com.google.common.collect.Lists;

public class JsArrayBase<T> extends JavaScriptObject {
	
	protected List<T> list = Lists.newArrayList();

	public List<T> getList() {
		return list;
	}
}
