package com.szwadronwilkowalfa.statystyki.views;

import com.szwadronwilkowalfa.statystyki.services.CancerService;
import com.szwadronwilkowalfa.statystyki.services.PowiatService;
import com.szwadronwilkowalfa.statystyki.views.components.CancerDataSpan;
import com.szwadronwilkowalfa.statystyki.views.components.PowiatSettingsSpan;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class SettingsView extends VerticalLayout {

    private final CancerService cancerService;
    private final PowiatService powiatService;

    @Autowired
    public SettingsView(CancerService cancerService, PowiatService powiatService) {
        this.cancerService = cancerService;
        this.powiatService = powiatService;
        Span cancerDataSpan = new CancerDataSpan(cancerService);
        Span terytDataSpan = new PowiatSettingsSpan(powiatService);
        this.add(cancerDataSpan, terytDataSpan);
    }

}
