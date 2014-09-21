package com.garrytrue.audiocapture;

import java.util.concurrent.BlockingQueue;

import android.util.Log;

public abstract class BaseProducer implements Runnable {
	private static String TAG = "ReadFileProducer";
	private BlockingQueue<Buffer> _output;
	private Thread _thread;
	public int _bufferSize;

	public BaseProducer(
			BlockingQueue<Buffer> output,
			int bufferSize) {
		_output = output;
		_bufferSize = bufferSize;
	}

	public void start() {
		_thread = new Thread(this);
		_thread.start();
	}
	
	public void stop() throws InterruptedException {
		doStop();
		_thread.join();
	}
	
	protected abstract void onStop();
	protected abstract void doStop();
	protected abstract boolean produce(Buffer b);

	@Override
	public void run() {
		Log.i(TAG, "start");
		try {
			boolean readMore = true;
			while (readMore) {
				Buffer b = new Buffer(_bufferSize);
				readMore = produce(b);
				if (!readMore) {
					b.last = true;
				}
				_output.put(b);
				Log.i(TAG, "sent buffer: " + b);
			}
		} catch (InterruptedException e) {
		}
		onStop();
		Log.i(TAG, "exit");
	}
}
