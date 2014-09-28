package com.garrytrue.audiocapture;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.util.Log;

import com.garrytrue.producer_consumer.Buffer;
import com.garrytrue.producer_consumer.IProducer;

public class ReverseReaderProducer implements IProducer {
	private final static String TAG = "ReverseReader";
	private String mFileName;
	private RandomAccessFile mFile;
	private byte[] mInternalBuff;
	private long mCurPos;

	public ReverseReaderProducer(String name) {
		mFileName = name;

	}

	@Override
	public void onStart() {
		try {
			mFile = new RandomAccessFile(mFileName, "r");
			mCurPos = mFile.length();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.e(TAG, "File not found", e);
		} catch (IOException e1) {
			e1.printStackTrace();
			Log.e(TAG, "Can't get file size", e1);
		}
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

	@Override
	public boolean produce(Buffer b) {
		try {
			lazyInitInternalBuffer(b);
			Log.i(TAG, "fp=" + mFile.getFilePointer());
			int byteCount;
			if (mInternalBuff.length <= mCurPos) {
				byteCount = mInternalBuff.length;
			} else {
				byteCount = (int) mCurPos;
			}
			mCurPos -= byteCount;
			Log.i(TAG, "read block: [" + mCurPos + ", " + byteCount + ")");
			mFile.seek(mCurPos);
			mFile.readFully(mInternalBuff, 0, byteCount);
			byteToShort(b);
			reverse(b.buffer);
			b.size = byteCount / 2;
			return mCurPos != 0;
		} catch (IOException e) {
			Log.e(TAG, e.toString());
			return false;
		}
	}

	private void byteToShort(Buffer b) {
		ByteBuffer.wrap(mInternalBuff).order(ByteOrder.BIG_ENDIAN)
				.asShortBuffer().get(b.buffer);
	}

	private void lazyInitInternalBuffer(Buffer b) {
		if (mInternalBuff == null
				|| mInternalBuff.length != b.buffer.length * 2) {
			mInternalBuff = new byte[b.buffer.length * 2];
		}
	}

	@Override
	public void onStop() {
		try {
			mFile.close();
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, "Problem with close", e);
		}

	}

}