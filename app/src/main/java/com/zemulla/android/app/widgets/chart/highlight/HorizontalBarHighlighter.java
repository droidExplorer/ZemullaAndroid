package com.zemulla.android.app.widgets.chart.highlight;

import com.zemulla.android.app.widgets.chart.data.BarData;
import com.zemulla.android.app.widgets.chart.data.DataSet;
import com.zemulla.android.app.widgets.chart.data.Entry;
import com.zemulla.android.app.widgets.chart.interfaces.dataprovider.BarDataProvider;
import com.zemulla.android.app.widgets.chart.interfaces.datasets.IBarDataSet;
import com.zemulla.android.app.widgets.chart.interfaces.datasets.IDataSet;
import com.zemulla.android.app.widgets.chart.utils.MPPointD;

/**
 * Created by Philipp Jahoda on 22/07/15.
 */
public class HorizontalBarHighlighter extends BarHighlighter {

	public HorizontalBarHighlighter(BarDataProvider chart) {
		super(chart);
	}

	@Override
	public Highlight getHighlight(float x, float y) {

		BarData barData = mChart.getBarData();

		MPPointD pos = getValsForTouch(y, x);

		Highlight high = getHighlightForX((float) pos.y, y, x);
		if (high == null)
			return null;

		IBarDataSet set = barData.getDataSetByIndex(high.getDataSetIndex());
		if (set.isStacked()) {

			return getStackedHighlight(high,
					set,
					(float) pos.y,
					(float) pos.x);
		}

		MPPointD.recycleInstance(pos);

		return high;
	}

	@Override
	protected Highlight buildHighlight(IDataSet set, int dataSetIndex, float xVal, DataSet.Rounding rounding) {

		final Entry e = set.getEntryForXValue(xVal, rounding);

		MPPointD pixels = mChart.getTransformer(set.getAxisDependency()).getPixelForValues(e.getY(), e.getX());

		return new Highlight(e.getX(), e.getY(), (float) pixels.x, (float) pixels.y, dataSetIndex, set.getAxisDependency());
	}

	@Override
	protected float getDistance(float x1, float y1, float x2, float y2) {
		return Math.abs(y1 - y2);
	}
}
