package com.garrytrue.audiocapture;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

public class ActivityMain extends ActionBarActivity {
	static final int BUTTON_REC_IS_ENABLED = 1;
	static final int BUTTON_REC_IS_DISSABLED = 0;
	static Button sRec, sPlay;
	static Chronometer sChronometer;

	private static class MHandler extends Handler {
		private final WeakReference<ActivityMain> activityMain;

		public MHandler(ActivityMain activity) {
			activityMain = new WeakReference<ActivityMain>(activity);
		}

		public void handleMessage(Message msg) {
			ActivityMain activity = activityMain.get();
			if (activity != null) {
				switch (msg.what) {
				case BUTTON_REC_IS_DISSABLED:
					Log.i("Activity", "Button is pressed in WeakRef");
					activity.getSupportFragmentManager()
							.findFragmentById(R.id.container).getView()
							.findViewById(R.id.bREC).setEnabled(false);
					break;
				case BUTTON_REC_IS_ENABLED:
					activity.getSupportFragmentManager()
							.findFragmentById(R.id.container).getView()
							.findViewById(R.id.bREC).setEnabled(true);
					activity.getSupportFragmentManager()
							.findFragmentById(R.id.container).getView()
							.findViewById(R.id.bPlay).setEnabled(true);
					((Chronometer) activity.getSupportFragmentManager()
							.findFragmentById(R.id.container).getView()
							.findViewById(R.id.chrono)).stop();
				default:
					break;
				}
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new MainFragment()).commit();
		}
	}

	protected void onStart() {
		super.onStart();
		Fragment mFragment = getSupportFragmentManager().findFragmentById(
				R.id.container);
		sRec = (Button) mFragment.getView().findViewById(R.id.bREC);
		sPlay = (Button) mFragment.getView().findViewById(R.id.bPlay);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A Main fragment containing a simple view and working logic.
	 */
	public static class MainFragment extends Fragment implements
			OnClickListener {

		private static final String TAG = "MainFragment";
		private static final String PATCH_NAME = "Records";
		// Time stamp 10 sec
		private static final long RECORD_TIME = 1 * 1000;

		private Button mbPlay, mbRec;
		private boolean isRecording = true;
		private Chronometer mChronometer;
		private MHandler mHandler = null;
		private Recorder mRecorder;// change to RecorderMultiply
		private RecorderMultiply mRecorderMultiply;
		private ReversPlay mReversPlay;// change to Player
		private Player mPlayer;

		public MainFragment() {

		}

		public void onCreate(Bundle saved) {
			super.onCreate(saved);
			Log.i(TAG, "onCreate");
		}

		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

		}

		public void onStart() {
			super.onStart();
			mHandler = new MHandler((ActivityMain) this.getActivity());
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Log.i(TAG, "onCreateView");
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			// Find buttons and set Listeners
			mbRec = (Button) rootView.findViewById(R.id.bREC);
			mbRec.setOnClickListener(this);
			mbPlay = (Button) rootView.findViewById(R.id.bPlay);
			mbPlay.setOnClickListener(this);
			mbPlay.setEnabled(!isRecording);
			mChronometer = (Chronometer) rootView.findViewById(R.id.chrono);
			return rootView;
		}

		private void startRecording() {
			try {
				// mRecorder = new Recorder(getFileName());
				mRecorderMultiply = new RecorderMultiply(getFileName());
				mRecorderMultiply.start();

				// mRecorder.start();
				mHandler.sendEmptyMessage(ActivityMain.BUTTON_REC_IS_DISSABLED);
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						stopRecording();
					}
				}, RECORD_TIME);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		private void stopRecording() {
			Log.i(TAG, "stopRecording");
			// mRecorder.stop();
			try {
				mRecorderMultiply.stop();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Log.i(TAG, "_recorder stopped");
			mHandler.sendEmptyMessage(ActivityMain.BUTTON_REC_IS_ENABLED);
		}

		// Realize Buttons Click
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.bREC:
				Log.i(TAG, "rec pressed");
				mChronometer.setBase(SystemClock.elapsedRealtime() - 1000);
				mChronometer.start();
				Toast.makeText(getActivity(),
						getResources().getText(R.string.toast),
						Toast.LENGTH_SHORT).show();

				startRecording();
				break;
			case R.id.bPlay:
				Log.i(TAG, "play pressed");
				playAudio();
				break;
			default:
				break;
			}
		}

		private String getFileName() {
			ContextWrapper mWrapper = new ContextWrapper(getActivity()
					.getApplicationContext());
			File directory = mWrapper.getDir(PATCH_NAME, Context.MODE_PRIVATE);
			return directory.getAbsolutePath() + "/" + "rec.pcm";
		}

		private void playAudio() {
//			mReversPlay = new ReversPlay(getFileName());
//			mReversPlay.start();
			mPlayer = new Player(getFileName());
			mPlayer.start();
		}
	}
}
