/*
 * Copyright 2010 G. Brown, ixnay.biz.
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

import org.apache.pivot.beans.BeanSerializer;
import org.apache.pivot.charts.PieChartView;
import org.apache.pivot.charts.content.CategorySeries;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Window;

public class DynamicDataDemo implements Application {
    private Window window = null;
    private PieChartView pieChartView = null;

    public void startup(Display display, Map<String, String> properties)
        throws Exception {
        BeanSerializer beanSerializer = new BeanSerializer();
        window = (Window)beanSerializer.readObject(this, "dynamic_data_demo.bxml");

        pieChartView = (PieChartView)beanSerializer.get("pieChartView");

        CategorySeries categorySeries = new CategorySeries();
        categorySeries.put("name", "Example Series 1");
        categorySeries.put("a", 43.2);
        categorySeries.put("b", 27.9);
        categorySeries.put("c", 79.5);

        ArrayList<CategorySeries> pieChartData = new ArrayList<CategorySeries>();
        pieChartData.add(categorySeries);
        pieChartView.setChartData(pieChartData);

        window.open(display);
    }

    public boolean shutdown(boolean optional) {
        if (window != null) {
            window.close();
        }

        return false;
    }

    public void suspend() {
    }

    public void resume() {
    }

    public static void main(String[] args) {
        DesktopApplicationContext.main(DynamicDataDemo.class, args);
    }
}
