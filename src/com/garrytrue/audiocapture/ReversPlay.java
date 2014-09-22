package com.garrytrue.audiocapture;

import com.garrytrue.producer_consumer.ProduceConsumeChain;

public class ReversPlay extends ProduceConsumeChain {

	public ReversPlay(String fileName) {
		super(new ReverseReader(fileName), new PlayConsumer(), 10);
	
	}

}
