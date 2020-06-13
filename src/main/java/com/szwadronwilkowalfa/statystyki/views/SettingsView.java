package com.szwadronwilkowalfa.statystyki.views;

import com.szwadronwilkowalfa.statystyki.MainView;
import com.szwadronwilkowalfa.statystyki.services.CancerService;
import com.szwadronwilkowalfa.statystyki.services.PowiatService;
import com.szwadronwilkowalfa.statystyki.services.UrlService;
import com.szwadronwilkowalfa.statystyki.views.components.CancerDataSpan;
import com.szwadronwilkowalfa.statystyki.views.components.PowiatSettingsSpan;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
@Route(SettingsView.ROUTE)
public class SettingsView extends VerticalLayout {

    public static final String ROUTE = "settings";

    private final CancerService cancerService;
    private final PowiatService powiatService;
    private final UrlService urlService;

    @Autowired
    public SettingsView(CancerService cancerService, PowiatService powiatService, UrlService urlService) {
        this.cancerService = cancerService;
        this.powiatService = powiatService;
        this.urlService = urlService;

        Span cancerDataSpan = new CancerDataSpan(cancerService, urlService);
        Span terytDataSpan = new PowiatSettingsSpan(powiatService);
        this.add(cancerDataSpan, terytDataSpan);
    }

}
