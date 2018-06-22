package com.baichang.android.library.kotlin.widget.photoView;

import android.view.View;

public class Compat {
	
	private static final int SIXTY_FPS_INTERVAL = 1000 / 60;
	
	public static void postOnAnimation(View view, Runnable runnable) {
		SDK16.postOnAnimation(view, runnable);
	}

}
