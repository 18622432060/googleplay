package com.itheima.googleplay.ui.activity;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itheima.googleplay.R;
import com.itheima.googleplay.ui.fragment.BaseFragment;
import com.itheima.googleplay.ui.fragment.FragmentFactory;
import com.itheima.googleplay.ui.view.PagerTab;
import com.itheima.googleplay.utils.UIUtils;

/**
 * 当项目和appcompat关联在一起时, 就必须在清单文件中设置Theme.AppCompat的主题, 否则崩溃
 * android:theme="@style/Theme.AppCompat.Light"
 * 
 * @author liupeng
 * @date 2016-10-27
 */
public class MainActivity extends BaseActivity {
	
    @InjectView(R.id.pager_tab)
	PagerTab mPagerTab;
    @InjectView(R.id.viewpager)
	ViewPager mViewPager;
    
	private MyAdapter mAdapter;
	private ActionBarDrawerToggle toggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);

		mAdapter = new MyAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mAdapter);

		mPagerTab.setViewPager(mViewPager);// 将指针和viewpager绑定在一起

		mPagerTab.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				BaseFragment fragment = FragmentFactory.createFragment(position);
				fragment.loadData();// 开始加载数据
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels) {
			
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			
			}
		});

		initActionbar();
	}

	// 初始化actionbar
	private void initActionbar() {
		ActionBar actionbar = getSupportActionBar();

		actionbar.setHomeButtonEnabled(true);// home处可以点击
		actionbar.setDisplayHomeAsUpEnabled(true);// 显示左上角返回键,当和侧边栏结合时展示三个杠图片

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);

		// 初始化抽屉开关
		toggle = new ActionBarDrawerToggle(this, drawer,R.drawable.ic_drawer_am, R.string.drawer_open,R.string.drawer_close);
		toggle.syncState();// 同步状态, 将DrawerLayout和开关关联在一起
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				// 切换抽屉
				toggle.onOptionsItemSelected(item);
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * FragmentPagerAdapter是PagerAdapter的子类, 如果viewpager的页面是fragment的话,就继承此类
	 */
	class MyAdapter extends FragmentPagerAdapter {

		private String[] mTabNames;

		public MyAdapter(FragmentManager fm) {
			super(fm);
			mTabNames = UIUtils.getStringArray(R.array.tab_names);// 加载页面标题数组
		}

		// 返回页签标题
		@Override
		public CharSequence getPageTitle(int position) {
			return mTabNames[position];
		}

		// 返回当前页面位置的fragment对象
		@Override
		public Fragment getItem(int position) {
			BaseFragment fragment = FragmentFactory.createFragment(position);
			return fragment;
		}

		// fragment数量
		@Override
		public int getCount() {
			return mTabNames.length;
		}

	}

}