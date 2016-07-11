package com.google.gwt;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.doctusoft.gwtmock.GWTMock;
import com.google.gwt.core.client.GWT;

public class TestGWTCreate {

    public static class FooSimple {
        
        public String bar() {
            return "Simple";
        }
    }
    
    public static class FooExtendeded extends FooSimple {
        
        public String bar() {
            return "Extended";
        }
    }
    
    @Before
    public void setup() {
        GWTMock.reset();
    }
    
    @Test
    public void testCreateRegistration() {
        FooSimple foo;
        // Default creation of object implementation.
        foo = GWT.create(FooSimple.class);
        Assert.assertEquals("Simple", foo.bar());
        
        GWTMock.registerGWTCreateImplementation(FooSimple.class, FooExtendeded.class);
        foo = GWT.create(FooSimple.class);
        Assert.assertEquals("Extended", foo.bar());
        
        // Reset to default implementation
        GWTMock.reset();
        foo = GWT.create(FooSimple.class);
        Assert.assertEquals("Simple", foo.bar());
    }
}
