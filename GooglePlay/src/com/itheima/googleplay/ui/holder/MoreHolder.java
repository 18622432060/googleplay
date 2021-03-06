package com.itheima.googleplay.ui.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itheima.googleplay.R;
import com.itheima.googleplay.utils.UIUtils;

/**
 * @author liupeng
 */
public class MoreHolder extends BaseHolder<Integer> {

	// 加载更多的几种状态
	public static final int STATE_MORE_MORE = 1;// 1. 可以加载更多
	public static final int STATE_MORE_ERROR = 2;// 2. 加载更多失败
	public static final int STATE_MORE_NONE = 3; // 3. 没有更多数据

	@InjectView(R.id.ll_load_more)
	LinearLayout llLoadMore;
	@InjectView(R.id.tv_load_error)
	TextView tvLoadError;

	public MoreHolder(boolean hasMore) {
		// 如果有更多数据,状态为STATE_MORE_MORE,否则为STATE_MORE_NONE,将此状态传递给父类的data,父类会同时刷新界面
		setData(hasMore ? STATE_MORE_MORE : STATE_MORE_NONE);// setData结束后一定会调refreshView
	}

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.list_item_more);
		ButterKnife.inject(this, view);
		return view;
	}

	@Override
	public void refreshView(Integer data) {
		switch (data) {
			case STATE_MORE_MORE:
				// 显示加载更多
				llLoadMore.setVisibility(View.VISIBLE);
				tvLoadError.setVisibility(View.GONE);
				break;
			case STATE_MORE_NONE:
				// 隐藏加载更多
				llLoadMore.setVisibility(View.GONE);
				tvLoadError.setVisibility(View.GONE);
				break;
			case STATE_MORE_ERROR:
				// 显示加载失败的布局
				llLoadMore.setVisibility(View.GONE);
				tvLoadError.setVisibility(View.VISIBLE);
				break;
			default:
				break;
		}
	}

}