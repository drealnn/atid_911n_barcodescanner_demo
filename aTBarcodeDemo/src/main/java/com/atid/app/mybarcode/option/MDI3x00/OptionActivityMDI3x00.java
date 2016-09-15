package com.atid.app.mybarcode.option.MDI3x00;

import com.atid.lib.dev.ATScanManager;
import com.atid.lib.dev.ATScanner;
import com.atid.lib.dev.barcode.opticon.param.OpticonParamName;
import com.atid.lib.dev.barcode.opticon.param.OpticonParamValue;
import com.atid.lib.dev.barcode.opticon.param.OpticonParamValueList;
import com.atid.lib.dev.barcode.params.ATScanMDI3x00Parameter;
import com.atid.app.mybarcode.R;
import com.atid.app.mybarcode.adapter.MDI3x00.SymbolOptionListAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


public class OptionActivityMDI3x00 extends Activity implements Button.OnClickListener {

	private static final String TAG = "OptionActivityMDI3x00";
	public static final int VIEW_ENABLE_STATE_SYMBOL = 1;
	public static final int VIEW_GENERAL = 10;
	
	private SymbolOptionListAdapter adpOptions;
	private ATScanner mScanner;
	private ATScanMDI3x00Parameter mParam;
	
	private ListView lstOptions;
	private Button btnEnableStatus;
	private Button btnDefaultAllSymbol;
	private Button btnDisableAllSymbol;
	private Button btnEnableAllSymbol;
	private Button btnGeneral;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option_main);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// Initialize Scanner Instance
		this.mScanner = ATScanManager.getInstance();
		this.mParam = (ATScanMDI3x00Parameter) this.mScanner.getParameter();
		
		// Initialize Widgets
		this.lstOptions = (ListView) findViewById(R.id.symbol_list);
		this.adpOptions = new SymbolOptionListAdapter(this);
		this.lstOptions.setAdapter(this.adpOptions);
		
		this.btnEnableStatus = (Button) findViewById(R.id.set_enable_status);
		this.btnEnableStatus.setOnClickListener(this);
		
		this.btnDefaultAllSymbol = (Button) findViewById(R.id.default_all_symbologies);
		this.btnDefaultAllSymbol.setOnClickListener(this);
		
		this.btnDisableAllSymbol = (Button) findViewById(R.id.disable_all_symbologies);
		this.btnDisableAllSymbol.setOnClickListener(this);
		
		this.btnEnableAllSymbol = (Button) findViewById(R.id.enable_all_symbologies);
		this.btnEnableAllSymbol.setOnClickListener(this);
		
		this.btnGeneral = (Button) findViewById(R.id.gerneral_config);
		this.btnGeneral.setOnClickListener(this);
		
		// Initialize Symbol Option List
		initSymbolOptionList();
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		// Disable All Widget
		enableAllWidget(true);

		if (requestCode == VIEW_ENABLE_STATE_SYMBOL && resultCode == RESULT_OK) {
			// Initialize Symbol Option List
			initSymbolOptionList();
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

	@Override
	public void onClick(View v) {

		Intent intent;
		OpticonParamValueList paramList;
		
		switch (v.getId()) {
		case R.id.detail_option:
			Log.d(TAG, "detail_option");
			break;
		case R.id.set_enable_status:
			Log.d(TAG, "set_enable_status");
			intent = new Intent(this, OptionEnableStateActivity.class);
			startActivityForResult(intent, VIEW_ENABLE_STATE_SYMBOL);
			break;
		case R.id.default_all_symbologies:
			// �� Ŀ�ǵ带 ������, prefix, suffix ������ �ٽ��ؾߵǼ� �ϴ� ����.
			
			paramList = new OpticonParamValueList(new OpticonParamValue[] {
					new OpticonParamValue(OpticonParamName.BackToCustomDefaults),
					//new OpticonParamValue(OpticonParamName.SaveSettingsInStartUpSettingArea)
					});
			
			this.mParam.setParams(paramList);
			
			// 2015.07.09 ������ config ����� �Ϸ���� ���� �����̹Ƿ�, �׳� �߱��� cahrSet���� �ǵ���.
			this.mScanner.setCharSetName("GB18030");
			
			Log.d(TAG, "default_all_symbologies");
			break;
		case R.id.disable_all_symbologies:
			paramList = new OpticonParamValueList(new OpticonParamValue[] {
					new OpticonParamValue(OpticonParamName.AllCodes_Disable),
					new OpticonParamValue(OpticonParamName.SaveSettingsInStartUpSettingArea)
					});
			
			this.mParam.setParams(paramList);
			Log.d(TAG, "disable_all_symbologies");
			break;
		case R.id.enable_all_symbologies:
			paramList = new OpticonParamValueList(new OpticonParamValue[] {
					new OpticonParamValue(OpticonParamName.AllCodes_Multiple),
					new OpticonParamValue(OpticonParamName.SaveSettingsInStartUpSettingArea)
					});
			
			this.mParam.setParams(paramList);
			Log.d(TAG, "enable_all_symbologies");
			break;
		case R.id.gerneral_config:
			Log.d(TAG, "gerneral_config");
			intent = new Intent(this, OptionGeneralConfigActivity.class);
			startActivityForResult(intent, VIEW_GENERAL);
			break;
		}
	}
	
	// Enable/Disable All Widget
	private void enableAllWidget(boolean enabled) {
		this.lstOptions.setEnabled(enabled);
		this.btnEnableStatus.setEnabled(enabled);
		this.btnDefaultAllSymbol.setEnabled(enabled);
		this.btnDisableAllSymbol.setEnabled(enabled);
		this.btnEnableAllSymbol.setEnabled(enabled);
		this.btnGeneral.setEnabled(enabled);
	}

	private void initSymbolOptionList() {
		
		OpticonParamValueList paramList = this.mParam.getParams(new OpticonParamName[] {
				OpticonParamName.UPCA, OpticonParamName.UPCE, OpticonParamName.EAN13, OpticonParamName.EAN8,
				OpticonParamName.Code39, OpticonParamName.Codabar, OpticonParamName.Industrial2of5,
				OpticonParamName.Interleaved2of5, OpticonParamName.SCode, OpticonParamName.Code128,
				OpticonParamName.Code93, OpticonParamName.IATA, OpticonParamName.MSI_Plessey, OpticonParamName.UK_Plessey,
				OpticonParamName.Telepen, OpticonParamName.Code11, OpticonParamName.Matrix2of5, OpticonParamName.ChinesePost,
				OpticonParamName.KoreanPostalAuthority, OpticonParamName.IntelligentMailBarCode, OpticonParamName.POSTNET,
				OpticonParamName.JapanesePost, OpticonParamName.GS1_Databar, OpticonParamName.PDF417,
				OpticonParamName.MicroPDF417, OpticonParamName.CodablockF, OpticonParamName.QRCode,
				OpticonParamName.MicroQRCode, OpticonParamName.DataMatrix, OpticonParamName.Aztec,
				OpticonParamName.ChineseSensibleCode, OpticonParamName.MaxiCode
		});
		
		this.adpOptions.updateList(paramList);
	}
}
