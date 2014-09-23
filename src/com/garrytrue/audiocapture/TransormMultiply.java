package com.garrytrue.audiocapture;

import com.garrytrue.producer_consumer.Buffer;
import com.garrytrue.producer_consumer.ITransform;

public class TransormMultiply implements ITransform {
	private static final float MULTIPLEXER  = 1.5f; 

	@Override
	public void onStart() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean transform(Buffer b) {
		if(b.buffer.length == 0) return false;
		for (int i = 0; i < b.buffer.length; i++) {
			short tmp = b.buffer[i];
			tmp = (short) (tmp*MULTIPLEXER);
			b.buffer[i] = tmp;
		}
		return true;
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub

	}

}
