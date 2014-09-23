package com.garrytrue.audiocapture;

import java.io.FileNotFoundException;

import com.garrytrue.producer_consumer.ProducerTransformerConsumerChain;

public class RecorderMultiply extends ProducerTransformerConsumerChain {

	public RecorderMultiply(String fileName) throws FileNotFoundException {
		super(new RecordProducer(), new TransformMultiply(), new WriteFileConsumer(fileName), 10);
		// TODO Auto-generated constructor stub
	}

}
