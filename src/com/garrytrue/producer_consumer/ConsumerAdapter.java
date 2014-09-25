package com.garrytrue.producer_consumer;

public class ConsumerAdapter implements IBufferHandler {
	private IConsumer mIConsumer;
	
	public ConsumerAdapter(IConsumer c){
		mIConsumer = c;
	}

	@Override
	public void onStart() {
		mIConsumer.onStart();
	}

	@Override
	public boolean handle(Buffer b) {
		return mIConsumer.consume(b);
	}

	@Override
	public void onStop() {
		mIConsumer.onStop();
	}

}
