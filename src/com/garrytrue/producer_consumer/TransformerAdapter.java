package com.garrytrue.producer_consumer;

public class TransformerAdapter implements IBufferHandler {
	private ITransform mTransformer;
	
	public TransformerAdapter(ITransform t){
		mTransformer = t;
	}

	@Override
	public void onStart() {
		mTransformer.onStart();

	}

	@Override
	public boolean handle(Buffer b) {
		return mTransformer.transform(b);
	}

	@Override
	public void onStop() {
		mTransformer.onStop();
	}

}
