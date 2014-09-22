package com.garrytrue.audiocapture;

import java.io.FileNotFoundException;

import com.garrytrue.producer_consumer.ProduceConsumeChain;

public class Recorder extends ProduceConsumeChain {

	public Recorder(String fileName) throws FileNotFoundException {
		super(new RecordProducer(), new WriteFileConsumer(fileName), 10);
	}

}
