package com.garrytrue.producer_consumer;

public class ProducerAdapter implements IBufferHandler {
	private IProducer mIProducer;
	public ProducerAdapter(IProducer p){
		mIProducer = p;
	}

	@Override
	public void onStart() {
		mIProducer.onStart();

	}

	@Override
	public boolean handle(Buffer b) {
		return mIProducer.produce(b);
	}

	@Override
	public void onStop() {
		mIProducer.onStop();
	}

}
