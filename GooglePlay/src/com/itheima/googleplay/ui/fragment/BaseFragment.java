package com.itheima.googleplay.ui.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.googleplay.ui.view.LoadingPage;
import com.itheima.googleplay.ui.view.LoadingPage.ResultState;
import com.itheima.googleplay.utils.UIUtils;
@SuppressWarnings("rawtypes")
public abstract class BaseFragment extends Fragment {

	private LoadingPage mLoadingPage;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
  
		mLoadingPage = new LoadingPage(UIUtils.getContext()) {

			@Override
			public View onCreateSuccessView() {
				// 注意:此处一定要调用BaseFragment的onCreateSuccessView, 否则栈溢出
				return BaseFragment.this.onCreateSuccessView();
			}

			@Override
			public ResultState onLoad() {
				return BaseFragment.this.onLoad();
			}

		};
		return mLoadingPage;
	}

	// 加载成功的布局, 必须由子类来实现    如果加载数据成功, 就回调此方法, 在主线程运行  (加载页面 先执行onLoad() 再执行onCreateSuccessView())
	public abstract View onCreateSuccessView();

	// 加载网络数据, 必须由子类来实现
	public abstract ResultState onLoad();

	// 开始加载数据
	public void loadData() {
		if (mLoadingPage != null) {
			mLoadingPage.loadData();
		}
	}

	// 对网络返回数据的合法性进行校验
	public ResultState check(Object obj) {
		if (obj != null) {
			if (obj instanceof ArrayList) {// 判断是否是集合
				ArrayList list = (ArrayList) obj;
				if (list.isEmpty()) {
					return ResultState.STATE_EMPTY;
				} else {
					return ResultState.STATE_SUCCESS;
				}
			}
		}
		return ResultState.STATE_ERROR;
	}
	
}