package com.garrytrue.producer_consumer;

import java.util.concurrent.BlockingQueue;

public class SourceBuffManConsumer implements IBufferManager {
	private BlockingQueue<Buffer> mInQueue;
	
	public SourceBuffManConsumer(BlockingQueue<Buffer> b){
		mInQueue = b;
	}

	@Override
	public Buffer takeBuffer() {
		Buffer b = null;
		try {
			b = mInQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return b;
	}

	@Override
	public boolean releaseBuffer(Buffer b) {
		return false;
	}

}
