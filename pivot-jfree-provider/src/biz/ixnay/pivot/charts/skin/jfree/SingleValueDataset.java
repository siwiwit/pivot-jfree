/*
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

import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.general.ValueDataset;


/**
 * Implementation of JFreeChart ValueDataset.
 *
 * @author rwhitcomb
 */
public class SingleValueDataset implements ValueDataset {
    private Object chartData;

    private DatasetGroup datasetGroup = null;

    public SingleValueDataset(Object chartData) {
        if (chartData == null) {
            throw new IllegalArgumentException("chartData is null.");
        }

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
    public Number getValue() {
        Number value;

        if (chartData == null) {
            return null;
        } else if (chartData instanceof String) {
            value = Double.parseDouble((String)chartData);
        } else if (chartData instanceof Number) {
            value = (Number)chartData;
        } else {
            value = Double.parseDouble(chartData.toString());
        }

        return value;
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
