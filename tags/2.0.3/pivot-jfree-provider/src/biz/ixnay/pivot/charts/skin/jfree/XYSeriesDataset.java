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

import org.jfree.data.DomainOrder;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.xy.XYDataset;

import org.apache.pivot.beans.BeanAdapter;
import org.apache.pivot.collections.Dictionary;
import org.apache.pivot.collections.List;

/**
 * Implementation of JFreeChart XYDataset.
 *
 * @author gbrown
 */
@SuppressWarnings("unchecked")
public class XYSeriesDataset implements XYDataset {
    private String seriesNameKey;
    private List<?> chartData;

    private DatasetGroup datasetGroup = null;

    public static final String X_KEY = "x";
    public static final String Y_KEY = "y";

    public XYSeriesDataset(String seriesNameKey, List<?> chartData) {
        if (seriesNameKey == null) {
            throw new IllegalArgumentException("seriesNameKey is null.");
        }

        if (chartData == null) {
            throw new IllegalArgumentException("chartData is null.");
        }

        this.seriesNameKey = seriesNameKey;
        this.chartData = chartData;
    }

    @Override
    public DatasetGroup getGroup() {
        return datasetGroup;
    }

    @Override
    public void setGroup(DatasetGroup datasetGroup) {
        this.datasetGroup = datasetGroup;
    }

    @Override
    public int getSeriesCount() {
        return chartData.getLength();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Comparable getSeriesKey(int seriesIndex) {
        Dictionary<String, ?> seriesDictionary = getSeriesDictionary(seriesIndex);
        return (String)seriesDictionary.get(seriesNameKey);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public int indexOf(Comparable seriesName) {
        if (seriesName == null) {
            throw new IllegalArgumentException("seriesName is null.");
        }

        int index = -1;
        for (int i = 0, n = chartData.getLength(); i < n && index == -1; i++) {
            Dictionary<String, ?> seriesDictionary = getSeriesDictionary(i);

            if (seriesName.compareTo(seriesDictionary.get(seriesNameKey)) == 0) {
                index = i;
            }
        }

        return index;
    }

    @Override
    public DomainOrder getDomainOrder() {
        return DomainOrder.NONE;
    }

    @Override
    public int getItemCount(int seriesIndex) {
        List<?> series = getSeries(seriesIndex);
        return series.getLength();
    }

    @Override
    public Number getX(int seriesIndex, int itemIndex) {
        Dictionary<String, ?> itemDictionary = getItemDictionary(seriesIndex, itemIndex);

        Object value = itemDictionary.get(X_KEY);
        if (value == null) {
            throw new NullPointerException(X_KEY + " is null.");
        }

        if (value instanceof String) {
            value = Double.parseDouble((String)value);
        }

        return (Number)value;
    }

    @Override
    public double getXValue(int seriesIndex, int itemIndex) {
        return getX(seriesIndex, itemIndex).doubleValue();
    }

    @Override
    public Number getY(int seriesIndex, int itemIndex) {
        Dictionary<String, ?> itemDictionary = getItemDictionary(seriesIndex, itemIndex);

        Object value = itemDictionary.get(Y_KEY);
        if (value == null) {
            throw new NullPointerException(Y_KEY + " is null.");
        }

        if (value instanceof String) {
            value = Double.parseDouble((String)value);
        }

        return (Number)value;
    }

    @Override
    public double getYValue(int seriesIndex, int itemIndex) {
        return getY(seriesIndex, itemIndex).doubleValue();
    }

    private List<?> getSeries(int seriesIndex) {
        if (seriesIndex < 0
            || seriesIndex > chartData.getLength() - 1) {
            throw new IndexOutOfBoundsException();
        }

        List<?> series = (List<?>)chartData.get(seriesIndex);
        return series;
    }

    protected Dictionary<String, ?> getSeriesDictionary(int seriesIndex) {
        List<?> series = getSeries(seriesIndex);

        Dictionary<String, ?> seriesDictionary;
        if (series instanceof Dictionary<?, ?>) {
            seriesDictionary = (Dictionary<String, ?>)series;
        } else {
            seriesDictionary = new BeanAdapter(series);
        }

        return seriesDictionary;
    }

    protected Dictionary<String, ?> getItemDictionary(int seriesIndex, int itemIndex) {
        List<?> series = getSeries(seriesIndex);

        if (itemIndex < 0
            || itemIndex > series.getLength() - 1) {
            throw new IndexOutOfBoundsException();
        }

        Object item = series.get(itemIndex);

        Dictionary<String, ?> itemDictionary;
        if (item instanceof Dictionary<?, ?>) {
            itemDictionary = (Dictionary<String, ?>)item;
        } else {
            itemDictionary = new BeanAdapter(item);
        }

        return itemDictionary;
    }

    @Override
    public void addChangeListener(DatasetChangeListener listener) {
        // No-op
    }

    @Override
    public void removeChangeListener(DatasetChangeListener listener) {
        // No-op
    }
}
