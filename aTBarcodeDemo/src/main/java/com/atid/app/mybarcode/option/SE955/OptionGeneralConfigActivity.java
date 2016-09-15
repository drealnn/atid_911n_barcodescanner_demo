package com.atid.app.mybarcode.option.SE955;

import com.atid.lib.dev.ATScanManager;
import com.atid.lib.dev.ATScanner;
import com.atid.lib.dev.barcode.motorola.param.SSIParamName;
import com.atid.lib.dev.barcode.motorola.param.SSIParamValueList;
import com.atid.lib.dev.barcode.params.ATScanSE955Parameter;
import com.atid.lib.dev.barcode.motorola.type.LinearCodeTypeSecurityLevel;
import com.atid.lib.dev.barcode.motorola.type.SSIBeepVolumeType;
import com.atid.lib.dev.barcode.motorola.type.SSIScanAngleType;
import com.atid.app.mybarcode.R;
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

public class OptionGeneralConfigActivity extends Activity implements
		OnClickListener {

	@SuppressWarnings("unused")
	private static final String TAG = "OptionGeneralConfigActivity";

	private ATScanner mScanner;
	private ATScanSE955Parameter mParam;

	private Spinner spnLevel;
	private CheckBox chkBidirect;
	private Spinner spnScanAngle;
	private Spinner spnLaserOnTime;
	private Spinner spnAimDuration;
	private Spinner spnTimeout;
	private CheckBox chkBeep;
	private Spinner spnBeepVolume;
	private Button btnSetOption;

	private ValueAdapter<LinearCodeTypeSecurityLevel> adpLevel;
	private ValueAdapter<SSIScanAngleType> adpScanAngle;
	private ValueAdapter<Float> adpLaserOnTime;
	private ValueAdapter<Float> adpAimDuration;
	private ValueAdapter<Float> adpTimeout;
	private ValueAdapter<SSIBeepVolumeType> adpBeepVolume;

	private static final int MIN_LASER_ON_TIME = 5;
	private static final int MAX_LASER_ON_TIME = 99;
	private static final int MIN_AIM_DURATION = 0;
	private static final int MAX_AIM_DURATION = 99;
	private static final int MIN_TIMEOUT_BETWEEN_SAME_SYMBOL = 0;
	private static final int MAX_TIMEOUT_BETWEEN_SAME_SYMBOL = 99;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option_1d_general_config);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// Initialize Scanner Instance
		this.mScanner = ATScanManager.getInstance();
		this.mParam = (ATScanSE955Parameter) this.mScanner.getParameter();

		// Initialize Widgets
		this.spnLevel = (Spinner) findViewById(R.id.linear_code_type_security_level);
		this.adpLevel = new ValueAdapter<LinearCodeTypeSecurityLevel>(this);
		for (LinearCodeTypeSecurityLevel item : LinearCodeTypeSecurityLevel
				.values()) {
			this.adpLevel.add(new ValueItem<LinearCodeTypeSecurityLevel>(item
					.toString(), item));
		}
		this.spnLevel.setAdapter(this.adpLevel);

		this.chkBidirect = (CheckBox) findViewById(R.id.bidirectional_redundancy);

		this.spnScanAngle = (Spinner) findViewById(R.id.scan_angle);
		this.adpScanAngle = new ValueAdapter<SSIScanAngleType>(this);
		for (SSIScanAngleType item : SSIScanAngleType.values()) {
			this.adpScanAngle.add(new ValueItem<SSIScanAngleType>(item.toString(),
					item));
		}
		this.spnScanAngle.setAdapter(this.adpScanAngle);

		this.spnLaserOnTime = (Spinner) findViewById(R.id.laser_on_time);
		this.adpLaserOnTime = new ValueAdapter<Float>(this);
		this.adpLaserOnTime
				.add(new ValueItem<Float>("Not Used", (float) 25.5F));
		for (int i = MIN_LASER_ON_TIME; i <= MAX_LASER_ON_TIME; i++) {
			this.adpLaserOnTime.add(new ValueItem<Float>(String.format(
					"%.1f sec", (float) i / 10.0F), (float) i / 10.0F));
		}
		this.spnLaserOnTime.setAdapter(this.adpLaserOnTime);

		this.spnAimDuration = (Spinner) findViewById(R.id.aim_duration);
		this.adpAimDuration = new ValueAdapter<Float>(this);
		for (int i = MIN_AIM_DURATION; i <= MAX_AIM_DURATION; i++) {
			this.adpAimDuration.add(new ValueItem<Float>(String.format(
					"%.1f sec", (float) i / 10.0F), (float) i / 10.0F));
		}
		this.spnAimDuration.setAdapter(this.adpAimDuration);

		this.spnTimeout = (Spinner) findViewById(R.id.time_out_between_same_symbol);
		this.adpTimeout = new ValueAdapter<Float>(this);
		for (int i = MIN_TIMEOUT_BETWEEN_SAME_SYMBOL; i <= MAX_TIMEOUT_BETWEEN_SAME_SYMBOL; i++) {
			this.adpTimeout.add(new ValueItem<Float>(String.format("%.1f sec",
					(float) i / 10.0F), (float) i / 10.0F));
		}
		this.spnTimeout.setAdapter(this.adpTimeout);

		this.chkBeep = (CheckBox) findViewById(R.id.beep_after_good_decode);
		this.spnBeepVolume = (Spinner) findViewById(R.id.beeper_volume);
		this.adpBeepVolume = new ValueAdapter<SSIBeepVolumeType>(this);
		for (SSIBeepVolumeType item : SSIBeepVolumeType.values()) {
			this.adpBeepVolume.add(new ValueItem<SSIBeepVolumeType>(item.toString(),
					item));
		}
		this.spnBeepVolume.setAdapter(this.adpBeepVolume);

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

			paramList.add(SSIParamName.RedundancyLevel,
					this.adpLevel.getItem(this.spnLevel
							.getSelectedItemPosition()));
			paramList.add(SSIParamName.BidirectionalRedundancy,
					this.chkBidirect.isChecked());
			paramList.add(SSIParamName.ScanAngle, this.adpScanAngle
					.getItem(this.spnScanAngle.getSelectedItemPosition()));
			paramList.add(SSIParamName.LaserOnTime, this.adpLaserOnTime
					.getItem(this.spnLaserOnTime.getSelectedItemPosition()));
			paramList.add(SSIParamName.AimDuration, this.adpAimDuration
					.getItem(this.spnAimDuration.getSelectedItemPosition()));
			paramList.add(SSIParamName.TimeoutBetweenSameSymbol, this.adpTimeout
					.getItem(this.spnTimeout.getSelectedItemPosition()));
			paramList.add(SSIParamName.BeepAfterGoodDecode,
					this.chkBeep.isChecked());
			paramList.add(SSIParamName.BeeperVolume, this.adpBeepVolume
					.getItem(this.spnBeepVolume.getSelectedItemPosition()));

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
				SSIParamName.RedundancyLevel,
				SSIParamName.BidirectionalRedundancy, SSIParamName.ScanAngle,
				SSIParamName.LaserOnTime, SSIParamName.AimDuration,
				SSIParamName.TimeoutBetweenSameSymbol,
				SSIParamName.BeepAfterGoodDecode, SSIParamName.BeeperVolume });
		this.spnLevel.setSelection(this.adpLevel
				.getPosition((LinearCodeTypeSecurityLevel) paramList
						.getValueAt(SSIParamName.RedundancyLevel)));
		this.chkBidirect.setChecked((Boolean) paramList
				.getValueAt(SSIParamName.BidirectionalRedundancy));
		this.spnScanAngle.setSelection(this.adpScanAngle
				.getPosition((SSIScanAngleType) paramList
						.getValueAt(SSIParamName.ScanAngle)));
		this.spnLaserOnTime.setSelection(this.adpLaserOnTime
				.getPosition((Float) paramList
						.getValueAt(SSIParamName.LaserOnTime)));
		this.spnAimDuration.setSelection(this.adpAimDuration
				.getPosition((Float) paramList
						.getValueAt(SSIParamName.AimDuration)));
		this.spnTimeout.setSelection(this.adpTimeout
				.getPosition((Float) paramList
						.getValueAt(SSIParamName.TimeoutBetweenSameSymbol)));
		this.chkBeep.setChecked((Boolean) paramList
				.getValueAt(SSIParamName.BeepAfterGoodDecode));
		this.spnBeepVolume.setSelection(this.adpBeepVolume
				.getPosition((SSIBeepVolumeType) paramList
						.getValueAt(SSIParamName.BeeperVolume)));
	}
}
