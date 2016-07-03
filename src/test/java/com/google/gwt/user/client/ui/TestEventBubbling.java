/*
 * Pyx4j framework
 * Copyright (C) 2008-2015 pyx4j.com.
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
 *
 * Created on Jul 3, 2016
 * @author vlads
 */
package com.google.gwt.user.client.ui;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import com.google.gwt.dom.client.DOMImpl;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;

public class TestEventBubbling {


    @Before
    public void setup() {
        RootPanel.get().clear();
        com.doctusoft.gwtmock.Document.reset();
    }
    
    @Test
    public void testTextBoxFocus() {
        Label field1 = new Label();
        field1.ensureDebugId("label1");

        FocusPanel content = new FocusPanel();
        content.add(field1);
        RootPanel.get().add(content);
        
        ClickHandler childClickHandler = Mockito.mock(ClickHandler.class);
        field1.addClickHandler(childClickHandler);
        
        ClickHandler parentClickHandler = Mockito.mock(ClickHandler.class);
        content.addClickHandler(parentClickHandler);
        
        DOMImpl.fireEventBubbling(field1.getElement(), ClickEvent.getType());
        
        InOrder inOrder = Mockito.inOrder(childClickHandler, parentClickHandler);
        inOrder.verify(childClickHandler).onClick(Mockito.isA(ClickEvent.class));
        inOrder.verify(parentClickHandler).onClick(Mockito.isA(ClickEvent.class));
    }
}
