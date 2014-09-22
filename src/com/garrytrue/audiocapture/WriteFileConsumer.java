package com.garrytrue.audiocapture;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.garrytrue.producer_consumer.IConsumer;

public class WriteFileConsumer extends DataOutputStreamConsumer implements IConsumer{

	public WriteFileConsumer(String fileName) throws FileNotFoundException {
		super(createDataOutputStream(fileName));
	}
	
	public static DataOutputStream createDataOutputStream(
			String fileName) throws FileNotFoundException {
		return new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
	}
}
