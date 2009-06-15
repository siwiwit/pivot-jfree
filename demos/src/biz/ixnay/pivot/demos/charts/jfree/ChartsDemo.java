/*
 * Copyright (c) 2009 VMware, Inc.
 *
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
package biz.ixnay.pivot.demos.charts.jfree;

import org.apache.pivot.beans.BeanDictionary;
import org.apache.pivot.charts.AreaChartView;
import org.apache.pivot.charts.BarChartView;
import org.apache.pivot.charts.ChartView;
import org.apache.pivot.charts.HighLowChartView;
import org.apache.pivot.charts.LineChartView;
import org.apache.pivot.charts.PieChartView;
import org.apache.pivot.collections.Dictionary;
import org.apache.pivot.collections.List;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Alert;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.ComponentMouseButtonListener;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Mouse;
import org.apache.pivot.wtk.Window;
import org.apache.pivot.wtkx.WTKX;
import org.apache.pivot.wtkx.WTKXSerializer;

public class ChartsDemo implements Application {
    private Window window = null;

    @WTKX(id="pieCharts.pieChartView") private PieChartView pieChartView;
    @WTKX(id="barCharts.categoryBarChartView") private BarChartView categoryBarChartView;
    @WTKX(id="barCharts.xyBarChartView") private BarChartView xyBarChartView;
    @WTKX(id="lineCharts.categoryLineChartView") private LineChartView categoryLineChartView;
    @WTKX(id="lineCharts.xyLineChartView") private LineChartView xyLineChartView;
    @WTKX(id="areaCharts.categoryAreaChartView") private AreaChartView categoryAreaChartView;
    @WTKX(id="areaCharts.xyAreaChartView") private AreaChartView xyAreaChartView;
    @WTKX(id="highLowCharts.highLowChartView") private HighLowChartView highLowChartView;

    private ComponentMouseButtonListener chartViewMouseButtonListener =
        new ComponentMouseButtonListener.Adapter() {
        @SuppressWarnings("unchecked")
        public boolean mouseClick(Component component, Mouse.Button button, int x, int y, int count) {
            ChartView chartView = (ChartView)component;
            ChartView.Element element = chartView.getElementAt(x, y);

            if (element != null) {
                int seriesIndex = element.getSeriesIndex();
                int elementIndex = element.getElementIndex();

                String elementLabel;
                ChartView.CategorySequence categories = chartView.getCategories();
                if (categories.getLength() > 0) {
                    elementLabel = "\"" + chartView.getCategories().get(elementIndex).getLabel() + "\"";
                } else {
                    elementLabel = Integer.toString(elementIndex);
                }

                List<?> chartData = chartView.getChartData();
                Object series = chartData.get(seriesIndex);

                Dictionary<String, Object> seriesDictionary;
                if (series instanceof Dictionary<?, ?>) {
                    seriesDictionary = (Dictionary<String, Object>)series;
                } else {
                    seriesDictionary = new BeanDictionary(series);
                }

                String seriesNameKey = chartView.getSeriesNameKey();

                Alert.alert("You clicked element " + elementLabel + " in \""
                    + seriesDictionary.get(seriesNameKey) + "\".", window);
            }

            return false;
        }
    };

    public void startup(Display display, Map<String, String> properties)
        throws Exception {
        WTKXSerializer wtkxSerializer = new WTKXSerializer();
        window = (Window)wtkxSerializer.readObject(this, "charts_demo.wtkx");
        wtkxSerializer.bind(this, ChartsDemo.class);

        pieChartView.getComponentMouseButtonListeners().add(chartViewMouseButtonListener);
        categoryBarChartView.getComponentMouseButtonListeners().add(chartViewMouseButtonListener);
        xyBarChartView.getComponentMouseButtonListeners().add(chartViewMouseButtonListener);
        categoryLineChartView.getComponentMouseButtonListeners().add(chartViewMouseButtonListener);
        xyLineChartView.getComponentMouseButtonListeners().add(chartViewMouseButtonListener);
        categoryAreaChartView.getComponentMouseButtonListeners().add(chartViewMouseButtonListener);
        xyAreaChartView.getComponentMouseButtonListeners().add(chartViewMouseButtonListener);
        highLowChartView.getComponentMouseButtonListeners().add(chartViewMouseButtonListener);

        window.open(display);
    }

    public boolean shutdown(boolean optional) {
        if (window != null) {
            window.close();
        }

        return true;
    }

    public void suspend() {
    }

    public void resume() {
    }

    public static void main(String[] args) {
        DesktopApplicationContext.main(ChartsDemo.class, args);
    }
}
