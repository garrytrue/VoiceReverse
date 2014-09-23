package com.garrytrue.producer_consumer;

import java.util.concurrent.BlockingQueue;

import android.util.Log;

class Consumer {
	private final static String TAG = "Comsumer";
	private IConsumer mConsumer;
	private BlockingQueue<Buffer> mBufferQueue;
	private Thread mThread;

	public Consumer(IConsumer mIConsumer, BlockingQueue<Buffer> b) {
		mConsumer = mIConsumer;
		mBufferQueue = b;
	}

	void start() {
		mThread = new Thread(new Runnable() {

			@Override
			public void run() {
				doRun();
			}
		},"consumer");
		mThread.start();
	}

	void stop() {
		try {
			mThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doRun() {
		Log.i(TAG, "start");
		mConsumer.onStart();
		try {
			while (true) {
				Buffer buffer = mBufferQueue.take();
				if (!mConsumer.consume(buffer)) {
					break;
				}
				if (buffer.last) {
					break;
				}
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		mConsumer.onStop();
		Log.i(TAG, "stop");
	}

}
