package com.szwadronwilkowalfa.statystyki;

import com.szwadronwilkowalfa.statystyki.views.AboutView;
import com.szwadronwilkowalfa.statystyki.views.HomeView;
import com.szwadronwilkowalfa.statystyki.views.SettingsView;
import com.szwadronwilkowalfa.statystyki.views.StatisticsView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.tabs.PagedTabs;

@Route("")
public class MainView extends VerticalLayout {

    private final SettingsView settingsView;
    private final StatisticsView statisticsView;

    @Autowired
    public MainView(SettingsView settingsView, StatisticsView statisticsView) {
        this.settingsView = settingsView;
        this.statisticsView = statisticsView;

        PagedTabs tabs = new PagedTabs();
        tabs.add(new HomeView(), "Home");
        tabs.add(statisticsView, "Statistics");
        tabs.add(settingsView, "Settings");
        tabs.add(new AboutView(), "About");

        addComponentAsFirst(tabs);
    }

}
