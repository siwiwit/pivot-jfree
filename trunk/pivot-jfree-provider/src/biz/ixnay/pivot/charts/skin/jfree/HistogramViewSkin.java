/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package biz.ixnay.pivot.charts.skin.jfree;

import org.apache.pivot.charts.ChartView;
import org.apache.pivot.charts.ChartView.Element;
import org.apache.pivot.charts.HistogramView;
import org.apache.pivot.charts.content.HistogramSeries;
import org.apache.pivot.collections.List;
import org.apache.pivot.wtk.content.ListItem;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

public class HistogramViewSkin extends JFreeChartViewSkin {

    public Element getElementAt(int x, int y) {
        ChartView.Element element = null;

        ChartEntity chartEntity = getChartEntityAt(x, y);
        if (chartEntity instanceof XYItemEntity) {
            XYItemEntity xyItemEntity = (XYItemEntity) chartEntity;
            HistogramDataset dataSet=(HistogramDataset)((XYItemEntity) chartEntity).getDataset();
            int series=xyItemEntity.getSeriesIndex();
            int item=xyItemEntity.getItem();

            double binStart=dataSet.getStartXValue(series, item);
            double binEnd=dataSet.getEndXValue(series, item);
            double binValue=dataSet.getXValue(series, item);
            double frequency=dataSet.getYValue(series, item);

            element = new HistogramView.HistogramBin(series,item,binStart,binValue,binEnd,frequency);

        }
        return element;
    }

    protected JFreeChart createChart() {
        HistogramView chartView = (HistogramView) getComponent();
        String title = chartView.getTitle();
        String verticalLabel = chartView.getVerticalAxisLabel();
        String horizontalLabel = chartView.getHorizontalAxisLabel();
        boolean showLegend = chartView.getShowLegend();
        final JFreeChart chart = ChartFactory.createHistogram(title,
                horizontalLabel, verticalLabel, createDataSet(chartView),
                PlotOrientation.VERTICAL, showLegend, false, false);
        createMarkers(chart, chartView);
        return chart;
    }

    private void createMarkers(JFreeChart chart,ChartView chartView) {
        for (org.apache.pivot.charts.content.ValueMarker valueMarker : chartView.getValueMarkers()) {
            final Marker target = new ValueMarker(valueMarker.getValue());
            target.setPaint(valueMarker.getColor());
            target.setLabel(valueMarker.getLabel());
            target.setLabelAnchor(RectangleAnchor.TOP);
            target.setLabelTextAnchor(TextAnchor.TOP_LEFT);
            ((XYPlot)chart.getPlot()).addDomainMarker(target);
        }
    }

    private HistogramDataset createDataSet(HistogramView chartView) {
        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.FREQUENCY);
        for (Object object : chartView.getChartData()) {
            HistogramSeries<?> histogramSeries=(HistogramSeries<?>)object;
            dataset.addSeries(histogramSeries.getName(), getValues(histogramSeries), histogramSeries.getBinCount());
        }

        /*double[] values = getValues(chartView.getChartData());
        int binCount = chartView.getBinCount();


        dataset.addSeries("", values, binCount);*/

        return dataset;
    }

    private double[] getValues(List<?> seriesValues) {
        double[] values = new double[seriesValues.getLength()];
        int i = 0;
        for (Object value : seriesValues) {
            if (value instanceof Double) {
                values[i] = ((Double) value).doubleValue();
            } else if (value instanceof ListItem){
                values[i] = Double.parseDouble(((ListItem)value).getText());
            } else {
                throw new java.lang.IllegalArgumentException(value.getClass().getName()+" Histogram series may contain items of type Double or ListItem.");
            }
            i++;
        }
        return values;
    }

}
