package com.google.gwt.user.client.ui;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;

public class TestJSON {
	
	@Test
	public void testParseSimpleStringMap() {
		String jsonSrc = "{\"key1\": \"value1\", \"key2\": \"value2\"}";
		JSONObject object = (JSONObject) JSONParser.parseStrict(jsonSrc);
		Assert.assertEquals("value1", object.get("key1").toString());
		Assert.assertEquals("value2", object.get("key2").toString());
	}
	
	@Test
	public void testParseSimpleBooleanMap() {
		String jsonSrc = "{\"key1\": true, \"key2\": false}";
		JSONObject object = (JSONObject) JSONParser.parseStrict(jsonSrc);
		Assert.assertEquals("true", object.get("key1").toString());
		Assert.assertEquals("false", object.get("key2").toString());
	}
	
	@Test
	public void testParseSimpleIntegerMap() {
		String jsonSrc = "{\"key1\": 0, \"key2\": -1, \"key3\": 1}";
		JSONObject object = (JSONObject) JSONParser.parseStrict(jsonSrc);
		Assert.assertEquals("0", object.get("key1").toString());
		Assert.assertEquals("-1", object.get("key2").toString());
		Assert.assertEquals("1", object.get("key3").toString());
	}
}
