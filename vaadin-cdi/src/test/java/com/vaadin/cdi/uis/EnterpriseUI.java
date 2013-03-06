/*
 * Copyright 2012 Vaadin Ltd.
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

package com.vaadin.cdi.uis;

import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.uis.Boundary;
import com.vaadin.cdi.uis.InstrumentedView;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.concurrent.atomic.AtomicInteger;

@CDIUI
public class EnterpriseUI extends UI {

    private final static AtomicInteger COUNTER = new AtomicInteger(0);
    private int clickCount;

    @Inject
    Boundary boundary;

    @PostConstruct
    public void initialize() {
        COUNTER.incrementAndGet();
        this.clickCount = 0;

    }

    @Override
    protected void init(VaadinRequest request) {
        setSizeFull();

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        final Label label = new Label("+EnterpriseUI");
        label.setId("label");
        Button button = new Button("InvokeEJB", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                String response = boundary.echo(String.valueOf(++clickCount));
                label.setValue(response);
            }
        });
        button.setId("button");

        layout.addComponent(label);
        layout.addComponent(button);
        setContent(layout);
    }

    public static int getNumberOfInstances() {
        return COUNTER.get();
    }

    public static void resetCounter() {
        COUNTER.set(0);
    }

}