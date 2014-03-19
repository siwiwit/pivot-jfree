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

import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.plot.Plot;
import org.jfree.data.general.ValueDataset;

import org.apache.pivot.charts.ChartView;
import org.apache.pivot.charts.SingleValueChartView;

/**
 * Base class for all single-valued chart skins.
 *
 * @author rwhitcomb
 */
public abstract class SingleValueChartSkin extends JFreeChartViewSkin {

    @Override
    public ChartView.Element getElementAt(int x, int y) {
        ChartView.Element element = null;

        ChartEntity chartEntity = getChartEntityAt(x, y);
        // TODO: are there any useful / necessary entities here?

        return element;
    }

    protected ValueDataset getDataset() {
        SingleValueChartView chartView = (SingleValueChartView)getComponent();
        ValueDataset value = new SingleValueDataset(chartView.getValue());
    return value;
    }

    protected JFreeChart createChart(Plot plot) {
        if (plot == null) {
            throw new IllegalArgumentException("plot cannot be null.");
        }

        SingleValueChartView chartView = (SingleValueChartView)getComponent();

        String title = chartView.getTitle();

        JFreeChart chart = new JFreeChart(title, plot);

        // TODO: anything else that we need to set before returning?

        return chart;
    }

}
