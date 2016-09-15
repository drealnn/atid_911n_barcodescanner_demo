package com.atid.app.mybarcode.adapter.SE955;

import java.util.ArrayList;

import com.atid.lib.dev.barcode.motorola.param.SSIParamName;
import com.atid.lib.dev.barcode.motorola.param.SSIParamValueList;
import com.atid.app.mybarcode.R;
import com.atid.app.mybarcode.type.SE955.Scan1dSymbolOption;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class SymbolOptionListAdapter extends BaseAdapter {

	@SuppressWarnings("unused")
	private static final String TAG = "SymbolOptionListAdapter";

	private Context context;
	private LayoutInflater inflater;
	private ArrayList<SymbolOptionListItem> list;

	// ------------------------------------------------------------------------
	// Constructor SymbolOptionListAdapter
	// ------------------------------------------------------------------------

	public SymbolOptionListAdapter(Context context) {
		super();

		this.context = context;
		this.inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list = new ArrayList<SymbolOptionListItem>();
	}

	public void updateList(SSIParamValueList param) {
		this.list.clear();

		// is UPC/EAN Option
		if ((Boolean)param.getValueAt(SSIParamName.UPC_A)
				|| (Boolean)param.getValueAt(SSIParamName.UPC_E)
				|| (Boolean)param.getValueAt(SSIParamName.UPC_E1)
				|| (Boolean)param.getValueAt(SSIParamName.EAN_8)
				|| (Boolean)param.getValueAt(SSIParamName.EAN_13)
				|| (Boolean)param.getValueAt(SSIParamName.Bookland_EAN)) {
			this.list.add(new SymbolOptionListItem(Scan1dSymbolOption.UpcEan));
		}
		// is Code 39 Option
		if ((Boolean)param.getValueAt(SSIParamName.Code39)) {
			this.list.add(new SymbolOptionListItem(Scan1dSymbolOption.Code39));
		}
		// is Code 93 Option
		if ((Boolean)param.getValueAt(SSIParamName.Code93)) {
			this.list.add(new SymbolOptionListItem(Scan1dSymbolOption.Code93));
		}
		// is Code 11 Option
		if ((Boolean)param.getValueAt(SSIParamName.Code11)) {
			this.list.add(new SymbolOptionListItem(Scan1dSymbolOption.Code11));
		}
		// is Interleaved 2 of 5 Option
		if ((Boolean)param.getValueAt(SSIParamName.I2of5)) {
			this.list.add(new SymbolOptionListItem(Scan1dSymbolOption.I2of5));
		}
		// is Discrete 2 of 5 Option
		if ((Boolean)param.getValueAt(SSIParamName.D2of5)) {
			this.list.add(new SymbolOptionListItem(Scan1dSymbolOption.D2of5));
		}
		// is Codabar Option
		if ((Boolean)param.getValueAt(SSIParamName.Codabar)) {
			this.list.add(new SymbolOptionListItem(Scan1dSymbolOption.Codabar));
		}
		// is MSI Option
		if ((Boolean)param.getValueAt(SSIParamName.MSI)) {
			this.list.add(new SymbolOptionListItem(Scan1dSymbolOption.Msi));
		}
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return this.list.size();
	}

	@Override
	public Scan1dSymbolOption getItem(int position) {
		return this.list.get(position).getOption();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SymbolOptionListItem item = this.list.get(position);
		
		if (null == convertView) {
			convertView = inflater.inflate(R.layout.item_symbol_option_list,
					parent, false);
			item.setView(convertView);
		}
		if (null == item.getView()) {
			item.setView(convertView);
		}
		item.displayItem();

		return convertView;
	}

	// ------------------------------------------------------------------------
	// Internal class SymbolOptionListItem
	// ------------------------------------------------------------------------

	private class SymbolOptionListItem {
		private Scan1dSymbolOption option;
		
		private View view;
		private TextView symbol;
		private Button button;
		
		public SymbolOptionListItem(Scan1dSymbolOption option) {
			this.option = option;
		}
		
		public Scan1dSymbolOption getOption() {
			return this.option;
		}
		
		public View getView() {
			return this.view;
		}
		
		public void setView(View view) {
			this.view = view;
			this.symbol = (TextView) this.view.findViewById(R.id.symbol);
			this.button = (Button) this.view.findViewById(R.id.detail_option);
			this.button.setOnClickListener((Button.OnClickListener)context);
		}
		
		public void displayItem() {
			this.symbol.setText(option.getName());
			this.button.setTag(this.option);
		}
	}
}
