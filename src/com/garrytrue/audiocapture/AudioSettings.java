package com.garrytrue.audiocapture;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class AudioSettings {
	// 44100Hz is currently the only rate that is guaranteed to work on all
	// devices
	public static final int SAMPLE_RATE = 8000;
	// CHANNEL_IN_MONO is guaranteed to work on all devices.
	public static final int CHANNEL_CONFIG_AR = AudioFormat.CHANNEL_IN_MONO;
	public static final int CHANNEL_CONFIG_AT = AudioFormat.CHANNEL_OUT_MONO;
	// Audio data format: PCM 16 bit per sample. Guaranteed to be supported
	// by devices.
	public static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
//	private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_8BIT;
	// Creation mode where audio data is streamed from Java to the native
	// layer as the audio is playing.
	public static final int AUDIO_MODE = AudioTrack.MODE_STREAM;
	public static final int AUDIO_STREM = AudioManager.STREAM_MUSIC;

}
