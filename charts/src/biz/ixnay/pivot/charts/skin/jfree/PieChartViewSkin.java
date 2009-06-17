/*
 * Copyright (c) 2009 VMware, Inc.
 * Copyright 2009 G. Brown, ixnay.biz.
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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.PieSectionEntity;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.util.TableOrder;

import org.apache.pivot.charts.PieChartView;
import org.apache.pivot.charts.ChartView;
import org.apache.pivot.collections.HashMap;
import org.apache.pivot.collections.List;
import org.apache.pivot.collections.Map;

/**
 * Pie chart view skin.
 *
 * @author gbrown
 */
public class PieChartViewSkin extends JFreeChartViewSkin {
    private Map<String, Number> explodePercentages = new HashMap<String, Number>();

    private boolean threeDimensional = false;
    private boolean darkerSides = false;
    private double depthFactor = 0.10d;

    public ChartView.Element getElementAt(int x, int y) {
        ChartView.Element element = null;

        ChartEntity chartEntity = getChartEntityAt(x, y);
        if (chartEntity instanceof PieSectionEntity) {
            PieSectionEntity pieSectionEntity = (PieSectionEntity)chartEntity;
            int sectionIndex = pieSectionEntity.getSectionIndex();
            int seriesIndex = pieSectionEntity.getPieIndex();

            element = new ChartView.Element(seriesIndex, sectionIndex);
        }

        return element;
    }

    protected JFreeChart createChart() {
        PieChartView chartView = (PieChartView)getComponent();

        String title = chartView.getTitle();
        boolean showLegend = chartView.getShowLegend();

        ChartView.CategorySequence categories = chartView.getCategories();
        String seriesNameKey = chartView.getSeriesNameKey();
        List<?> chartData = chartView.getChartData();


        JFreeChart chart;
        if (threeDimensional) {
            if (chartData.getLength() > 1) {
                CategorySeriesDataset dataset = new CategorySeriesDataset(categories,
                    seriesNameKey, chartData);

                chart = ChartFactory.createMultiplePieChart3D(title, dataset, TableOrder.BY_ROW,
                    showLegend, false, false);
            } else {
                PieSeriesDataset dataset = new PieSeriesDataset(categories, chartData.get(0));
                chart = ChartFactory.createPieChart3D(title, dataset, showLegend, false, false);

                PiePlot3D plot = (PiePlot3D)chart.getPlot();
                plot.setDarkerSides(darkerSides);
                plot.setDepthFactor(depthFactor);
            }
        } else {
            if (chartData.getLength() > 1) {
                CategorySeriesDataset dataset = new CategorySeriesDataset(categories,
                    seriesNameKey, chartData);

                chart = ChartFactory.createMultiplePieChart(title, dataset, TableOrder.BY_ROW,
                    showLegend, false, false);
            } else {
                PieSeriesDataset dataset = new PieSeriesDataset(categories, chartData.get(0));
                chart = ChartFactory.createPieChart(title, dataset, showLegend, false, false);

                HashMap<String, String> categoryLabels = new HashMap<String, String>();
                for (int i = 0, n = categories.getLength(); i < n; i++) {
                    ChartView.Category category = categories.get(i);
                    categoryLabels.put(category.getKey(), category.getLabel());
                }

                PiePlot plot = (PiePlot)chart.getPlot();
                for (String categoryKey : explodePercentages) {
                    plot.setExplodePercent(categoryLabels.get(categoryKey),
                        explodePercentages.get(categoryKey).doubleValue());
                }
            }
        }

        return chart;
    }

    public boolean isThreeDimensional() {
        return threeDimensional;
    }

    public void setThreeDimensional(boolean threeDimensional) {
        this.threeDimensional = threeDimensional;
        repaintComponent();
    }

    public Map<String, Number> getExplodePercentages() {
        return explodePercentages;
    }

    public void setExplodePercentages(Map<String, Number> explodePercentages) {
        this.explodePercentages = explodePercentages;
        repaintComponent();
    }

    public boolean getDarkerSides() {
        return darkerSides;
    }

    public void setDarkerSides(boolean darkerSides) {
        this.darkerSides = darkerSides;
        repaintComponent();
    }

    public double getDepthFactor() {
        return depthFactor;
    }

    public void setDepthFactor(double depthFactor) {
        if (depthFactor < 0) {
            throw new IllegalArgumentException("depthFactor is negative.");
        }

        this.depthFactor = depthFactor;
        repaintComponent();
    }
}
