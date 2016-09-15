package com.atid.app.mybarcode.adapter.MDI3x00;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.atid.lib.dev.barcode.opticon.param.OpticonParamName;
import com.atid.lib.dev.barcode.opticon.param.OpticonParamValueList;

import com.atid.app.mybarcode.R;
import com.atid.app.mybarcode.type.MDI3x00.ScanOpticonSymbolOption;


public class SymbolOptionListAdapter  extends BaseAdapter {

	private static final String TAG = "SymbolOptionListAdapter";

	private Context context;
	private LayoutInflater inflater;
	private ArrayList<SymbolOptionListItem> list;
	
	public SymbolOptionListAdapter(Context context) {
		super();

		this.context = context;
		this.inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list = new ArrayList<SymbolOptionListItem>();
	}
	
	public void updateList(OpticonParamValueList param) {
		
		this.list.clear();
		
		if ((Boolean)param.getValueAt(OpticonParamName.Aztec)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.Aztec));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.UPCA)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.UPCA));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.UPCE)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.UPCE));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.EAN13)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.EAN13));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.EAN8)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.EAN8));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.Code39)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.Code39));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.Codabar)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.Codabar));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.Industrial2of5)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.Industrial2of5));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.Interleaved2of5)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.Interleaved2of5));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.SCode)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.SCode));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.Code128)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.Code128));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.Code93)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.Code93));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.IATA)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.IATA));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.MSI_Plessey)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.MSI_Plessey));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.UK_Plessey)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.UK_Plessey));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.Telepen)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.Telepen));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.Code11)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.Code11));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.Matrix2of5)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.Matrix2of5));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.ChinesePost)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.ChinesePost));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.KoreanPostalAuthority)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.KoreanPostalAuthority));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.IntelligentMailBarCode)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.IntelligentMailBarCode));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.POSTNET)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.POSTNET));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.JapanesePost)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.JapanesePost));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.GS1_Databar)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.GS1_Databar));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.PDF417)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.PDF417));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.MicroPDF417)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.MicroPDF417));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.CodablockF)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.CodablockF));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.QRCode)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.QRCode));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.MicroQRCode)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.MicroQR));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.DataMatrix)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.DataMatrix));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.ChineseSensibleCode)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.ChineseSensibleCode));
		}
		
		if ((Boolean)param.getValueAt(OpticonParamName.MaxiCode)) {
			this.list.add(new SymbolOptionListItem(ScanOpticonSymbolOption.Maxicode));
		}
		
		
		this.notifyDataSetChanged();
		Log.d(TAG,  "--- updateList");
	}
	
	@Override
	public int getCount() {
		return this.list.size();
	}
	@Override
	public Object getItem(int position) {
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
		private ScanOpticonSymbolOption option;
		
		private View view;
		private TextView symbol;
		private Button button;
		
		public SymbolOptionListItem(ScanOpticonSymbolOption option) {
			this.option = option;
		}
		
		public ScanOpticonSymbolOption getOption() {
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
