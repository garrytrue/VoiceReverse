package com.garrytrue.audiocapture;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.BlockingQueue;

import android.util.Log;

public class ReverseReader extends BaseProducer implements Runnable {
	private static String TAG = "ReverseReader";

	RandomAccessFile _file;
	byte[] _buffer;
	long _fileSize;
	long _pos;

	public ReverseReader(String fileName, BlockingQueue<Buffer> output)
			throws IOException {
		super(output, 1024 * 20);
		_file = new RandomAccessFile(fileName, "r");
		_fileSize = _file.length();
		_pos = _fileSize;
	}

	public static void reverse(short[] array) {
		if (array == null) {
			return;
		}
		int i = 0;
		int j = array.length - 1;
		short tmp;
		while (j > i) {
			tmp = array[j];
			array[j] = array[i];
			array[i] = tmp;
			j--;
			i++;
		}
	}

	protected boolean produce(Buffer b) {
		try {
			if (_buffer == null || _buffer.length != b.buffer.length * 2) {
				_buffer = new byte[b.buffer.length * 2];
			}

			Log.i(TAG, "fp=" + _file.getFilePointer());

			int byteCount;
			if (_buffer.length <= _pos) {
				byteCount = _buffer.length;
			} else {
				byteCount = (int) _pos;
			}
			_pos -= byteCount;
			Log.i(TAG, "read block: [" + _pos + ", " + byteCount + ")");
			_file.seek(_pos);
			_file.readFully(_buffer, 0, byteCount);

			ByteBuffer.wrap(_buffer).order(ByteOrder.BIG_ENDIAN)
					.asShortBuffer().get(b.buffer);
			reverse(b.buffer);
			b.size = byteCount / 2;
			b.last = (_pos == 0);

			return _pos != 0;
		} catch (IOException e) {
			Log.e(TAG, e.toString());
			return false;
		}
	}

	@Override
	protected void doStop() {
		// TODO Auto-generated method stub

	}
	
	protected void onStop() {
		try {
			_file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
