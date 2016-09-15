package com.atid.app.mybarcode.option.SE4710;

import com.atid.lib.dev.ATScanManager;
import com.atid.lib.dev.ATScanner;
import com.atid.lib.dev.barcode.motorola.param.SSIParamName;
import com.atid.lib.dev.barcode.motorola.param.SSIParamValueList;
import com.atid.lib.dev.barcode.params.ATScanSE4710Parameter;
import com.atid.app.mybarcode.R;
import com.atid.app.mybarcode.widget.SymbolLength;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class OptionSymbolCodabarActivity extends Activity implements
		OnClickListener {

	@SuppressWarnings("unused")
	private static final String TAG = "OptionSymbolCodabarActivity";

	private ATScanner mScanner;
	private ATScanSE4710Parameter mParam;

	private CheckBox chkClsiEditing;
	private CheckBox chkNotisEditing;
	private SymbolLength wgtLength;
	private Button btnSetOption;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option_1d_symbol_codabar);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		this.setTitle(R.string.symbol_codabar_name);

		// Initialize Scanner Instance
		this.mScanner = ATScanManager.getInstance();
		this.mParam = (ATScanSE4710Parameter) this.mScanner.getParameter();

		// Initialize Widgets
		this.chkClsiEditing = (CheckBox) findViewById(R.id.clsi_editing);
		this.chkNotisEditing = (CheckBox) findViewById(R.id.notis_editing);
		
		this.wgtLength = (SymbolLength) findViewById(R.id.length);
		
		this.btnSetOption = (Button) findViewById(R.id.set_option);
		this.btnSetOption.setOnClickListener(this);

		// Load Scanner Symbol Detail Option
		loadOption();
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		if(mScanner != null)
			ATScanManager.wakeUp();
	}

	@Override
	protected void onStop() {
		if(mScanner != null)
			ATScanManager.sleep();
		super.onStop();
	}

	@SuppressLint("ShowToast")
	@Override
	public void onClick(View v) {
		if (R.id.set_option == v.getId()) {
			SSIParamValueList paramList = new SSIParamValueList();

			paramList.add(SSIParamName.CLSI_Editing,
					this.chkClsiEditing.isChecked());
			paramList.add(SSIParamName.NOTIS_Editing,
					this.chkNotisEditing.isChecked());
			paramList.add(SSIParamName.Codabar_Length_Min,
					this.wgtLength.getLength1());
			paramList.add(SSIParamName.Codabar_Length_Max,
					this.wgtLength.getLength2());

			if (this.mParam.setParams(paramList)) {
				this.setResult(RESULT_OK);
				this.finish();
			} else {
				Toast.makeText(this, R.string.faile_to_set_symbologies,
						Toast.LENGTH_LONG);
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// Load Scanner Symbol Detail Option
	private void loadOption() {
		SSIParamValueList paramList = this.mParam.getParams(new SSIParamName[] {
				SSIParamName.Codabar_Length_Min, SSIParamName.Codabar_Length_Max,
				SSIParamName.CLSI_Editing, SSIParamName.NOTIS_Editing });
		this.chkClsiEditing.setChecked((Boolean) paramList
				.getValueAt(SSIParamName.CLSI_Editing));
		this.chkNotisEditing.setChecked((Boolean) paramList
				.getValueAt(SSIParamName.NOTIS_Editing));
		this.wgtLength.setLength(
				(Integer) paramList.getValueAt(SSIParamName.Codabar_Length_Min),
				(Integer) paramList.getValueAt(SSIParamName.Codabar_Length_Max));
	}
}
