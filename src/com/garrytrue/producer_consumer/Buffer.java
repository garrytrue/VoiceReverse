package com.garrytrue.producer_consumer;

public class Buffer {
	public short[] buffer;
	public int size;
	public boolean last;
	
	public Buffer(int capacity) {
		buffer = new short[capacity];
		size = 0;
		last = false;
	}

	@Override
	public String toString() {
		return "Buffer [size=" + size
				+ ", last=" + last + "]";
	}
}
