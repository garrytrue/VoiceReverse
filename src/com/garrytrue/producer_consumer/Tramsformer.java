package com.garrytrue.producer_consumer;

import java.util.concurrent.BlockingQueue;

import android.util.Log;

public class Tramsformer {
	private final static String TAG = "Transformer";
	private ITransform mTransform;
	private BlockingQueue<Buffer> mInputQueue;
	private BlockingQueue<Buffer> mOutputQueue;
	private Thread mThread;
	private boolean isTransformed;
	private boolean last;
	private boolean mustStop = false;

	public Tramsformer(ITransform t, BlockingQueue<Buffer> input,
			BlockingQueue<Buffer> output) {
		mTransform = t;
		mInputQueue = input;
		mOutputQueue = output;
	}

	void start() {
		mThread = new Thread(new Runnable() {

			@Override
			public void run() {
				doRun();

			}
		});
		mThread.start();
	}

	void doRun() {
		Log.i(TAG, "Start Transformer");
		mTransform.onStart();
		while (true) {
			try {
				Buffer buffer = mInputQueue.take();
				last = buffer.last;
				isTransformed = mTransform.transform(buffer);
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
		mustStop = true;
		mThread.join();

	}

}
