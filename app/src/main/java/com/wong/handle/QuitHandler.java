package com.wong.handle;



import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;

/** 退出控制器 */
public class QuitHandler {
	/**
	 * 退出应用
	 */
	public  void ExitApp(final Activity activity) {
		Builder builder = new Builder(activity);
		builder.setTitle("退出");
		builder.setMessage("确定退出本软件？");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				activity.finish();
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		});
		builder.setNegativeButton("取消", null);
		builder.show();
	}
}
