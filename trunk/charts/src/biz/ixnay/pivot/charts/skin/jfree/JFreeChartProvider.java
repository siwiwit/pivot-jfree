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
package biz.ixnay.pivot.charts.skin.jfree;

import org.apache.pivot.charts.AreaChartView;
import org.apache.pivot.charts.BarChartView;
import org.apache.pivot.charts.ChartView;
import org.apache.pivot.charts.HighLowChartView;
import org.apache.pivot.charts.LineChartView;
import org.apache.pivot.charts.PieChartView;
import org.apache.pivot.charts.Provider;
import org.apache.pivot.wtk.Skin;

/**
 * JFreeChart chart provider.
 *
 * @author gbrown
 */
public class JFreeChartProvider implements Provider {
    public Class<? extends Skin> getSkinClass(Class<? extends ChartView> chartViewClass) {
        Class<? extends Skin> skinClass;

        if (chartViewClass == AreaChartView.class) {
            skinClass = AreaChartViewSkin.class;
        } else if (chartViewClass == BarChartView.class) {
            skinClass = BarChartViewSkin.class;
        } else if (chartViewClass == HighLowChartView.class) {
            skinClass = HighLowChartViewSkin.class;
        } else if (chartViewClass == LineChartView.class) {
            skinClass = LineChartViewSkin.class;
        } else if (chartViewClass == PieChartView.class) {
            skinClass = PieChartViewSkin.class;
        } else {
            throw new IllegalArgumentException();
        }

        return skinClass;
    }
}
