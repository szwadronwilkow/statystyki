package com.szwadronwilkowalfa.statystyki;

import com.szwadronwilkowalfa.statystyki.views.AboutView;
import com.szwadronwilkowalfa.statystyki.views.HomeView;
import com.szwadronwilkowalfa.statystyki.views.SettingsView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.tabs.PagedTabs;

@Route()
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends AppLayout {

    private final SettingsView settingsView;

    @Autowired
    public MainView(SettingsView settingsView) {
        this.settingsView = settingsView;
        Image img = new Image("https://i.imgur.com/GPpnszs.png", "Vaadin Logo");
        img.setHeight("44px");

        PagedTabs tabs = new PagedTabs();
        tabs.add(new HomeView(), "Home");
        tabs.add(new AboutView(), "About");
        tabs.add(settingsView, "Settings");

        addToNavbar(img, tabs);
    }

}
