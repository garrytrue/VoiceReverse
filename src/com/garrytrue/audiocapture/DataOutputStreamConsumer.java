package com.garrytrue.audiocapture;

import java.io.DataOutputStream;
import java.io.IOException;

import com.garrytrue.producer_consumer.Buffer;
import com.garrytrue.producer_consumer.IConsumer;

public class DataOutputStreamConsumer implements IConsumer {
	private DataOutputStream dataOutStream;

	public DataOutputStreamConsumer(DataOutputStream dataStream) {
		dataOutStream = dataStream;
	}

	@Override
	public void onStart() {
	}

	@Override
	public boolean consume(Buffer b) {
		// TODO Auto-generated method stub
		try {
			for (int i = 0; i < b.size; i++) {
				dataOutStream.writeShort(b.buffer[i]);
			}
			return true;
		} catch (IOException e) {
			return false;
		}

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		try {
			dataOutStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}