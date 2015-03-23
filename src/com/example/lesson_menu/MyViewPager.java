package com.example.lesson_menu;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyViewPager extends Activity {
	int[] images = null;// 图片资源ID
	String[] titles = null;// 标题

	ArrayList<ImageView> imageSource = null;// 图片资源
	ArrayList<View> dots = null;// 点
	TextView title = null;
	ViewPager viewPager;// 用于显示图片
	MyPagerAdapter adapter;// viewPager的适配器
	private int currPage = 0;// 当前显示的页
	private int oldPage = 0;// 上一次显示的页

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_pager);
		init();
	}

	public void init() {
		images = new int[] { R.drawable.a, R.drawable.b, R.drawable.c,
				R.drawable.d, R.drawable.e };
		titles = new String[] { "这是第1张图片", "这是第2张图片", "这是第3张图片", "这是第4张图片",
				"这是第5张图片" };
		// 将要显示的图片放到list集合中
		imageSource = new ArrayList<ImageView>();
		for (int i = 0; i < images.length; i++) {
			ImageView image = new ImageView(this);
			image.setBackgroundResource(images[i]);
			imageSource.add(image);
			
			final int j = i;
			image.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {

					if (j == 0) {
						Toast.makeText(MyViewPager.this, "第一张图片好漂亮啊", 1000).show();
					}
					if (j == 1) {
						Toast.makeText(MyViewPager.this, "第二张图片还可以吧", 1000).show();
					}
					if (j == 2) {
						Toast.makeText(MyViewPager.this, "第三张图片看得过去", 1000).show();
					}
					if (j == 3) {
						Toast.makeText(MyViewPager.this, "和第一张图片一样", 1000).show();
					}
					if (j == 4) {
						Toast.makeText(MyViewPager.this, "和第二张图片一样", 1000).show();
					}

				}
			});
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		}

		// 获取显示的点（即文字下方的点，表示当前是第几张）
		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.dot1));
		dots.add(findViewById(R.id.dot2));
		dots.add(findViewById(R.id.dot3));
		dots.add(findViewById(R.id.dot4));
		dots.add(findViewById(R.id.dot5));

		// 图片的标题
		title = (TextView) findViewById(R.id.title);
		title.setText(titles[0]);

		// 显示图片的VIew
		viewPager = (ViewPager) findViewById(R.id.vp);
		// 为viewPager设置适配器
		adapter = new MyPagerAdapter();
		viewPager.setAdapter(adapter);
		// 为viewPager添加监听器，该监听器用于当图片变换时，标题和点也跟着变化
		MyPageChangeListener listener = new MyPageChangeListener();
		viewPager.setOnPageChangeListener(listener);

		// 开启定时器，每隔2秒自动播放下一张（通过调用线程实现）（与Timer类似，可使用Timer代替）
		ScheduledExecutorService scheduled = Executors
				.newSingleThreadScheduledExecutor();
		// 设置一个线程，该线程用于通知UI线程变换图片
		ViewPagerTask pagerTask = new ViewPagerTask();
		scheduled.scheduleAtFixedRate(pagerTask, 2, 2, TimeUnit.SECONDS);
	}

	// ViewPager每次仅最多加载三张图片（有利于防止发送内存溢出）
	private class MyPagerAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			System.out.println("getCount");
			return images.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// 判断将要显示的图片是否和现在显示的图片是同一个
			// arg0为当前显示的图片，arg1是将要显示的图片
			System.out.println("isViewFromObject");
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			System.out.println("destroyItem==" + position);
			// 销毁该图片
			container.removeView(imageSource.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// 初始化将要显示的图片，将该图片加入到container中，即viewPager中
			container.addView(imageSource.get(position));
			System.out.println("instantiateItem===" + position + "===="
					+ container.getChildCount());
			return imageSource.get(position);
		}
	}

	// 监听ViewPager的变化
	private class MyPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {
			// 当显示的图片发生变化之后
			// 设置标题
			title.setText(titles[position]);
			// 改变点的状态
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			dots.get(oldPage).setBackgroundResource(R.drawable.dot_normal);
			// 记录的页面
			oldPage = position;
			currPage = position;
		}
	}

	private class ViewPagerTask implements Runnable {
		@Override
		public void run() {
			// 改变当前页面的值
			currPage = (currPage + 1) % images.length;
			// 发送消息给UI线程
			handler.sendEmptyMessage(0);
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// 接收到消息后，更新页面
			viewPager.setCurrentItem(currPage);
		};
	};
}
