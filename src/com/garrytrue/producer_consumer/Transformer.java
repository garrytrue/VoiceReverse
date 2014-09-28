package com.garrytrue.producer_consumer;

import java.util.concurrent.BlockingQueue;

import android.util.Log;

public class Transformer {
	private final static String TAG = "Transformer";
	private ITransform mTransform;
	private BlockingQueue<Buffer> mInputQueue;
	private BlockingQueue<Buffer> mOutputQueue;
	private Thread mThread;
	private boolean mustStop = false;

	public Transformer(ITransform t, BlockingQueue<Buffer> input,
			BlockingQueue<Buffer> output) {
		mTransform = t;
		mInputQueue = input;
		mOutputQueue = output;
	}

	void start() {
		Log.i(TAG, "start");
		mThread = new Thread(new Runnable() {

			@Override
			public void run() {
				doRun();

			}
		}, "transformer");
		mThread.start();
	}

	void doRun() {
		Log.i(TAG, "Start Transformer");
		mTransform.onStart();
		while (true) {
			try {
				Buffer buffer = mInputQueue.take();
				boolean last = buffer.last;
				boolean isTransformed = mTransform.transform(buffer);
				mOutputQueue.put(buffer);
				Log.i(TAG, "Transformered buffer " + buffer);
				if (!isTransformed || mustStop) {
					break;
				}
				if (last) {
					break;
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			Log.i(TAG, "Stop Transformer");
			mTransform.onStop();
		}
	}

	void stop() throws InterruptedException {
		Log.i(TAG, "stop");
		mustStop = true;
		mThread.join();

	}

}
