package com.szwadronwilkowalfa.statystyki.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PlotData {
    private String title = "";
    private List<String> labels = new ArrayList<>();
    private List<BigDecimal> values = new ArrayList<>();

    public PlotData(String title) {
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public BigDecimal[] getValues() {
        return this.values.toArray(new BigDecimal[values.size()]);
    }

    public void addLabel(String label){
        this.labels.add(label);
    }

    public String[] getLabels(){
        return this.labels.toArray(new String[labels.size()]);
    }

    public void addValue(BigDecimal value) {
        this.values.add(value);
    }

    public boolean isEmpty() {
        return labels.isEmpty();
    }
}
