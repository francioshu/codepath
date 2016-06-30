package jjj.demo.floatwindow;

import jjj.demo.folatwindowdemo.R;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;

public class FloatWindowService extends Service {
	View floatView;
	WindowManager mWindowManager;
	LayoutParams wmParams;
	int sW;
	int sH;
	EatBeanMan eatBeanMan;
	LayoutParams eatParams;
	boolean hasAddEat = false;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO 自动生成的方法存根
		createFloatView();
		return super.onStartCommand(intent, flags, startId);
	}

	private void createFloatView() {
		mWindowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
		sW = mWindowManager.getDefaultDisplay().getWidth();
		sH = mWindowManager.getDefaultDisplay().getHeight();
		initWindowParams();// 初始化WindowParams
		LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
		floatView = inflater.inflate(R.layout.bo_view, null);
		mWindowManager.addView(floatView, wmParams);
		// 至此悬浮窗已经显示出来了，后面的只是监听触摸事件
		// 通过mWindowManager.updateViewLayout方法让悬浮窗随手指移动
		// 并且在移动到某个区域时显示一个吃豆人，有兴趣的可以看下
		eatBeanMan = new EatBeanMan(getApplicationContext());
		View bo = floatView.findViewById(R.id.bobobo);
		bo.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_MOVE:
					wmParams.x = (int) event.getRawX() - 15;
					wmParams.y = (int) event.getRawY() - 15 - 35;// 这里35应该换成相应状态栏的高度
					mWindowManager.updateViewLayout(floatView, wmParams);
					// 上面的是让floatView随手指移动
					// 下面的是判断要不要显示吃豆人
					if (!hasAddEat) {
						if (wmParams.x < 350 && wmParams.y > (sH / 2 - 175) && wmParams.y < (sH / 2 + 200)) {
							mWindowManager.addView(eatBeanMan, eatParams);
							hasAddEat = true;
						}
					} else {
						if (wmParams.x < 350 && wmParams.y > (sH / 2 - 175) && wmParams.y < (sH / 2 + 200)) {

						} else {
							mWindowManager.removeView(eatBeanMan);
							hasAddEat = false;
						}
					}
					break;
				case MotionEvent.ACTION_UP:
					if (hasAddEat) {
						mWindowManager.removeView(eatBeanMan);
					}
					hasAddEat = false;
					// 抬起手指时让floatView紧贴屏幕左右边缘
					wmParams.x = wmParams.x <= (sW / 2) ? 0 : sW;
					mWindowManager.updateViewLayout(floatView, wmParams);
					break;
				}
				return false;
			}
		});
		bo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Toast.makeText(getApplicationContext(), "Don't touch me!", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void initWindowParams() {
		wmParams = new LayoutParams();
		wmParams.type = LayoutParams.TYPE_SYSTEM_ALERT;// 特别注意在这里设置等级为系统警告
		wmParams.format = PixelFormat.RGBA_8888;
		wmParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;
		wmParams.gravity = Gravity.LEFT | Gravity.TOP;
		wmParams.x = 0;
		wmParams.y = 0;
		wmParams.width = 30;
		wmParams.height = 30;
		eatParams = new LayoutParams();
		eatParams.type = LayoutParams.TYPE_SYSTEM_ALERT;
		eatParams.format = PixelFormat.RGBA_8888;
		eatParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;
		eatParams.gravity = Gravity.LEFT | Gravity.TOP;
		eatParams.x = 0;
		eatParams.y = sH / 2 - 150;
		eatParams.width = 300;
		eatParams.height = 300;
	}

	@Override
	public void onDestroy() {
		if (hasAddEat) {
			mWindowManager.removeView(eatBeanMan);
		}
		mWindowManager.removeView(floatView);
		super.onDestroy();
	}
}
