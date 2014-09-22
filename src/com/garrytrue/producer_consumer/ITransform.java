package com.garrytrue.producer_consumer;

public interface ITransform {
	void onStart();
	boolean transform(Buffer b);
	void onStop();

}
