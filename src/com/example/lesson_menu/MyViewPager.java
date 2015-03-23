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
	int[] images = null;// ͼƬ��ԴID
	String[] titles = null;// ����

	ArrayList<ImageView> imageSource = null;// ͼƬ��Դ
	ArrayList<View> dots = null;// ��
	TextView title = null;
	ViewPager viewPager;// ������ʾͼƬ
	MyPagerAdapter adapter;// viewPager��������
	private int currPage = 0;// ��ǰ��ʾ��ҳ
	private int oldPage = 0;// ��һ����ʾ��ҳ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_pager);
		init();
	}

	public void init() {
		images = new int[] { R.drawable.a, R.drawable.b, R.drawable.c,
				R.drawable.d, R.drawable.e };
		titles = new String[] { "���ǵ�1��ͼƬ", "���ǵ�2��ͼƬ", "���ǵ�3��ͼƬ", "���ǵ�4��ͼƬ",
				"���ǵ�5��ͼƬ" };
		// ��Ҫ��ʾ��ͼƬ�ŵ�list������
		imageSource = new ArrayList<ImageView>();
		for (int i = 0; i < images.length; i++) {
			ImageView image = new ImageView(this);
			image.setBackgroundResource(images[i]);
			imageSource.add(image);
			
			final int j = i;
			image.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {

					if (j == 0) {
						Toast.makeText(MyViewPager.this, "��һ��ͼƬ��Ư����", 1000).show();
					}
					if (j == 1) {
						Toast.makeText(MyViewPager.this, "�ڶ���ͼƬ�����԰�", 1000).show();
					}
					if (j == 2) {
						Toast.makeText(MyViewPager.this, "������ͼƬ���ù�ȥ", 1000).show();
					}
					if (j == 3) {
						Toast.makeText(MyViewPager.this, "�͵�һ��ͼƬһ��", 1000).show();
					}
					if (j == 4) {
						Toast.makeText(MyViewPager.this, "�͵ڶ���ͼƬһ��", 1000).show();
					}

				}
			});
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		}

		// ��ȡ��ʾ�ĵ㣨�������·��ĵ㣬��ʾ��ǰ�ǵڼ��ţ�
		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.dot1));
		dots.add(findViewById(R.id.dot2));
		dots.add(findViewById(R.id.dot3));
		dots.add(findViewById(R.id.dot4));
		dots.add(findViewById(R.id.dot5));

		// ͼƬ�ı���
		title = (TextView) findViewById(R.id.title);
		title.setText(titles[0]);

		// ��ʾͼƬ��VIew
		viewPager = (ViewPager) findViewById(R.id.vp);
		// ΪviewPager����������
		adapter = new MyPagerAdapter();
		viewPager.setAdapter(adapter);
		// ΪviewPager��Ӽ��������ü��������ڵ�ͼƬ�任ʱ������͵�Ҳ���ű仯
		MyPageChangeListener listener = new MyPageChangeListener();
		viewPager.setOnPageChangeListener(listener);

		// ������ʱ����ÿ��2���Զ�������һ�ţ�ͨ�������߳�ʵ�֣�����Timer���ƣ���ʹ��Timer���棩
		ScheduledExecutorService scheduled = Executors
				.newSingleThreadScheduledExecutor();
		// ����һ���̣߳����߳�����֪ͨUI�̱߳任ͼƬ
		ViewPagerTask pagerTask = new ViewPagerTask();
		scheduled.scheduleAtFixedRate(pagerTask, 2, 2, TimeUnit.SECONDS);
	}

	// ViewPagerÿ�ν�����������ͼƬ�������ڷ�ֹ�����ڴ������
	private class MyPagerAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			System.out.println("getCount");
			return images.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// �жϽ�Ҫ��ʾ��ͼƬ�Ƿ��������ʾ��ͼƬ��ͬһ��
			// arg0Ϊ��ǰ��ʾ��ͼƬ��arg1�ǽ�Ҫ��ʾ��ͼƬ
			System.out.println("isViewFromObject");
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			System.out.println("destroyItem==" + position);
			// ���ٸ�ͼƬ
			container.removeView(imageSource.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// ��ʼ����Ҫ��ʾ��ͼƬ������ͼƬ���뵽container�У���viewPager��
			container.addView(imageSource.get(position));
			System.out.println("instantiateItem===" + position + "===="
					+ container.getChildCount());
			return imageSource.get(position);
		}
	}

	// ����ViewPager�ı仯
	private class MyPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {
			// ����ʾ��ͼƬ�����仯֮��
			// ���ñ���
			title.setText(titles[position]);
			// �ı���״̬
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			dots.get(oldPage).setBackgroundResource(R.drawable.dot_normal);
			// ��¼��ҳ��
			oldPage = position;
			currPage = position;
		}
	}

	private class ViewPagerTask implements Runnable {
		@Override
		public void run() {
			// �ı䵱ǰҳ���ֵ
			currPage = (currPage + 1) % images.length;
			// ������Ϣ��UI�߳�
			handler.sendEmptyMessage(0);
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// ���յ���Ϣ�󣬸���ҳ��
			viewPager.setCurrentItem(currPage);
		};
	};
}
