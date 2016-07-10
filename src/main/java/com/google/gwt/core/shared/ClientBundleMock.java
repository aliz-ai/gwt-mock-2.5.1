package com.google.gwt.core.shared;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;


public class ClientBundleMock {

    private static Map<Method, ImageResource> images = new HashMap<>();
    
    @SuppressWarnings("unchecked")
    public static <T extends ClientBundle> T createClientBundle(final Class<?> classLiteral) {
        return (T) Proxy.newProxyInstance(classLiteral.getClassLoader(), new Class<?>[] { classLiteral }, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (ImageResource.class.equals(method.getReturnType())) {
                    ImageResource image = images.get(method);
                    if (image == null) {
                        String name = method.getName();
                        int left = 0;
                        int top = 0;
                        int width = 21;
                        int height = 21;

                        String externalImage;
                        
                        // It is mock, some random image is selected.
                        externalImage = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABUAAAAVCAYAAACpF6WWAAAAFklEQVR42mNgGAWjYBSMglEwCkYQAAAG+QABz6nPSQAAAABJRU5ErkJggg==";
                        
                        image = new com.google.gwt.resources.client.impl.ImageResourcePrototype(name,
                                com.google.gwt.safehtml.shared.UriUtils.fromTrustedString(externalImage), //
                                left, top, width, height, false, false);

                        images.put(method, image);
                    }

                    return image;
                } else if (ClientBundle.class.isAssignableFrom(method.getReturnType()))  {
                    return createClientBundle(method.getReturnType());
                }
                return method.invoke(this, args);
            }
        });
    }

}
