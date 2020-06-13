package com.szwadronwilkowalfa.statystyki;

import com.szwadronwilkowalfa.statystyki.views.AboutView;
import com.szwadronwilkowalfa.statystyki.views.HomeView;
import com.szwadronwilkowalfa.statystyki.views.SettingsView;
import com.szwadronwilkowalfa.statystyki.views.StatisticsView;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.tabs.PagedTabs;
@PageTitle("Cancer incidence statistics")
@Route(MainView.ROUTE)
public class MainView extends VerticalLayout {

    public static final String ROUTE = "main";

    private final SettingsView settingsView;
    private final StatisticsView statisticsView;

    @Autowired
    public MainView(SettingsView settingsView, StatisticsView statisticsView) {
        this.settingsView = settingsView;
        this.statisticsView = statisticsView;

        H1 logo = new H1("Cancer incidence statistics");
        logo.addClassName("logo");

        Anchor logout = new Anchor("logout", "Log out");

        HorizontalLayout header = new HorizontalLayout(logo, logout);
        header.expand(logo);
        header.addClassName("header");
        header.setWidth("100%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addComponentAsFirst(header);

        PagedTabs tabs = new PagedTabs();
        tabs.add(new HomeView(), "Home");
        tabs.add(statisticsView, "Statistics");
        tabs.add(settingsView, "Settings");
        tabs.add(new AboutView(), "About");


        add(tabs);
        setSizeFull();

    }

}
