package com.example.cantmakecalls;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telecom.TelecomManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import static android.Manifest.permission.CALL_PHONE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements OnClickListener {

	private static final String TAG = "CantMakeCalls";

	private static final int RC_DEFAULT_PHONE = 3289;
	private static final int RC_PERMISSION = 3810;

	private TelecomManager telecomManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btn = findViewById(R.id.button);
		btn.setOnClickListener(this);

		telecomManager = (TelecomManager) getSystemService(Context.TELECOM_SERVICE);
	}

	@Override
	public void onClick(View view) {
		if (checkSelfPermission(CALL_PHONE) != PERMISSION_GRANTED) {
			askForPermission();
			return;
		}
		if (isDefaultPhoneApp()) {
			makeTheCall();
		} else {
			requestDefaultPhoneApp();
		}
	}

	private void askForPermission() {
		Log.i(TAG, "asking permissions");
		requestPermissions(new String[]{CALL_PHONE}, RC_PERMISSION);
	}

	private boolean isDefaultPhoneApp() {
		String defaultDialerPackage = telecomManager.getDefaultDialerPackage();
		Log.i(TAG, "DefaultPhoneHandler: " + defaultDialerPackage);
		return getPackageName().equals(defaultDialerPackage);
	}

	@SuppressLint("MissingPermission")
	private void makeTheCall() {
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:1-800-800-1234"));
		startActivity(intent);
	}

	private void requestDefaultPhoneApp() {
		Log.i(TAG, "asking to be default phone app");
		Intent intent = new Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER);
		intent.putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, getPackageName());
		startActivityForResult(intent, RC_DEFAULT_PHONE);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		Log.i(TAG, "permission result: request=" + requestCode + ", result=" + grantResults[0]);
		if ((requestCode == RC_PERMISSION) && (grantResults[0] == PERMISSION_GRANTED)) {
			onClick(null);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		Log.i(TAG, "activity result: request=" + requestCode + ", result=" + resultCode +
				", Default-app=" + telecomManager.getDefaultDialerPackage());
		if ((requestCode == 3289) && (resultCode == RESULT_OK)) {
			makeTheCall();
		}
	}
}
