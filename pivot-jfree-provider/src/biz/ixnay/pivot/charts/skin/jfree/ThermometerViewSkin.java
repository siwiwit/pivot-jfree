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
import org.jfree.chart.plot.ThermometerPlot;
import org.jfree.data.general.ValueDataset;

import org.apache.pivot.charts.SingleValueChartView;
import org.apache.pivot.charts.ThermometerView;

/**
 * Thermometer chart view skin.
 *
 * @author rwhitcomb
 */
public class ThermometerViewSkin extends SingleValueChartSkin {

    private int bulbRadius = -1;

    private ThermometerView.Units units = null;

    @Override
    protected JFreeChart createChart() {
        ValueDataset value = getDataset();
        ThermometerPlot plot = new ThermometerPlot(value);
        // TODO: set style values before creating the chart object
        SingleValueChartView chartView = (SingleValueChartView)getComponent();
        SingleValueChartView.ValueRange bounds = chartView.getValueBounds();
        plot.setRange(bounds.getLower(), bounds.getUpper());
        for (SingleValueChartView.Range range : SingleValueChartView.Range.values()) {
            SingleValueChartView.ValueRange rangeBounds = chartView.getValueRange(range);
            if (rangeBounds != null) {
                double lower = rangeBounds.getLower();
                double upper = rangeBounds.getUpper();
                switch (range) {
                    case NORMAL:
                        plot.setSubrange(ThermometerPlot.NORMAL, lower, upper);
                        break;
                    case WARNING:
                        plot.setSubrange(ThermometerPlot.WARNING, lower, upper);
                        break;
                    case CRITICAL:
                        plot.setSubrange(ThermometerPlot.CRITICAL, lower, upper);
                        break;
                }
            }
        }
        if (units != null) {
        switch (units) {
        case NONE:
            plot.setUnits(ThermometerPlot.UNITS_NONE);
            break;
        case CELSIUS:
            plot.setUnits(ThermometerPlot.UNITS_CELCIUS);
            break;
        case FAHRENHEIT:
            plot.setUnits(ThermometerPlot.UNITS_FAHRENHEIT);
            break;
        case KELVIN:
            plot.setUnits(ThermometerPlot.UNITS_KELVIN);
            break;
        }
        }
        if (bulbRadius > 0) {
            plot.setBulbRadius(bulbRadius);
        }
        return createChart(plot);
    }

    public int getBulbRadius() {
        return this.bulbRadius;
    }

    public void setBulbRadius(int radius) {
        this.bulbRadius = radius;
        repaintComponent();
    }

    public ThermometerView.Units getUnits() {
        return this.units;
    }

    public void setUnits(String units) {
        setUnits(ThermometerView.Units.valueOf(units));
    }

    public void setUnits(ThermometerView.Units units) {
        this.units = units;
        repaintComponent();
    }

    // TODO: more style settings here

}
