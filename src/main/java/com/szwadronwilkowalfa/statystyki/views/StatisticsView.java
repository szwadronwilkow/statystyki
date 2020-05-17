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
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Scope("prototype")
public class StatisticsView extends VerticalLayout {

    private final CancerStatistics cancerStatistics;

    @Autowired
    public StatisticsView( CancerStatistics cancerStatistics){
        this.cancerStatistics = cancerStatistics;
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
        plotData.ifPresent(data -> this.add(createBarChart(data)));
        setSizeFull();
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
    }

}
