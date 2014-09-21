package com.garrytrue.audiocapture;

import java.util.concurrent.BlockingQueue;

import android.media.AudioTrack;
import android.util.Log;

public class PlayConsumer extends BaseConsumer implements Runnable {
	private static String TAG = "PlayConsumer";
	AudioTrack _at;

	public PlayConsumer(BlockingQueue<Buffer> input) {
		super(input);
		_at = createAudioTrack();
		_at.play();
	}
	
	private AudioTrack createAudioTrack() {
		int minBufferAudioTrack = AudioTrack.getMinBufferSize(
				AudioSettings.SAMPLE_RATE, AudioSettings.CHANNEL_CONFIG_AT,
				AudioSettings.AUDIO_FORMAT);
		Log.i(TAG, "MinBufferSizeAudioTrack is" + minBufferAudioTrack);
		AudioTrack audioTrack = new AudioTrack(AudioSettings.AUDIO_STREM,
				AudioSettings.SAMPLE_RATE, AudioSettings.CHANNEL_CONFIG_AT,
				AudioSettings.AUDIO_FORMAT, minBufferAudioTrack,
				AudioSettings.AUDIO_MODE);
		Log.i(TAG, "AudioTrackState " + audioTrack.getState());
		return audioTrack;
	}

	boolean consume(Buffer b) {
		int remaining = b.size;
		int offset = 0;

		while (0 < remaining) {
			int numRead = _at.write(b.buffer, offset, remaining);
			if (numRead == AudioTrack.ERROR_INVALID_OPERATION
					|| numRead == AudioTrack.ERROR_BAD_VALUE) {
				return false;
			} else {
				offset += numRead;
				remaining -= numRead;
			}
		}
		return true;
	}

	protected void onStop() {
		_at.stop();
		_at.release();
	}
}
