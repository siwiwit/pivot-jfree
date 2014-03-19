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
import org.apache.pivot.collections.List;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.data.category.CategoryDataset;





public class SpiderWebViewSkin extends JFreeChartViewSkin {

    private static StandardChartTheme STANDARD_THEME = new StandardChartTheme("JFree");

    @Override
    public ChartView.Element getElementAt(int x, int y) {
        ChartView.Element element = null;

        ChartEntity chartEntity = getChartEntityAt(x, y);
        if (chartEntity instanceof CategoryItemEntity) {
            CategoryItemEntity categoryItemEntity = (CategoryItemEntity) chartEntity;
            CategoryDataset dataset = categoryItemEntity.getDataset();

            String columnKey = (String) categoryItemEntity.getColumnKey();
            int columnIndex = dataset.getColumnIndex(columnKey);

            String rowKey = (String) categoryItemEntity.getRowKey();
            int rowIndex = dataset.getRowIndex(rowKey);

            element = new ChartView.Element(rowIndex, columnIndex);
        } else if (chartEntity instanceof XYItemEntity) {
            XYItemEntity xyItemEntity = (XYItemEntity) chartEntity;
            element = new ChartView.Element(xyItemEntity.getSeriesIndex(),
                    xyItemEntity.getItem());
        }

        return element;
    }

    @Override
    protected JFreeChart createChart() {
        ChartView chartView = (ChartView) getComponent();

        String title = chartView.getTitle();
        boolean showLegend = chartView.getShowLegend();
        String seriesNameKey = chartView.getSeriesNameKey();

        List<?> chartData = chartView.getChartData();

        JFreeChart chart;
        ChartView.CategorySequence categories = chartView.getCategories();

        CategorySeriesDataset dataset = new CategorySeriesDataset(categories,
                seriesNameKey, chartData);

        // JFree ChartFactory is missing factory method for SpiderWebPlot
        SpiderWebPlot plot = new SpiderWebPlot(dataset);
        chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot,
                showLegend);

        STANDARD_THEME.apply(chart);

        return chart;
    }

}
