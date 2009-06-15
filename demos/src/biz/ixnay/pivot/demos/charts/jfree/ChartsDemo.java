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

import pivot.beans.BeanDictionary;
import pivot.charts.AreaChartView;
import pivot.charts.BarChartView;
import pivot.charts.ChartView;
import pivot.charts.HighLowChartView;
import pivot.charts.LineChartView;
import pivot.charts.PieChartView;
import pivot.collections.Dictionary;
import pivot.collections.List;
import pivot.wtk.Alert;
import pivot.wtk.Application;
import pivot.wtk.Component;
import pivot.wtk.ComponentMouseButtonListener;
import pivot.wtk.DesktopApplicationContext;
import pivot.wtk.Display;
import pivot.wtk.Mouse;
import pivot.wtk.Window;
import pivot.wtkx.Bindable;

public class ChartsDemo extends Bindable implements Application {
    @Load(resourceName="charts_demo.wtkx") private Window window;
    @Bind(fieldName="window", id="pieCharts.pieChartView") private PieChartView pieChartView;
    @Bind(fieldName="window", id="barCharts.categoryBarChartView") private BarChartView categoryBarChartView;
    @Bind(fieldName="window", id="barCharts.xyBarChartView") private BarChartView xyBarChartView;
    @Bind(fieldName="window", id="lineCharts.categoryLineChartView") private LineChartView categoryLineChartView;
    @Bind(fieldName="window", id="lineCharts.xyLineChartView") private LineChartView xyLineChartView;
    @Bind(fieldName="window", id="areaCharts.categoryAreaChartView") private AreaChartView categoryAreaChartView;
    @Bind(fieldName="window", id="areaCharts.xyAreaChartView") private AreaChartView xyAreaChartView;
    @Bind(fieldName="window", id="highLowCharts.highLowChartView") private HighLowChartView highLowChartView;

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

    public void startup(Display display, Dictionary<String, String> properties)
        throws Exception {
        bind();

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
