package com.garrytrue.producer_consumer;

import java.util.concurrent.BlockingQueue;

public class SourceBuffManTransformer implements IBufferManager {
	private BlockingQueue<Buffer> mInputQueue;
	private BlockingQueue<Buffer> mOutputQueue;
	
	public SourceBuffManTransformer(BlockingQueue<Buffer> input, BlockingQueue<Buffer> output){
		mInputQueue = input;
		mOutputQueue = output;
	}

	@Override
	public Buffer takeBuffer() {
		try {
			return mInputQueue.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean releaseBuffer(Buffer b) {
		try {
			mOutputQueue.put(b);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
