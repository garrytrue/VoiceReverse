package com.garrytrue.audiocapture;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.util.Log;

import com.garrytrue.producer_consumer.Buffer;
import com.garrytrue.producer_consumer.IProducer;

public class ReadFileProducer implements IProducer {
	private final static String TAG = "ReaderFileProducer";
	private String mFileName;
	private DataInputStream mInput;

	public ReadFileProducer(String fileName) {
		mFileName = fileName;
	}

	@Override
	public void onStart() {
		try {
			mInput = new DataInputStream(new BufferedInputStream(
					new FileInputStream(mFileName)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.e(TAG, "File not found", e);
		}
	}

	@Override
	public boolean produce(Buffer b) {
		Log.i(TAG, "Start ReadFile");
		try {
			Log.i(TAG, "Buffer lenght "+ b.buffer.length);
			for (int i = 0; 0 < mInput.available() && i < b.buffer.length; i++) {
				b.buffer[i] = mInput.readShort();
				b.size = i + 1;
				}
			return (0 < mInput.available());
		} catch (IOException ex) {
			Log.e(TAG, "IOException", ex);
			return false;
		}
	}

	@Override
	public void onStop() {
		try {
			mInput.close();
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, "Can not close stream ", e);
		}

	}
}