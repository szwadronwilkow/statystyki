package com.szwadronwilkowalfa.statystyki.views;


import be.ceau.chart.BarChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
import be.ceau.chart.options.BarOptions;
import be.ceau.chart.options.scales.BarScale;
import be.ceau.chart.options.scales.XAxis;
import be.ceau.chart.options.scales.YAxis;
import be.ceau.chart.options.ticks.LinearTicks;
import com.syndybat.chartjs.ChartJs;
import com.szwadronwilkowalfa.statystyki.data.CancerStatistics;
import com.szwadronwilkowalfa.statystyki.data.PlotData;
import com.szwadronwilkowalfa.statystyki.model.Powiat;
import com.szwadronwilkowalfa.statystyki.services.PowiatService;
import com.szwadronwilkowalfa.statystyki.views.components.Alert;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Scope("prototype")
public class StatisticsView extends VerticalLayout {

    private final CancerStatistics cancerStatistics;
    private final PowiatService powiatService;
    private Component barChart;
    private Div detailsPlaceholder;
    private ComboBox<String> comboBox;

    @Autowired
    public StatisticsView(CancerStatistics cancerStatistics, PowiatService powiatService){
        this.cancerStatistics = cancerStatistics;
        this.powiatService = powiatService;
        init();
    }

    private void createRefreshButton() {
        Button button = new Button("Refresh");
        button.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> init());
        this.add(button);
    }

    public void init(){
        this.removeAll();
        createRefreshButton();
        Optional<PlotData> plotData = cancerStatistics.getPlotDataPerYears();
        plotData.ifPresent(data -> {
            barChart = createBarChart(data);
            this.add(barChart);
        });
        this.add(new Label("Details:"));
        Span span = new Span();
        span.add(createDropDownWithLands());
        span.add(createShowDetailButton());
        this.add(span);
        detailsPlaceholder = new Div();
        detailsPlaceholder.setSizeFull();
        this.add(detailsPlaceholder);
        setSizeFull();
    }

    private Component createShowDetailButton() {
        Button button = new Button("Show");
        button.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                createDetailPlot();
            }
        });
        return button;
    }

    private void createDetailPlot() {
        String value = comboBox.getValue();
        if (StringUtils.isEmpty(value)) {
            Alert.error("Invalid selection!");
            return;
        }
        Powiat powiat = powiatService.findByName(value);
        if (powiat == null){
            Alert.error("Invalid selection!");
            return;
        }
        Optional<PlotData> plotData = cancerStatistics.getPlotDataPerYearsPerLand(powiat);
        plotData.ifPresent(data -> {
            barChart = createBarChart(data);
            this.detailsPlaceholder.removeAll();
            this.detailsPlaceholder.add(barChart);
        });
    }

    private Component createDropDownWithLands() {
        comboBox = new ComboBox<>();
        List<Powiat> lands = powiatService.getLands();
        if (!CollectionUtils.isEmpty(lands)){
            comboBox.setItems(lands.stream().map(Powiat::getNazwa).collect(Collectors.toList()));
        }
        return comboBox;
    }

    private Component createBarChart(PlotData plotData) {
        BarScale scale = new BarScale()
                .addxAxes(new XAxis<LinearTicks>().setStacked(true))
                .addyAxes(new YAxis<LinearTicks>().setStacked(true));

        BarOptions options = new BarOptions();
        options.setResponsive(true).setScales(scale);

        BarData barData = new BarData();

        barData.setLabels(plotData.getLabels());
        BarDataset dataset = new BarDataset().addBackgroundColor(Color.BLUE).setLabel(plotData.getTitle()).addData(1).addData(2).addData(3);
        dataset.setData(plotData.getValues());
        barData.addDataset(dataset);
        BarChart barChart = new BarChart(barData, options).setVertical();

        ChartJs chart = new ChartJs(barChart.toJson());
        Div div = new Div();
        div.add(chart);
        div.setWidth("50%");
        return div;
        //test
    }

}
