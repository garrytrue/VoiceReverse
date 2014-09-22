package com.garrytrue.audiocapture;

import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import com.garrytrue.producer_consumer.Buffer;
import com.garrytrue.producer_consumer.IProducer;

public class RecordProducer implements IProducer {
	private final static String TAG = "RecorderProducer";
	private AudioRecord mRecorder;

	public RecordProducer() {
		// get Min Buffer Size
		int minBuffer = AudioRecord.getMinBufferSize(AudioSettings.SAMPLE_RATE,
				AudioSettings.CHANNEL_CONFIG_AR, AudioSettings.AUDIO_FORMAT);

		// Initial AudioRecorder
		mRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
				AudioSettings.SAMPLE_RATE, AudioSettings.CHANNEL_CONFIG_AR,
				AudioSettings.AUDIO_FORMAT, minBuffer);

	}

	@Override
	public void onStart() {
		Log.i(TAG, "Recorder state must be 1 is " + mRecorder.getState());
		mRecorder.startRecording();
		Log.i(TAG,
				"Recording state mist be 3 is " + mRecorder.getRecordingState());

	}

	@Override
	public boolean produce(Buffer b) {
		int recData = mRecorder.read(b.buffer, 0, b.buffer.length);
		if (recData == AudioRecord.ERROR_INVALID_OPERATION
				|| recData == AudioRecord.ERROR_BAD_VALUE) {
			return false;
		}
		b.size = recData;
		return true;
	}

	@Override
	public void onStop() {
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
	}

}
