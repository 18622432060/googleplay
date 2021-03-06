package com.itheima.googleplay.ui.holder;

import java.util.ArrayList;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;

import com.itheima.googleplay.R;
import com.itheima.googleplay.domain.AppInfo;
import com.itheima.googleplay.domain.AppInfo.SafeInfo;
import com.itheima.googleplay.http.HttpHelper;
import com.itheima.googleplay.utils.BitmapHelper;
import com.itheima.googleplay.utils.LogUtils;
import com.itheima.googleplay.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

/**
 * 应用详情页-安全模块
 * 
 * @author liupeng
 * @date 2016-11-1
 */
public class DetailSafeHolder extends BaseHolder<AppInfo> {

	@InjectViews({ R.id.iv_safe1, R.id.iv_safe2, R.id.iv_safe3, R.id.iv_safe4 })
	ImageView[] mSafeIcons;// 安全标识图片
	@InjectViews({ R.id.iv_des1, R.id.iv_des2, R.id.iv_des3, R.id.iv_des4 })
	ImageView[] mDesIcons;// 安全描述图片
	@InjectViews({ R.id.tv_des1, R.id.tv_des2, R.id.tv_des3, R.id.tv_des4 })
	TextView[] mSafeDes;// 安全描述文字
	@InjectViews({ R.id.ll_des1, R.id.ll_des2, R.id.ll_des3, R.id.ll_des4 })
	LinearLayout[] mSafeDesBar;// 安全描述条目(图片+文字)
	@InjectView(R.id.rl_des_root)
	RelativeLayout rlDesRoot;
	@InjectView(R.id.ll_des_root)
	LinearLayout llDesRoot;
	@InjectView(R.id.iv_arrow)
	ImageView ivArrow;

	private BitmapUtils mBitmapUtils;
	private int mDesHeight;
	private LinearLayout.LayoutParams mParams;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.layout_detail_safeinfo);
		ButterKnife.inject(this, view);
		rlDesRoot.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toggle();
			}
		});
		mBitmapUtils = BitmapHelper.getBitmapUtils();
		return view;
	}

	private boolean isOpen = false;// 标记安全描述开关状态,默认关

	// 打开或者关闭安全描述信息
	// 导入jar包: nineoldandroids-2.4.0.jar
	protected void toggle() {
		ValueAnimator animator = null;
		if (isOpen) {
			// 关闭
			isOpen = false;
			// 属性动画
			animator = ValueAnimator.ofInt(mDesHeight, 0);// 从某个值变化到某个值
		} else {
			// 开启
			isOpen = true;
			// 属性动画
			animator = ValueAnimator.ofInt(0, mDesHeight);
		}

		// 动画更新的监听
		animator.addUpdateListener(new AnimatorUpdateListener() {
			// 启动动画之后, 会不断回调此方法来获取最新的值
			@Override
			public void onAnimationUpdate(ValueAnimator animator) {
				// 获取最新的高度值
				Integer height = (Integer) animator.getAnimatedValue();
				LogUtils.v("最新高度:" + height);
				// 重新修改布局高度
				mParams.height = height;
				llDesRoot.setLayoutParams(mParams);
			}
		});

		animator.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animator) {

			}

			@Override
			public void onAnimationRepeat(Animator animator) {

			}

			@Override
			public void onAnimationEnd(Animator animator) {
				// 动画结束的事件
				// 更新小箭头的方向
				if (isOpen) {
					ivArrow.setImageResource(R.drawable.arrow_up);
				} else {
					ivArrow.setImageResource(R.drawable.arrow_down);
				}
			}

			@Override
			public void onAnimationCancel(Animator animator) {

			}
		});
		animator.setDuration(200);// 动画时间
		animator.start();// 启动动画
	}

	@Override
	public void refreshView(AppInfo data) {
		ArrayList<SafeInfo> safe = data.safe;
		for (int i = 0; i < 4; i++) {
			if (i < safe.size()) {
				// 安全标识图片
				SafeInfo safeInfo = safe.get(i);
				mBitmapUtils.display(mSafeIcons[i], HttpHelper.URL + "image?name=" + safeInfo.safeUrl);
				// 安全描述文字
				mSafeDes[i].setText(safeInfo.safeDes);
				// 安全描述图片
				mBitmapUtils.display(mDesIcons[i], HttpHelper.URL + "image?name=" + safeInfo.safeDesUrl);
			} else {
				// 剩下不应该显示的图片
				mSafeIcons[i].setVisibility(View.GONE);
				// 隐藏多余的描述条目
				mSafeDesBar[i].setVisibility(View.GONE);
			}
		}
		// 获取安全描述的完整高度
		llDesRoot.measure(0, 0);
		mDesHeight = llDesRoot.getMeasuredHeight();
		LogUtils.v("安全描述高度:" + mDesHeight);
		// 修改安全描述布局高度为0,达到隐藏效果
		mParams = (LinearLayout.LayoutParams) llDesRoot.getLayoutParams();
		mParams.height = 0;
		llDesRoot.setLayoutParams(mParams);
	}

}