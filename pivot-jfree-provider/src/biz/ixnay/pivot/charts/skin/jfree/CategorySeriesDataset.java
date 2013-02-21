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

import org.apache.pivot.beans.BeanAdapter;
import org.apache.pivot.charts.ChartView;
import org.apache.pivot.collections.Dictionary;
import org.apache.pivot.collections.List;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;

/**
 * Implementation of JFreeChart CategoryDataset.
 *
 * @author gbrown
 */
@SuppressWarnings("unchecked")
public class CategorySeriesDataset implements CategoryDataset {
    private ChartView.CategorySequence categories;
    private String seriesNameKey;
    private List<?> chartData;

    private DatasetGroup datasetGroup = null;

    public CategorySeriesDataset(ChartView.CategorySequence categories,
        String seriesNameKey, List<?> chartData) {
        if (categories == null) {
            throw new IllegalArgumentException("categories is null.");
        }

        if (seriesNameKey == null) {
            throw new IllegalArgumentException("seriesNameKey is null.");
        }

        if (chartData == null) {
            throw new IllegalArgumentException("chartData is null.");
        }

        this.categories = categories;
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
	public int getColumnCount() {
        return categories.getLength();
    }

    @Override
	public int getRowCount() {
        return chartData.getLength();
    }

    @SuppressWarnings("rawtypes")
	@Override
    public int getColumnIndex(Comparable categoryLabel) {
        if (categoryLabel == null) {
            throw new IllegalArgumentException("categoryLabel is null.");
        }

        int columnIndex = -1;
        for (int i = 0, n = categories.getLength(); i < n && columnIndex == -1; i++) {
            ChartView.Category category = categories.get(i);

            if (categoryLabel.compareTo(category.getLabel()) == 0) {
                columnIndex = i;
            }
        }

        return columnIndex;
    }

    @SuppressWarnings("rawtypes")
	@Override
	public Comparable getColumnKey(int categoryIndex) {
        if (categoryIndex < 0
            || categoryIndex > categories.getLength() - 1) {
            throw new IndexOutOfBoundsException();
        }

        return categories.get(categoryIndex).getLabel();
    }

    @Override
	public java.util.List<String> getColumnKeys() {
        java.util.ArrayList<String> columnKeys = new java.util.ArrayList<String>(categories.getLength());
        for (int i = 0, n = categories.getLength(); i < n; i++) {
            columnKeys.add(categories.get(i).getLabel());
        }

        return columnKeys;
    }

    @SuppressWarnings("rawtypes")
	@Override
	public int getRowIndex(Comparable seriesName) {
        if (seriesName == null) {
            throw new IllegalArgumentException("seriesName is null.");
        }

        int rowIndex = -1;
        for (int i = 0, n = chartData.getLength(); i < n && rowIndex == -1; i++) {
            Dictionary<String, ?> seriesDictionary = getSeriesDictionary(i);

            if (seriesName.compareTo(seriesDictionary.get(seriesNameKey)) == 0) {
                rowIndex = i;
            }
        }

        return rowIndex;
    }

    @SuppressWarnings("rawtypes")
	@Override
	public Comparable getRowKey(int seriesIndex) {
        Dictionary<String, ?> seriesDictionary = getSeriesDictionary(seriesIndex);
        return (String)seriesDictionary.get(seriesNameKey);
    }

    @SuppressWarnings("rawtypes")
	@Override
	public java.util.List<Comparable> getRowKeys() {
        java.util.ArrayList<Comparable> rowKeys = new java.util.ArrayList<Comparable>(chartData.getLength());
        for (int i = 0, n = chartData.getLength(); i < n; i++) {
            rowKeys.add(getRowKey(i));
        }

        return rowKeys;
    }

    @Override
	public Number getValue(int seriesIndex, int categoryIndex) {
        Dictionary<String, ?> seriesDictionary = getSeriesDictionary(seriesIndex);

        if (categoryIndex < 0
            || categoryIndex > categories.getLength() - 1) {
            throw new IndexOutOfBoundsException();
        }

        ChartView.Category category = categories.get(categoryIndex);
        String categoryKey = category.getKey();

        Object value = seriesDictionary.get(categoryKey);
        if (value instanceof String) {
            value = Double.parseDouble((String)value);
        }

        return (Number)value;
    }

    @SuppressWarnings("rawtypes")
	@Override
	public Number getValue(Comparable seriesName, Comparable categoryLabel) {
        return getValue(getRowIndex(seriesName), getColumnIndex(categoryLabel));
    }

    protected Dictionary<String, ?> getSeriesDictionary(int seriesIndex) {
        if (seriesIndex < 0
            || seriesIndex > chartData.getLength() - 1) {
            throw new IndexOutOfBoundsException();
        }

        Object series = chartData.get(seriesIndex);

        Dictionary<String, ?> seriesDictionary;
        if (series instanceof Dictionary<?, ?>) {
            seriesDictionary = (Dictionary<String, ?>)series;
        } else {
            seriesDictionary = new BeanAdapter(series);
        }

        return seriesDictionary;
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
