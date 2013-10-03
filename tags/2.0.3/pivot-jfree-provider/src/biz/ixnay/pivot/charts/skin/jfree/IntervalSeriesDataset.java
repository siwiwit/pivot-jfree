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

import org.jfree.data.xy.IntervalXYDataset;

import org.apache.pivot.collections.Dictionary;
import org.apache.pivot.collections.List;

/**
 * Implementation of JFreeChart IntervalXYDataset.
 *
 * @author gbrown
 */
public class IntervalSeriesDataset extends XYSeriesDataset implements IntervalXYDataset {
    public static final String WIDTH_KEY = "width";
    public static final String HEIGHT_KEY = "height";

    public IntervalSeriesDataset(String seriesNameKey, List<?> chartData) {
        super(seriesNameKey, chartData);
    }

    @Override
    public Number getStartX(int seriesIndex, int itemIndex) {
        return getX(seriesIndex, itemIndex);
    }

    @Override
    public double getStartXValue(int seriesIndex, int itemIndex) {
        return getX(seriesIndex, itemIndex).doubleValue();
    }

    @Override
    public Number getEndX(int seriesIndex, int itemIndex) {
        return getXValue(seriesIndex, itemIndex) + getWidthValue(seriesIndex, itemIndex);
    }

    @Override
    public double getEndXValue(int seriesIndex, int itemIndex) {
        return getEndX(seriesIndex, itemIndex).doubleValue();
    }

    @Override
    public Number getStartY(int seriesIndex, int itemIndex) {
        return getY(seriesIndex, itemIndex);
    }

    @Override
    public double getStartYValue(int seriesIndex, int itemIndex) {
        return getY(seriesIndex, itemIndex).doubleValue();
    }

    @Override
    public Number getEndY(int seriesIndex, int itemIndex) {
        return getYValue(seriesIndex, itemIndex) + getWidthValue(seriesIndex, itemIndex);
    }

    @Override
    public double getEndYValue(int seriesIndex, int itemIndex) {
        return getEndY(seriesIndex, itemIndex).doubleValue();
    }

    protected double getWidthValue(int seriesIndex, int itemIndex) {
        Dictionary<String, ?> itemDictionary = getItemDictionary(seriesIndex, itemIndex);

        Object value = itemDictionary.get(WIDTH_KEY);
        if (value == null) {
            throw new NullPointerException(WIDTH_KEY + " is null.");
        }

        if (value instanceof String) {
            value = Double.parseDouble((String)value);
        }

        Number width = (Number)value;
        return width.doubleValue();
    }

    protected double getHeightValue(int seriesIndex, int itemIndex) {
        Dictionary<String, ?> itemDictionary = getItemDictionary(seriesIndex, itemIndex);

        Object value = itemDictionary.get(HEIGHT_KEY);
        if (value == null) {
            throw new NullPointerException(HEIGHT_KEY + " is null.");
        }

        if (value instanceof String) {
            value = Double.parseDouble((String)value);
        }

        Number height = (Number)value;
        return height.doubleValue();
    }
}
