package com.example.cantmakecalls;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.telecom.InCallService;
import android.util.Log;

public class CallService extends InCallService {

	@Override
	public void onCallAdded(Call call) {
		super.onCallAdded(call);
		Log.i("CantMakeCalls", "OnCallAdded - " + call);
		CallActivity.call = call;
		startActivity(new Intent(this, CallActivity.class));
	}

	@Override
	public void onCallRemoved(Call call) {
		super.onCallRemoved(call);
		Log.i("CantMakeCalls", "OnCallRemoved - " + call);
		CallActivity.call = null;
	}

	@Override
	public void onCanAddCallChanged(boolean canAddCall) {
		super.onCanAddCallChanged(canAddCall);
		Log.i("CantMakeCalls", "onCanAddCallChanged - " + canAddCall);
	}

	@Override
	public void onSilenceRinger() {
		super.onSilenceRinger();
		Log.i("CantMakeCalls", "onSilenceRinger");
	}

	@Override
	public void onConnectionEvent(Call call, String event, Bundle extras) {
		super.onConnectionEvent(call, event, extras);
		Log.i("CantMakeCalls", "onConnectionEvent - " + call);
	}
}
