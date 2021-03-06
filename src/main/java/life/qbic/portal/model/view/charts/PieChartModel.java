package life.qbic.portal.model.view.charts;


import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.PointClickEvent;

import java.util.Arrays;


/**
 * @author fhanssen
 * Example: https://demo.vaadin.com/charts/#PieChart , https://demo.vaadin.com/charts/#DonutChart
 */
public class PieChartModel extends AChartModel<DataSeries> {

    private final DataSeries series;

    public PieChartModel(Configuration configuration, String title, String subtitle, String tabTitle,
                         Tooltip tooltip, Legend legend, PlotOptionsPie options) {
        super(configuration, title,  subtitle, tabTitle, tooltip, legend,options);

        series = new DataSeries();
        super.configuration.setSeries(series);

        options.setAnimation(false);

    }


    public void addData(DataSeries... dataSeries){
        Arrays.stream(dataSeries).forEach(dataSerie -> dataSerie.getData().forEach(series::add));
    }

    public void addData(DataSeriesItem... item){
        Arrays.stream(item).forEach(series::add);
    }

    public void addDonutPieData(DataSeries... dataSeries){
        configuration.setSeries(dataSeries);
    }

    public void clearData(){
        series.clear();
    }

    public String getDataName(PointClickEvent event){
        return series.get(event.getPointIndex()).getName();
    }

}
