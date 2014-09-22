package com.garrytrue.audiocapture;

import com.garrytrue.producer_consumer.Buffer;
import com.garrytrue.producer_consumer.IConsumer;

import android.media.AudioTrack;
import android.util.Log;

public class PlayConsumer implements IConsumer {
	private final static String TAG = "PlayComsumer";
	private AudioTrack mAudioTrack;

	public PlayConsumer() {
		int minBufferAudioTrack = AudioTrack.getMinBufferSize(
				AudioSettings.SAMPLE_RATE, AudioSettings.CHANNEL_CONFIG_AT,
				AudioSettings.AUDIO_FORMAT);

		mAudioTrack = new AudioTrack(AudioSettings.AUDIO_STREM,
				AudioSettings.SAMPLE_RATE, AudioSettings.CHANNEL_CONFIG_AT,
				AudioSettings.AUDIO_FORMAT, minBufferAudioTrack,
				AudioSettings.AUDIO_MODE);
		Log.i(TAG, "AudioTrackState " + mAudioTrack.getState());
	}

	@Override
	public void onStart() {
		mAudioTrack.play();

	}

	@Override
	public boolean consume(Buffer b) {
		int remaining = b.size;
		int offset = 0;

		while (0 < remaining) {
			int numWrite = mAudioTrack.write(b.buffer, offset, remaining);
			if (numWrite == AudioTrack.ERROR_INVALID_OPERATION
					|| numWrite == AudioTrack.ERROR_BAD_VALUE) {
				return false;
			} else {
				offset += numWrite;
				remaining -= numWrite;
			}
		}
		return true;
	}

	@Override
	public void onStop() {
		mAudioTrack.stop();
		mAudioTrack.release();
		mAudioTrack = null;

	}

}
