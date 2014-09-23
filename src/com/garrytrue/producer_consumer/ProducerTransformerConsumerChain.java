package com.garrytrue.producer_consumer;

import java.util.concurrent.ArrayBlockingQueue;

public class ProducerTransformerConsumerChain {
	private Producer mProducer;
	private Consumer mConsumer;
	private Transformer mTransformer;
	private ArrayBlockingQueue<Buffer> mInputQueue;
	private ArrayBlockingQueue<Buffer> mOutputQueue;

	public ProducerTransformerConsumerChain(IProducer p, ITransform tr, IConsumer c, int capacity) {
		mInputQueue = new ArrayBlockingQueue<>(10);
		mOutputQueue = new ArrayBlockingQueue<>(10);
		mProducer = new Producer(p, mInputQueue);
		mTransformer = new Transformer(tr, mInputQueue, mOutputQueue);
		mConsumer = new Consumer(c, mOutputQueue);
	}
	public void start(){
		mProducer.start();
		mTransformer.start();
		mConsumer.start();
	}
	public void stop() throws InterruptedException{
		mProducer.stop();
		mTransformer.stop();
		mConsumer.stop();
	}
}
