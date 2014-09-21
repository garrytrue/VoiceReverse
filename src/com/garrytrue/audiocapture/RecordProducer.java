package com.garrytrue.audiocapture;

import java.util.concurrent.BlockingQueue;

import android.media.AudioRecord;
import android.media.MediaRecorder;

public class RecordProducer extends BaseProducer implements Runnable {
	private AudioRecord _recorder;
	private volatile boolean stop;
	
	public RecordProducer(BlockingQueue<Buffer> output) {
		super(output, AudioRecord.getMinBufferSize(
				AudioSettings.SAMPLE_RATE,
				AudioSettings.CHANNEL_CONFIG_AR,
				AudioSettings.AUDIO_FORMAT));
		stop = false;

		_recorder = new AudioRecord(
				MediaRecorder.AudioSource.MIC,
				AudioSettings.SAMPLE_RATE,
				AudioSettings.CHANNEL_CONFIG_AR,
				AudioSettings.AUDIO_FORMAT,
				_bufferSize);
		_recorder.startRecording();
	}
	
	protected void doStop() {
		stop = true;
	}
	
	protected boolean produce(Buffer b) {
		b.size = 0;
		if (stop) {
			return false;
		}
//		_recorder.getState();
//		_recorder.getRecordingState();

		
		//Log.i("Recorder", "RecordingState: " + _recorder.getRecordingState());
		int bytesRead = _recorder.read(b.buffer, 0, b.buffer.length);
		if (stop) {
			return false;
		}
		if (bytesRead == AudioRecord.ERROR_INVALID_OPERATION
				|| bytesRead == AudioRecord.ERROR_BAD_VALUE) {
			b.last = true;
			return false;
		}
		b.size = bytesRead;
		b.last = false;
		return true;
	}

	protected void onStop() {
		_recorder.stop();
		_recorder.release();
	}

}
