package com.atid.app.mybarcode.type.IT5x80;

import com.atid.lib.dev.barcode.honeywell.param.HoneywellParamName;
import com.atid.lib.dev.barcode.honeywell.param.HoneywellParamValue;
import com.atid.lib.dev.barcode.honeywell.param.HoneywellParamValueList;
import com.atid.lib.dev.barcode.params.ATScanIT5x80Parameter;
import com.atid.lib.dev.barcode.type.RangeIntValue;
import com.atid.app.mybarcode.R;
import com.atid.app.mybarcode.adapter.ValueAdapter;
import com.atid.app.mybarcode.adapter.ValueItem;

import android.app.Activity;
import android.widget.Spinner;

public class SymbolLength {

	private Spinner spnMin;
	private Spinner spnMax;

	private ValueAdapter<Integer> adpMin;
	private ValueAdapter<Integer> adpMax;

	private HoneywellParamName min;
	private HoneywellParamName max;

	public SymbolLength(Activity activity, HoneywellParamName min, HoneywellParamName max) {
		this.spnMin = (Spinner) activity.findViewById(R.id.min);
		this.adpMin = new ValueAdapter<Integer>(activity);
		this.spnMin.setAdapter(this.adpMin);
		this.spnMax = (Spinner) activity.findViewById(R.id.max);
		this.adpMax = new ValueAdapter<Integer>(activity);
		this.spnMax.setAdapter(this.adpMax);

		this.min = min;
		this.max = max;
	}

	public SymbolLength(Activity activity, HoneywellParamName min, HoneywellParamName max,
			ATScanIT5x80Parameter param) {
		this.spnMin = (Spinner) activity.findViewById(R.id.min);
		this.adpMin = new ValueAdapter<Integer>(activity);
		this.spnMin.setAdapter(this.adpMin);
		this.spnMax = (Spinner) activity.findViewById(R.id.max);
		this.adpMax = new ValueAdapter<Integer>(activity);
		this.spnMax.setAdapter(this.adpMax);

		this.min = min;
		this.max = max;

		this.fill(param);
	}

	public void load(ATScanIT5x80Parameter param) {
		this.fill(param);

		HoneywellParamValueList paramList = param.getParams(new HoneywellParamName[] {
				this.min, this.max });

		setValue(paramList.getValueAt(this.min), paramList.getValueAt(this.max));
	}

	public boolean save(ATScanIT5x80Parameter param) {
		HoneywellParamValueList paramList = new HoneywellParamValueList();
		paramList.add(getMin());
		paramList.add(getMax());
		if (!param.setParams(paramList)) {
			return false;
		}
		return true;
	}

	public void fill(ATScanIT5x80Parameter param) {
		fillValueAdapter(this.adpMin, param.getParamRange(this.min));
		fillValueAdapter(this.adpMax, param.getParamRange(this.max));
	}

	public void setValue(Object min, Object max) {
		selectSpinner(this.spnMin, this.adpMin, (Integer) min);
		selectSpinner(this.spnMax, this.adpMax, (Integer) max);
	}

	public HoneywellParamValue getMin() {
		return new HoneywellParamValue(this.min, getValue(this.spnMin, this.adpMin));
	}

	public HoneywellParamValue getMax() {
		return new HoneywellParamValue(this.max, getValue(this.spnMax, this.adpMax));
	}

	private void fillValueAdapter(ValueAdapter<Integer> adapter,
			RangeIntValue range) {
		ValueItem<Integer> item;
		for (int i = range.getMin(); i <= range.getMax(); i++) {
			item = new ValueItem<Integer>("" + i, i);
			adapter.add(item);
		}
		adapter.notifyDataSetChanged();
	}

	private void selectSpinner(Spinner spinner, ValueAdapter<Integer> adapter,
			int value) {
		int position = adapter.getPosition(value);
		spinner.setSelection(position);
	}

	private int getValue(Spinner spinner, ValueAdapter<Integer> adapter) {
		int position = spinner.getSelectedItemPosition();
		return adapter.getItem(position);
	}
}
