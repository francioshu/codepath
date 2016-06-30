package jjj.demo.floatwindow;

import jjj.demo.folatwindowdemo.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.Window;

public class MainActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mian);
		findViewById(R.id.main_btn_show).setOnClickListener(this);
		findViewById(R.id.main_btn_hide).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_btn_show:
			startService(new Intent(this, FloatWindowService.class));
			// mWindowManager = (WindowManager)
			// getApplication().getSystemService(Context.WINDOW_SERVICE);
			// sW = mWindowManager.getDefaultDisplay().getWidth();
			// sH = mWindowManager.getDefaultDisplay().getHeight();
			// initWindowParams();// 初始化WindowParams
			// LayoutInflater inflater =
			// LayoutInflater.from(getApplicationContext());
			// floatView = inflater.inflate(R.layout.bo_view, null);
			// mWindowManager.addView(floatView, wmParams);
			break;
		case R.id.main_btn_hide:
			stopService(new Intent(this, FloatWindowService.class));
			// mWindowManager.removeView(floatView);
			break;
		}

	}

	private void initWindowParams() {
		wmParams = new LayoutParams();
		wmParams.type = LayoutParams.TYPE_SYSTEM_ALERT;// 特别注意在这里设置等级为系统通话界面
		wmParams.format = PixelFormat.RGBA_8888;
		wmParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;
		wmParams.gravity = Gravity.LEFT | Gravity.TOP;
		wmParams.x = 0;
		wmParams.y = 0;
		wmParams.width = 30;
		wmParams.height = 30;
	}

	View floatView;
	WindowManager mWindowManager;
	WindowManager.LayoutParams wmParams;
	int sW;
	int sH;
}
