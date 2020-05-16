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
import com.syndybat.chartjs.ClickEvent;
import com.szwadronwilkowalfa.statystyki.services.CancerResourceService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class StatisticsView extends Div {

    private final CancerResourceService cancerResourceService;

    @Autowired
    public StatisticsView(CancerResourceService cancerResourceService){
        this.cancerResourceService = cancerResourceService;
        this.add(createBarChart());
    }

    private Component createBarChart() {
        BarScale scale = new BarScale()
                .addxAxes(new XAxis<LinearTicks>().setStacked(true))
                .addyAxes(new YAxis<LinearTicks>().setStacked(true));

        BarOptions options = new BarOptions();
        options.setResponsive(true).setScales(scale);

        BarData barData = new BarData();
        barData.setLabels("First", "Second", "Last");
        barData.addDataset(new BarDataset().addBackgroundColor(Color.BLUE).setLabel("lion").addData(1).addData(2).addData(3));
        barData.addDataset(new BarDataset().addBackgroundColor(Color.DARK_GREEN).setLabel("soldier").addData(11).addData(21).addData(30));
        barData.addDataset(new BarDataset().addBackgroundColor(Color.BLACK).setLabel("home").addData(20).addData(40).addData(20));
        BarChart barChart = new BarChart(barData, options).setHorizontal();

        ChartJs chart = new ChartJs(barChart.toJson());

        chart.addClickListener(new ComponentEventListener<ClickEvent>()
        {
            @Override
            public void onComponentEvent(ClickEvent clickEvent)
            {
                Notification.show(String.format("%s : %s : %s", clickEvent.getLabel(), clickEvent.getDatasetLabel(), clickEvent.getValue()),
                        3000, Notification.Position.TOP_CENTER);
            }
        });

        return chart;
    }


}
