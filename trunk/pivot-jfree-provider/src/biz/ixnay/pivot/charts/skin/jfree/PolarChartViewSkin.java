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
import org.apache.pivot.charts.PolarChartView;
import org.apache.pivot.collections.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.data.category.CategoryDataset;

public class PolarChartViewSkin extends JFreeChartViewSkin {

    public Element getElementAt(int x, int y) {
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

    protected JFreeChart createChart() {
        PolarChartView chartView = (PolarChartView) getComponent();
        String title = chartView.getTitle();
        boolean showLegend = chartView.getShowLegend();

        String seriesNameKey = chartView.getSeriesNameKey();
        List<?> chartData = chartView.getChartData();

        JFreeChart chart = ChartFactory.createPolarChart(title,
                new XYSeriesDataset(seriesNameKey, chartData), showLegend,
                false, false);
        return chart;
    }

}
