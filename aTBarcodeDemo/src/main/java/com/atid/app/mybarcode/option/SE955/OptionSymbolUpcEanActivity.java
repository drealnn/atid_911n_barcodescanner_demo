package com.atid.app.mybarcode.option.SE955;

import com.atid.lib.dev.ATScanManager;
import com.atid.lib.dev.ATScanner;
import com.atid.lib.dev.barcode.motorola.param.SSIParamName;
import com.atid.lib.dev.barcode.motorola.param.SSIParamValueList;
import com.atid.lib.dev.barcode.params.ATScanSE955Parameter;
import com.atid.lib.dev.barcode.motorola.type.DecodeUpcEanSupplementals;
import com.atid.lib.dev.barcode.motorola.type.Preamble;
import com.atid.lib.dev.barcode.motorola.type.UpcEanSecurityLevel;
import com.atid.app.mybarcode.R;
import com.atid.app.mybarcode.adapter.StringAdapter;
import com.atid.app.mybarcode.adapter.ValueAdapter;
import com.atid.app.mybarcode.adapter.ValueItem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

public class OptionSymbolUpcEanActivity extends Activity implements
		OnClickListener {

	@SuppressWarnings("unused")
	private static final String TAG = "OptionSymbolUpcEanActivity";

	private ATScanner scanner = null;
	private ATScanSE955Parameter param;

	private CheckBox chkTransmitUpcA;
	private CheckBox chkTransmitUpcE;
	private CheckBox chkTransmitUpcE1;
	private CheckBox chkConvertUpcE;
	private CheckBox chkConvertUpcE1;
	private CheckBox chkEanZeroExtend;
	private CheckBox chkConvertEan8toEan13;
	private CheckBox chkUccCoupon;
	private Spinner spnDecode;
	private Spinner spnDecodeRedundancy;
	private Spinner spnLevel;
	private Spinner spnUpcA;
	private Spinner spnUpcE;
	private Spinner spnUpcE1;
	private Button btnSetOption;

	private ValueAdapter<DecodeUpcEanSupplementals> adpDecode;
	private StringAdapter adpDecodeRedundancy;
	private ValueAdapter<UpcEanSecurityLevel> adpLevel;
	private ValueAdapter<Preamble> adpUpcA;
	private ValueAdapter<Preamble> adpUpcE;
	private ValueAdapter<Preamble> adpUpcE1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option_1d_symbol_upc_ean);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// Initialize Scanner Instance
		this.scanner = ATScanManager.getInstance();
		this.param = (ATScanSE955Parameter) this.scanner.getParameter();

		// Initialize Widgets
		this.chkTransmitUpcA = (CheckBox) findViewById(R.id.transmit_upc_a_check_digit);
		this.chkTransmitUpcE = (CheckBox) findViewById(R.id.transmit_upc_e_check_digit);
		this.chkTransmitUpcE1 = (CheckBox) findViewById(R.id.transmit_upc_e1_check_digit);
		this.chkConvertUpcE = (CheckBox) findViewById(R.id.convert_upc_e_to_upc_a);
		this.chkConvertUpcE1 = (CheckBox) findViewById(R.id.convert_upc_e1_to_upc_a);
		this.chkEanZeroExtend = (CheckBox) findViewById(R.id.ean_zero_extend);
		this.chkConvertEan8toEan13 = (CheckBox) findViewById(R.id.convert_ean_8_to_ean_13_type);
		this.chkUccCoupon = (CheckBox) findViewById(R.id.ucc_coupon_extended_code);
		this.spnDecode = (Spinner) findViewById(R.id.decode_upc_ean_supplement);
		this.adpDecode = new ValueAdapter<DecodeUpcEanSupplementals>(this);
		for (DecodeUpcEanSupplementals item : DecodeUpcEanSupplementals
				.values()) {
			this.adpDecode.add(new ValueItem<DecodeUpcEanSupplementals>(item
					.toString(), item));
		}
		this.spnDecode.setAdapter(this.adpDecode);
		this.spnDecodeRedundancy = (Spinner) findViewById(R.id.decode_upc_ean_supplement_redundancy);
		this.adpDecodeRedundancy = new StringAdapter(this,
				R.array.decode_upc_ean_supplemental_redundancy);
		this.spnDecodeRedundancy.setAdapter(this.adpDecodeRedundancy);
		this.spnLevel = (Spinner) findViewById(R.id.upc_ean_security_level);
		this.adpLevel = new ValueAdapter<UpcEanSecurityLevel>(this);
		for (UpcEanSecurityLevel item : UpcEanSecurityLevel.values()) {
			this.adpLevel.add(new ValueItem<UpcEanSecurityLevel>(item
					.toString(), item));
		}
		this.spnLevel.setAdapter(this.adpLevel);
		this.spnUpcA = (Spinner) findViewById(R.id.upc_a_preamble);
		this.adpUpcA = new ValueAdapter<Preamble>(this);
		for (Preamble item : Preamble.values()) {
			this.adpUpcA.add(new ValueItem<Preamble>(item.toString(), item));
		}
		this.spnUpcA.setAdapter(this.adpUpcA);
		this.spnUpcE = (Spinner) findViewById(R.id.upc_e_preamble);
		this.adpUpcE = new ValueAdapter<Preamble>(this);
		for (Preamble item : Preamble.values()) {
			this.adpUpcE.add(new ValueItem<Preamble>(item.toString(), item));
		}
		this.spnUpcE.setAdapter(this.adpUpcE);
		this.spnUpcE1 = (Spinner) findViewById(R.id.upc_e1_preamble);
		this.adpUpcE1 = new ValueAdapter<Preamble>(this);
		for (Preamble item : Preamble.values()) {
			this.adpUpcE1.add(new ValueItem<Preamble>(item.toString(), item));
		}
		this.spnUpcE1.setAdapter(this.adpUpcE1);
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
		if(scanner != null)
			ATScanManager.wakeUp();
	}

	@Override
	protected void onStop() {
		if(scanner != null)
			ATScanManager.sleep();
		super.onStop();
	}

	@SuppressLint("ShowToast")
	@Override
	public void onClick(View v) {
		if (R.id.set_option == v.getId()) {
			SSIParamValueList paramList = new SSIParamValueList();

			paramList.add(SSIParamName.Transmit_UPC_A_CheckDigit,
					this.chkTransmitUpcA.isChecked());
			paramList.add(SSIParamName.Transmit_UPC_E_CheckDigit,
					this.chkTransmitUpcE.isChecked());
			paramList.add(SSIParamName.Transmit_UPC_E1_CheckDigit,
					this.chkTransmitUpcE1.isChecked());
			paramList.add(SSIParamName.Convert_UPC_E_To_A,
					this.chkConvertUpcE.isChecked());
			paramList.add(SSIParamName.Convert_UPC_E1_To_A,
					this.chkConvertUpcE1.isChecked());
			paramList.add(SSIParamName.EAN_8_ZeroExtend,
					this.chkEanZeroExtend.isChecked());
			paramList.add(SSIParamName.Convert_EAN_8_To_EAN_13_Type,
					this.chkConvertEan8toEan13.isChecked());
			paramList.add(SSIParamName.UCC_CouponExtendedCode,
					this.chkUccCoupon.isChecked());
			paramList.add(SSIParamName.Decode_UPC_EAN_Supplementals,
					DecodeUpcEanSupplementals.valueOf((byte) this.spnDecode
							.getSelectedItemPosition()));
			paramList.add(SSIParamName.Decode_UPC_EAN_SupplementalRedundancy,
					this.spnDecodeRedundancy.getSelectedItemPosition());
			paramList.add(SSIParamName.SecurityLevel,
					UpcEanSecurityLevel.valueOf((byte) this.spnLevel
							.getSelectedItemPosition()));
			paramList.add(SSIParamName.UPC_A_Preamble, Preamble
					.valueOf((byte) this.spnUpcA.getSelectedItemPosition()));
			paramList.add(SSIParamName.UPC_E_Preamble, Preamble
					.valueOf((byte) this.spnUpcE.getSelectedItemPosition()));
			paramList.add(SSIParamName.UPC_E1_Preamble, Preamble
					.valueOf((byte) this.spnUpcE1.getSelectedItemPosition()));

			if (this.param.setParams(paramList)) {
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
		SSIParamValueList paramList = this.param.getParams(new SSIParamName[] {
				SSIParamName.Decode_UPC_EAN_Supplementals,
				SSIParamName.Decode_UPC_EAN_SupplementalRedundancy,
				SSIParamName.Transmit_UPC_A_CheckDigit,
				SSIParamName.Transmit_UPC_E_CheckDigit,
				SSIParamName.Transmit_UPC_E1_CheckDigit,
				SSIParamName.UPC_A_Preamble, SSIParamName.UPC_E_Preamble,
				SSIParamName.UPC_E1_Preamble, SSIParamName.Convert_UPC_E_To_A,
				SSIParamName.Convert_UPC_E1_To_A, SSIParamName.EAN_8_ZeroExtend,
				SSIParamName.Convert_EAN_8_To_EAN_13_Type,
				SSIParamName.SecurityLevel,
				SSIParamName.UCC_CouponExtendedCode });
		this.chkTransmitUpcA.setChecked((Boolean) paramList
				.getValueAt(SSIParamName.Transmit_UPC_A_CheckDigit));
		this.chkTransmitUpcE.setChecked((Boolean) paramList
				.getValueAt(SSIParamName.Transmit_UPC_E_CheckDigit));
		this.chkTransmitUpcE1.setChecked((Boolean) paramList
				.getValueAt(SSIParamName.Transmit_UPC_E1_CheckDigit));
		this.chkConvertUpcE.setChecked((Boolean) paramList
				.getValueAt(SSIParamName.Convert_UPC_E_To_A));
		this.chkConvertUpcE1.setChecked((Boolean) paramList
				.getValueAt(SSIParamName.Convert_UPC_E1_To_A));
		this.chkEanZeroExtend.setChecked((Boolean) paramList
				.getValueAt(SSIParamName.EAN_8_ZeroExtend));
		this.chkConvertEan8toEan13.setChecked((Boolean) paramList
				.getValueAt(SSIParamName.Convert_EAN_8_To_EAN_13_Type));
		this.chkUccCoupon.setChecked((Boolean) paramList
				.getValueAt(SSIParamName.UCC_CouponExtendedCode));
		this.spnDecode.setSelection(this.adpDecode
				.getPosition((DecodeUpcEanSupplementals) paramList
						.getValueAt(SSIParamName.Decode_UPC_EAN_Supplementals)));
		this.spnDecodeRedundancy.setSelection((Integer) paramList
				.getValueAt(SSIParamName.Decode_UPC_EAN_SupplementalRedundancy));
		this.spnLevel.setSelection(this.adpLevel
				.getPosition((UpcEanSecurityLevel) paramList
						.getValueAt(SSIParamName.SecurityLevel)));
		this.spnUpcA.setSelection(this.adpUpcA.getPosition((Preamble) paramList
				.getValueAt(SSIParamName.UPC_A_Preamble)));
		this.spnUpcE.setSelection(this.adpUpcE.getPosition((Preamble) paramList
				.getValueAt(SSIParamName.UPC_E_Preamble)));
		this.spnUpcE1.setSelection(this.adpUpcE1
				.getPosition((Preamble) paramList
						.getValueAt(SSIParamName.UPC_E1_Preamble)));
	}
}
