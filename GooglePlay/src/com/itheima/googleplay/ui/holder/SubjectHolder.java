package com.itheima.googleplay.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itheima.googleplay.R;
import com.itheima.googleplay.domain.SubjectInfo;
import com.itheima.googleplay.http.HttpHelper;
import com.itheima.googleplay.utils.BitmapHelper;
import com.itheima.googleplay.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * 专题holder
 * 
 * @author liupeng
 * @date 2016-10-30
 */
public class SubjectHolder extends BaseHolder<SubjectInfo> {

	@InjectView(R.id.iv_pic)
	ImageView ivPic;
	@InjectView(R.id.tv_title)
	TextView tvTitle;

	private BitmapUtils mBitmapUtils;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.list_item_subject);
		ButterKnife.inject(this, view);
		mBitmapUtils = BitmapHelper.getBitmapUtils();
		return view;
	}

	@Override
	public void refreshView(SubjectInfo data) {
		tvTitle.setText(data.des);
		mBitmapUtils.display(ivPic, HttpHelper.URL + "image?name=" + data.url);
	}

}