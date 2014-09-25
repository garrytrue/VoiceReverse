package com.garrytrue.audiocapture;

import android.util.Log;

import com.garrytrue.producer_consumer.Buffer;
import com.garrytrue.producer_consumer.ITransform;

public class TransformMultiply implements ITransform {
	private static final String TAG = "TransformerMultiply";
	
//	private static final float MULTIPLEXER  = 1.5f;
	private static final short INCEMENTAL = 20000;

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		Log.i(TAG, "Multiply start");

	}

	@Override
	public boolean transform(Buffer b) {
		Log.i(TAG, "Start transformation");
		if(b.buffer.length == 0) return false;
		for (int i = 0; i < b.buffer.length; i++) {
			short tmp = b.buffer[i];
//			with Log tread sooooo sloy
//			Log.i(TAG, "Before multiply "+ tmp);
			tmp = (short) (tmp+INCEMENTAL);
//			Log.i(TAG, "After multiply " + tmp);
			b.buffer[i] = tmp;
		}
		return true;
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		Log.i(TAG, "Multiply stop");

	}

}
