package com.garrytrue.producer_consumer;

public interface IBufferManager {
	Buffer takeBuffer();
	boolean releaseBuffer(Buffer b);

}
