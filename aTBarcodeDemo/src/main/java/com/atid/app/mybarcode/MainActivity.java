package com.atid.app.mybarcode;

import com.atid.lib.dev.ATScanManager;
import com.atid.lib.dev.ATScanner;
import com.atid.lib.dev.barcode.type.BarcodeType;
import com.atid.lib.dev.barcode.type.EventType;
import com.atid.lib.dev.event.BarcodeEventListener;
import com.atid.app.mybarcode.adapter.BarcodeListAdapter;
import com.atid.app.mybarcode.option.SE955.OptionActivitySE955;
import com.atid.app.mybarcode.option.SE4710.OptionActivitySE4710;
import com.atid.app.mybarcode.option.MDI3x00.OptionActivityMDI3x00;
import com.atid.app.mybarcode.option.IT5x80.OptionActivityIT5x80;
import com.atid.lib.system.device.type.BarcodeModuleType;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle; 
import android.os.PowerManager;
import android.os.Vibrator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("Wakelock")
public class MainActivity extends Activity implements Button.OnClickListener,
		BarcodeEventListener {

	private static final String TAG = "MainActivity";  

	private ATScanner mScanner;
	private PowerManager.WakeLock mWakeLock = null;
	private SoundPool mSoundPool;
	private int mBeepSuccess;
	private int mBeepFail;
	private Vibrator mVibrator;

	private TextView txtDemoVersion;
	private TextView txtDeviceRevision;
	private ListView lstBarcodeList;
	private Button btnClear;
	private Button btnScanAction;

	private BarcodeListAdapter adapterBarcode;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Log.d(TAG, "+++ onCreate");
		
		if ((this.mScanner = ATScanManager.getInstance( BarcodeModuleType.AT2D4710M_1)) == null) {
			showDeviceDialog();
			return;
		}
		
		// Setup always wake up
		if (this.mWakeLock == null) {
			PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
			this.mWakeLock = powerManager.newWakeLock(
					PowerManager.FULL_WAKE_LOCK, "wakeLock");
			this.mWakeLock.acquire();
		}
		
		// Initialize Sound Pool
		this.mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		this.mBeepSuccess = this.mSoundPool.load(this, R.raw.success, 1);
		this.mBeepFail = this.mSoundPool.load(this, R.raw.fail, 1);
		// Initialize Vibrator
		/*this.mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);*/
		this.mScanner.setEventListener(this);
		
		// Initialize Widgets
		this.txtDemoVersion = (TextView) findViewById(R.id.demo_version);
		this.txtDeviceRevision = (TextView) findViewById(R.id.device_revision);
		this.lstBarcodeList = (ListView) findViewById(R.id.scan_result);
		this.adapterBarcode = new BarcodeListAdapter(this);
		this.lstBarcodeList.setAdapter(this.adapterBarcode);
		this.lstBarcodeList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		this.btnClear = (Button) findViewById(R.id.clear);
		this.btnClear.setOnClickListener(this);
		this.btnScanAction = (Button) findViewById(R.id.scan_action);
		this.btnScanAction.setOnClickListener(this);

		String packageName = getPackageName();
		String versionName = "";
		try {
			versionName = getPackageManager().getPackageInfo(packageName,
					PackageManager.GET_META_DATA).versionName;
		} catch (NameNotFoundException e) {
		}
		this.txtDemoVersion.setText(versionName);

		String revision = this.mScanner.getVersion();
		this.txtDeviceRevision.setText(revision);
		
		Log.d(TAG, "--- onCreate");
	}

	@Override
	protected void onDestroy() {

		Log.d(TAG, "+++ onDestroy");
		
		// Deinitalize Scanner Instance
		ATScanManager.finish();

		// Setup basic wake up state
		if (this.mWakeLock != null) {
			this.mWakeLock.release();
			this.mWakeLock = null;
		}
			
		super.onDestroy();
		
		Log.d(TAG, "--- onDestroy");
	}
	
	
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "+- onStart");
		
		if(mScanner != null)
			ATScanManager.wakeUp();
	}

	@Override
	protected void onResume() {
		super.onResume();

		Log.d(TAG, "+- onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "+- onPause");
	}

	@Override
	protected void onStop() {
		if(mScanner != null) {
			ATScanManager.sleep();
		}
		
		super.onStop();
		Log.d(TAG, "+- onStop");
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		
		switch (item.getItemId()) {
		case R.id.scanner_config:
		case R.id.menu_scanner_config:
			Log.d(TAG, "scannerType : " + mScanner.getScannerType());
			switch (mScanner.getScannerType()) {
			case AT1D955M_1:
				intent = new Intent(this, OptionActivitySE955.class);
				startActivity(intent);
				return true;
			case AT2D5X80I_1:
				intent = new Intent(this, OptionActivityIT5x80.class);
				startActivity(intent);
				return true;
			case AT2D4710M_1:
				intent = new Intent(this, OptionActivitySE4710.class);
				startActivity(intent);
				return true;
			case AT2DMDI3x00:
				intent = new Intent(this, OptionActivityMDI3x00.class);
				startActivity(intent);
				return true;
			default:
				break;
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.scan_action:
			enableScanButton(false);

			if (this.mScanner.isDecoding()) {
				this.mScanner.stopDecode();
			} else {
				this.mScanner.startDecode();
			}
			enableScanButton(true);
			break;
		case R.id.clear:
			this.adapterBarcode.clear();
			break;
		}
	}

	@Override
	public void onStateChanged(EventType state) {
		
		Log.d(TAG, "EventType : " + state.toString());
	}

	@Override
	public void onDecodeEvent(BarcodeType type, String barcode) {

		Log.d(TAG, "onDecodeEvent(" + type + ", [" + barcode
				+ "])");

			if(type != BarcodeType.NoRead){
				int position = this.adapterBarcode.addItem(type, barcode);
				this.lstBarcodeList.setSelection(position);
				beep(true);
			}else{
				beep(false);
			}

		enableScanButton(true);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_SOFT_RIGHT
				|| keyCode == KeyEvent.KEYCODE_SHIFT_RIGHT || keyCode == KeyEvent.KEYCODE_SHIFT_LEFT)
				&& event.getRepeatCount() <= 0 && !this.mScanner.isDecoding()) {

			Log.d(TAG, "DEBUG. onKeyDown(" + keyCode + ")");

			enableScanButton(false);
			this.mScanner.startDecode();
			enableScanButton(true);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_SOFT_RIGHT
				|| keyCode == KeyEvent.KEYCODE_SHIFT_RIGHT || keyCode == KeyEvent.KEYCODE_SHIFT_LEFT)
				&& this.mScanner.isDecoding()) {

			Log.d(TAG, "DEBUG. onKeyUp(" + keyCode + ")");

			this.mScanner.stopDecode();
			enableScanButton(true);
		}
		return super.onKeyUp(keyCode, event);
	}

	// Show Error Device Dialog
	private void showDeviceDialog() {
		// Show Error Alert Dialog
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setOnCancelListener(new DialogInterface.OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		});
		alert.setPositiveButton(R.string.ok_button,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});
		alert.setIcon(android.R.drawable.ic_dialog_alert);
		alert.setTitle(R.string.device_error);
		alert.setMessage(R.string.filaed_to_device);
		alert.show();
	}

	// Beep & Vibrate
	private void beep(boolean isSuccess) {
		Log.d(TAG, "@@@@ DEBUG. Play beep....!!!!");
		try{
			if(isSuccess){
				this.mSoundPool.play(mBeepSuccess, 1, 1, 0, 0, 1);
				this.mVibrator.vibrate(100);
			}else{
				this.mSoundPool.play(mBeepFail, 1, 1, 0, 0, 1);
			}
		}catch(Exception ex){
		}
	}

	// Enable & Disable Scan Button...
	private void enableScanButton(boolean enable) {
		
		Log.d(TAG, "===>>> isDecoding : " + mScanner.isDecoding());
		
		if (enable) {
			if (this.mScanner.isDecoding()) {
				btnScanAction.setText(R.string.stop_scan_label);
			} else {
				btnScanAction.setText(R.string.start_scan_label);
			}
		}
		btnScanAction.setEnabled(enable);
	}
}


