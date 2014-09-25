package com.garrytrue.producer_consumer;

public interface IBufferHandler {
	void onStart();
	boolean handle(Buffer b);
	void onStop();

}
