package com.itheima.googleplay.ui.fragment;

import java.util.ArrayList;

import android.view.View;

import com.itheima.googleplay.domain.AppInfo;
import com.itheima.googleplay.http.protocol.AppProtocol;
import com.itheima.googleplay.ui.adapter.MyBaseAdapter;
import com.itheima.googleplay.ui.holder.AppHolder;
import com.itheima.googleplay.ui.holder.BaseHolder;
import com.itheima.googleplay.ui.view.LoadingPage.ResultState;
import com.itheima.googleplay.ui.view.MyListView;
import com.itheima.googleplay.utils.UIUtils;

/**
 * 应用
 * @author liupeng
 * @date 2016-10-27
 */
public class AppFragment extends BaseFragment {

	private ArrayList<AppInfo> data;

	// 只有成功才走此方法
	@Override
	public View onCreateSuccessView() {
		MyListView view = new MyListView(UIUtils.getContext());
		view.setAdapter(new AppAdapter(data));
		return view;
	}

	@Override
	public ResultState onLoad() {
		AppProtocol protocol = new AppProtocol();
		data = protocol.getData(0);
		return check(data);
	}

	class AppAdapter extends MyBaseAdapter<AppInfo> {

		public AppAdapter(ArrayList<AppInfo> data) {
			super(data);
		}

		@Override
		public BaseHolder<AppInfo> getHolder(int position) {
			return new AppHolder();
		}

		@Override
		public ArrayList<AppInfo> onLoadMore() {
			AppProtocol protocol = new AppProtocol();
			ArrayList<AppInfo> moreData = protocol.getData(getListSize());
			return moreData;
		}

	}

}