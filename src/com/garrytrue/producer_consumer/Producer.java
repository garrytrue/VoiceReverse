package com.garrytrue.producer_consumer;

import java.util.concurrent.BlockingQueue;

import android.util.Log;

class Producer {
	private static final String TAG = "Producer";
	private IProducer mProducer;
	private BlockingQueue<Buffer> mBufferQueue;
	private Thread mThread;
	int defCap = 1024;
	private boolean mustStop = false;

	public Producer(IProducer mIProducer, BlockingQueue<Buffer> b) {
		mProducer = mIProducer;
		mBufferQueue = b;
	}

	void start() {
		mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				doRun();
			}
		},"producer");
		mThread.start();
	}

	void stop() throws InterruptedException {
		mustStop = true;
		mThread.join();
	}

	private void doRun() {
		Log.i(TAG, "Producer Start");
		mProducer.onStart();
		try {
			while (true) {
				Buffer b = new Buffer(defCap);
				boolean readMore = mProducer.produce(b);
				boolean last = !readMore || mustStop;
				b.last = last;
				Log.i(TAG, "sending buffer: " + b);
				mBufferQueue.put(b);
				if (last) {
					break;
				}
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		Log.i(TAG, "exit");
		mProducer.onStop();
	}
}
