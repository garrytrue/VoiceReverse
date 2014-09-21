package com.garrytrue.audiocapture;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;

import android.util.Log;

public class ReadFileProducer extends BaseProducer implements Runnable {
	private static String TAG = "ReadFileProducer";
	private InputStream _input;

	public ReadFileProducer(
			InputStream input,
			BlockingQueue<Buffer> output,
			int bufferSize) {
		super(output, bufferSize);
		_input = input;
	}

	protected void doStop() {
	}
	
	protected void onStop() {
		try {
			_input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected boolean produce(Buffer b) {
		b.size = 0;
		try {

			DataInputStream dis = new DataInputStream(_input);
			//Log.i(TAG, "start read");

			while (dis.available() > 0 && b.size < b.buffer.length) {
				b.buffer[b.size] = dis.readShort();
				b.size++;
			}
			b.last = !(0 < dis.available());
			return dis.available() > 0;
		} catch (IOException e) {
			b.last = true;
			return false;
		}
	}
}
