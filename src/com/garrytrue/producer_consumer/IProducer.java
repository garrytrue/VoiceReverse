package com.garrytrue.producer_consumer;


public interface IProducer {
	void onStart();
	boolean produce(Buffer b);
	void onStop();

}
