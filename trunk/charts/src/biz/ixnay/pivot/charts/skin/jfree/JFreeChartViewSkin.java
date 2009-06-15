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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.EntityCollection;

import org.apache.pivot.charts.skin.ChartViewSkin;
import org.apache.pivot.wtk.Dimensions;

/**
 * Abstract base class for chart view skins.
 *
 * @author gbrown
 */
public abstract class JFreeChartViewSkin extends ChartViewSkin {
    private BufferedImage bufferedImage = null;

    private JFreeChart chart = null;
    private ChartRenderingInfo chartRenderingInfo = new ChartRenderingInfo();

    private static final int PREFERRED_WIDTH = 320;
    private static final int PREFERRED_HEIGHT = 240;

    public int getPreferredWidth(int height) {
        return PREFERRED_WIDTH;
    }

    public int getPreferredHeight(int width) {
        return PREFERRED_HEIGHT;
    }

    public Dimensions getPreferredSize() {
        return new Dimensions(getPreferredWidth(-1), getPreferredHeight(-1));
    }

    public void paint(Graphics2D graphics) {
        int width = getWidth();
        int height = getHeight();

        if (bufferedImage == null
            || bufferedImage.getWidth() != width
            || bufferedImage.getHeight() != height) {
            chart = createChart();

            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D bufferedImageGraphics = (Graphics2D)bufferedImage.getGraphics();

            java.awt.Rectangle area = new java.awt.Rectangle(0, 0, width, height);
            chart.setBackgroundPaint(null);
            chart.getPlot().setBackgroundPaint(getBackgroundColor());
            chart.draw(bufferedImageGraphics, area, chartRenderingInfo);

            bufferedImageGraphics.dispose();
        }

        graphics.drawImage(bufferedImage, 0, 0, null);
    }

    @Override
    public void repaintComponent() {
        super.repaintComponent();
        bufferedImage = null;
    }

    protected abstract JFreeChart createChart();

    protected ChartEntity getChartEntityAt(int x, int y) {
        ChartEntity result = null;

        if (chartRenderingInfo != null) {
            EntityCollection entities = chartRenderingInfo.getEntityCollection();
            result = (entities != null) ? entities.getEntity(x, y) : null;
        }

        return result;
    }
}
