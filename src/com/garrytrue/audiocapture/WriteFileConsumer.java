package com.garrytrue.audiocapture;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class WriteFileConsumer extends DataOutputStreamConsumer {

	public WriteFileConsumer(String fileName,
			BlockingQueue<Buffer> input) throws FileNotFoundException {
		super(createDataOutputStream(fileName),input);
	}
	
	public static DataOutputStream createDataOutputStream(
			String fileName) throws FileNotFoundException {
		return new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
	}
}
