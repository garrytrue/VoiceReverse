package com.garrytrue.producer_consumer;

import java.util.concurrent.BlockingQueue;


public class SourceBuffManProduder implements IBufferManager {
	private BlockingQueue<Buffer> mBuffers;
	private int defCap = 1024;
	
	public SourceBuffManProduder(BlockingQueue<Buffer> b){
		mBuffers = b;
	}

	@Override
	public Buffer takeBuffer() {
		return new Buffer(defCap);
	}

	@Override
	public boolean releaseBuffer(Buffer b) {
		try {
			mBuffers.put(b);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
