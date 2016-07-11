package com.doctusoft.gwtmock;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.core.shared.GWT.CustomGWTCreateSupplier;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Main API entry point for unit tests to configure and control the Mock library.
 */
public class GWTMock {

    /**
     * Register custom factory for mock objects created by GWT.create();
     * Will take priority over predefined factory in Mock library.
     *  
     * @param customSupplier
     */
    public static void addCustomGWTCreateSupplier(CustomGWTCreateSupplier customSupplier) {
        GWT.addCustomSupplier(customSupplier);
    }
    
    /**
     * Register implementation of object created with GWT.create();
     * 
     * @param classLiteral
     * @param classLiteralImplementation
     */
    public static void registerGWTCreateImplementation(Class<?> classLiteral, Class<?> classLiteralImplementation) {
        GWT.registerGWTCreateImplementation(classLiteral, classLiteralImplementation);
    }
    
    /**
     * Remove all elements created in DOM and initialization done in Mock.
     * Called to be able to start new test.
     * Usually called in your tests setUp() or tearDown() ;
     */
    public static void reset() {
        // Implementation notes: There are no internal Mock events fired. All calls are made explicitly from this function.
        RootPanel.detachWidgets();
        com.doctusoft.gwtmock.Document.reset();
        GWT.cleanCustomSuppliers();
    }
    
}
