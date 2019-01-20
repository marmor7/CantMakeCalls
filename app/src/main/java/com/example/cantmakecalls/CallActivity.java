package com.example.cantmakecalls;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.telecom.Call.Callback;
import android.telecom.VideoProfile;
import android.util.Log;
import android.view.View;

public class CallActivity extends AppCompatActivity {

	public static Call call = null;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.call_ui);

		call.registerCallback(new Callback() {
			@Override
			public void onStateChanged(Call call, int state) {
				super.onStateChanged(call, state);
				Log.i("CantMakeCalls", "state changed - " + call + ", " + state);
				if (state == Call.STATE_DISCONNECTED) {
					finish();
				}
			}
		});
	}

	public void onClick(View view) {
		if (view.getId() == R.id.answer) {
			call.answer(VideoProfile.STATE_AUDIO_ONLY);
		} else {
			call.disconnect();
		}
	}
}
