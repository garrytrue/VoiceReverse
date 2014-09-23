package com.garrytrue.producer_consumer;

import java.util.concurrent.ArrayBlockingQueue;

public class ProduceConsumeChain {
	private Producer mProducer;
	private Consumer mConsumer;
	private ArrayBlockingQueue<Buffer> mBlockingQueue;
	
	public ProduceConsumeChain(IProducer producer, IConsumer consumer, int capacity){
		mBlockingQueue = new ArrayBlockingQueue<>(capacity);
		mProducer = new Producer(producer, mBlockingQueue);
		mConsumer = new Consumer(consumer, mBlockingQueue);
	}
	
	public void start(){
		mProducer.start();
		mConsumer.start();
	}
	
	public void stop() throws InterruptedException{
		mProducer.stop();
		mConsumer.stop();
	}

}
