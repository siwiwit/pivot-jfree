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
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;

import org.apache.pivot.charts.ChartView;
import org.apache.pivot.charts.LineChartView;
import org.apache.pivot.collections.List;

/**
 * Line chart view skin.
 *
 * @author gbrown
 */
public class LineChartViewSkin extends JFreeChartViewSkin {
    private boolean threeDimensional = false;
    private double categoryLabelRotation = 0;
    private Range rangeAxisRange = null;

    @Override
    public ChartView.Element getElementAt(int x, int y) {
        ChartView.Element element = null;

        ChartEntity chartEntity = getChartEntityAt(x, y);

        if (chartEntity instanceof CategoryItemEntity) {
            CategoryItemEntity categoryItemEntity = (CategoryItemEntity)chartEntity;
            CategoryDataset dataset = categoryItemEntity.getDataset();

            String columnKey = (String)categoryItemEntity.getColumnKey();
            int columnIndex = dataset.getColumnIndex(columnKey);

            String rowKey = (String)categoryItemEntity.getRowKey();
            int rowIndex = dataset.getRowIndex(rowKey);

            element = new ChartView.Element(rowIndex, columnIndex);
        } else if (chartEntity instanceof XYItemEntity) {
            XYItemEntity xyItemEntity = (XYItemEntity)chartEntity;
            element = new ChartView.Element(xyItemEntity.getSeriesIndex(),
                xyItemEntity.getItem());
        }

        return element;
    }

    @Override
    protected JFreeChart createChart() {
        LineChartView chartView = (LineChartView)getComponent();

        String title = chartView.getTitle();
        String horizontalAxisLabel = chartView.getHorizontalAxisLabel();
        String verticalAxisLabel = chartView.getVerticalAxisLabel();
        boolean showLegend = chartView.getShowLegend();

        String seriesNameKey = chartView.getSeriesNameKey();
        List<?> chartData = chartView.getChartData();

        JFreeChart chart;
        ChartView.CategorySequence categories = chartView.getCategories();
        if (categories.getLength() > 0) {
            CategorySeriesDataset dataset = new CategorySeriesDataset(categories, seriesNameKey, chartData);

            if (threeDimensional) {
                chart = ChartFactory.createLineChart3D(title, horizontalAxisLabel, verticalAxisLabel,
                    dataset, PlotOrientation.VERTICAL, showLegend, false, false);
            } else {
                chart = ChartFactory.createLineChart(title, horizontalAxisLabel, verticalAxisLabel,
                    dataset, PlotOrientation.VERTICAL, showLegend, false, false);
            }

            CategoryPlot plot = (CategoryPlot)chart.getPlot();
            CategoryAxis domainAxis = plot.getDomainAxis();
            CategoryLabelPositions categoryLabelPositions =
                CategoryLabelPositions.createUpRotationLabelPositions(categoryLabelRotation);
            domainAxis.setCategoryLabelPositions(categoryLabelPositions);

            if (rangeAxisRange != null) {
                ValueAxis rangeAxis = plot.getRangeAxis();
                rangeAxis.setRange(rangeAxisRange);
            }
        } else {
            chart = ChartFactory.createXYLineChart(title, horizontalAxisLabel, verticalAxisLabel,
                new XYSeriesDataset(seriesNameKey, chartData),
                PlotOrientation.VERTICAL, showLegend, false, false);

            if (rangeAxisRange != null) {
                XYPlot plot = (XYPlot)chart.getPlot();
                ValueAxis rangeAxis = plot.getRangeAxis();
                rangeAxis.setRange(rangeAxisRange);
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

    public double getCategoryLabelRotation() {
        return categoryLabelRotation;
    }

    public void setCategoryLabelRotation(double categoryLabelRotation) {
        this.categoryLabelRotation = categoryLabelRotation;
        repaintComponent();
    }

    public void setRangeAxisLowerBound(double lower) {
        this.rangeAxisRange = new Range(lower, getRangeAxisUpperBound());
        repaintComponent();
    }

    public double getRangeAxisLowerBound() {
        return rangeAxisRange == null ? 0.0d : rangeAxisRange.getLowerBound();
    }

    public void setRangeAxisUpperBound(double upper) {
        this.rangeAxisRange = new Range(getRangeAxisLowerBound(), upper);
        repaintComponent();
    }

    public double getRangeAxisUpperBound() {
        return rangeAxisRange == null ? 1.0d : rangeAxisRange.getUpperBound();
    }

}
