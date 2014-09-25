package com.garrytrue.producer_consumer;

import android.util.Log;

public class Worker {
	private final static String TAG = "Worker";
	private IBufferHandler mHandler;
	private IBufferManager mManager;
	private Thread mThread;
	private boolean mustStop = false;

	public Worker(IBufferHandler h, IBufferManager m) {
		mHandler = h;
		mManager = m;
	}

	void start() {
		mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				doRun();
			}
		}, "worker");
		mThread.start();
	}

	void stop() throws InterruptedException {
		mustStop = true;
		mThread.join();
	}

	private void doRun() {
		mHandler.onStart();
		while (true) {
			Buffer b = mManager.takeBuffer();
			boolean readMore = mHandler.handle(b);
			boolean last = !readMore || mustStop;
			b.last = last;
			Log.i(TAG, "sending buffer: " + b);
			mManager.releaseBuffer(b);
			if (last) {
				break;
			}
		}
		Log.i(TAG, "exit");
		mHandler.onStop();
	}
}
