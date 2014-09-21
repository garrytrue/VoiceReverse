package com.garrytrue.audiocapture;

import java.util.concurrent.BlockingQueue;

import android.util.Log;

public abstract class BaseConsumer implements Runnable {
	private static String TAG = "BaseConsumer";
	private BlockingQueue<Buffer> _input;
	private Thread _thread;

	public BaseConsumer(BlockingQueue<Buffer> input) {
		_input = input;
	}
	
	public void start() {
		_thread = new Thread(this);
		_thread.start();
	}
	
	public void stop() throws InterruptedException {
		_thread.join();
	}

	abstract void onStop();
	abstract boolean consume(Buffer b);

	@Override
	public void run() {
		//Log.i(TAG, "start");
		try {
			while (true) {
				Buffer buffer = _input.take();
				//Log.i(TAG, "input buffer: " + buffer);
				if (0 < buffer.size) {
					if (!consume(buffer)) {
						break;
					}
				}
				if (buffer.last) {
					break;
				}
			}
		} catch (InterruptedException e) {
		}
		onStop();
		//Log.i(TAG, "exit");
	}
}
