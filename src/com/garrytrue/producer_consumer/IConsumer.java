package com.garrytrue.producer_consumer;


public interface IConsumer {
	void onStart();
	boolean consume(Buffer b);
	void onStop();

}
