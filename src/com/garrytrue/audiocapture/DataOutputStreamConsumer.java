package com.garrytrue.audiocapture;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class DataOutputStreamConsumer extends BaseConsumer implements Runnable {

	private DataOutputStream _output;

	public DataOutputStreamConsumer(DataOutputStream output,
			BlockingQueue<Buffer> input) {
		super(input);
		_output = output;
	}

	protected void onStop() {
		try {
			_output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	boolean consume(Buffer b) {
		try {
			for (int i = 0; i < b.size; i++) {
				_output.writeShort(b.buffer[i]);
			}
			return !b.last;
		} catch (IOException e) {
			return false;
		}
	}
}
