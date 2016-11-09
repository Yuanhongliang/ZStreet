package com.xiaoyuan.zstreet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.ksfc.framework.lib.widget.image.BigPicActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.xiaoyuan.zstreet.adapter.CommentAdapter;
import com.xiaoyuan.zstreet.adapter.DetailVpAdapter;
import com.xiaoyuan.zstreet.application.MyApplication;
import com.xiaoyuan.zstreet.base.BaseActivity;
import com.xiaoyuan.zstreet.base.BaseInterface;
import com.xiaoyuan.zstreet.bean.CartBean;
import com.xiaoyuan.zstreet.bean.CommentBean;
import com.xiaoyuan.zstreet.bean.GoodsBean;
import com.xiaoyuan.zstreet.bean.UserBean;
import com.xiaoyuan.zstreet.utils.DialogUtils;
import com.xiaoyuan.zstreet.utils.ImageLoaderUtils;

/**
 * 商品详情的展示界面
 * 
 * @author Administrator
 * 
 */
public class GoodsDetailActivity extends BaseActivity implements BaseInterface {
	// 当前浏览的商品
	private GoodsBean gb;
	// 商品的展示图片
	@ViewInject(R.id.act_goodsdet_vp)
	private ViewPager vp;
	// 商品名称
	@ViewInject(R.id.act_goodsdet_name)
	private TextView name;
	// 价格
	@ViewInject(R.id.act_goodsdet_price)
	private TextView price;
	// 收藏按钮
	@ViewInject(R.id.act_goodsdet_fav)
	private ImageView fav;
	// 显示评论的ListView
	@ViewInject(R.id.act_det_comment)
	private ListView lv;

	// 图片底部的滑动条
	private View[] bars = new View[5];
	private int[] barIds = { R.id.act_goodsdet_view1, R.id.act_goodsdet_view2,
			R.id.act_goodsdet_view3, R.id.act_goodsdet_view4,
			R.id.act_goodsdet_view5 };

	// 全部的颜色和尺码控件
	@ViewInject(R.id.act_goodsdet_red)
	private TextView red;
	@ViewInject(R.id.act_goodsdet_green)
	private TextView green;
	@ViewInject(R.id.act_goodsdet_blue)
	private TextView blue;
	@ViewInject(R.id.act_goodsdet_L)
	private TextView sizeL;
	@ViewInject(R.id.act_goodsdet_XL)
	private TextView sizeXL;
	@ViewInject(R.id.act_goodsdet_XXL)
	private TextView sizeXXL;
	// 输入的数量
	@ViewInject(R.id.act_goodsdet_num)
	private EditText num;
	// 当前的用户对象
	private UserBean ub;
	// 商品的图片展示
	private List<ImageView> imgs;
	// 选择的颜色
	private int colorInt = 1;
	// 选择的尺码
	private int sizeInt = 1;

	private int position = 0;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		initViews();
		initDatas();
		initOper();
	}

	@Override
	public void initViews() {
		setContentView(R.layout.act_goodsdetail);
		ViewUtils.inject(getAct());
		for (int i = 0; i < 5; i++) {
			bars[i] = findViewById(barIds[i]);
		}
	}

	@Override
	public void initDatas() {
		// 从缓存中拿出数据
		gb = (GoodsBean) MyApplication.getData("goodDetail", true);
		// 从本地取出用户对象
		ub = ((MyApplication) getApplication()).getUser();
		// 初始化图片控件
		imgs = new ArrayList<ImageView>();
		final List<String> urls = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			ImageView img = new ImageView(getAct());
			// 设置填充模式CENTER_CROP
			img.setScaleType(ScaleType.CENTER_CROP);
			imgs.add(img);
			urls.add(gb.getGoodsImgs().get(i).getFileUrl(getAct()));
			ImageLoaderUtils.loadBigImg(img, gb.getGoodsImgs().get(i)
					.getFileUrl(getAct()));
			img.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					BigPicActivity.open(getAct(), position, urls);
				}
			});
		}
		getComments();

	}

	/**
	 * 获取评论信息
	 */
	private void getComments() {
		DialogUtils.showLoadingDialog(getAct());
		BmobQuery<CommentBean> query = new BmobQuery<CommentBean>();
		query.addWhereEqualTo("goodsId", gb.getObjectId());
		query.order("-createdAt");
		query.findObjects(getAct(), new FindListener<CommentBean>() {

			@Override
			public void onSuccess(List<CommentBean> arg0) {
				DialogUtils.dismissDialog();
				if (arg0 != null && arg0.size() > 0) {
					lv.setAdapter(new CommentAdapter(getAct(), arg0));
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				toastShort("评论信息获取失败" + arg1);
			}
		});

	}

	@Override
	public void initOper() {
		// 设置商品名
		name.setText(gb.getGoodsName());
		// 设置商品价格
		price.setText("¥" + gb.getPrice());
		// 商品图片
		vp.setAdapter(new DetailVpAdapter(imgs));

		// 设置是否收藏过
		if (ub != null && ub.getCollectIds().contains(gb.getObjectId())) {
			fav.setImageResource(R.drawable.hearton);
		} else {
			fav.setImageResource(R.drawable.heartoff);
		}

		// 设置ViewPager的滑动监听
		vp.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// 当商品滑动时改变底部的滑动条颜色
				for (int i = 0; i < 5; i++) {
					position = i;
					if (i == arg0) {
						bars[i].setBackgroundColor(Color.parseColor("#ff4400"));
					} else {
						bars[i].setBackgroundColor(Color.parseColor("#e5e5e5"));
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	// 返回上一界面
	@OnClick(R.id.act_goodsdet_back)
	public void onBackClick(View v) {
		finish();
	}

	// 选择颜色和尺码的操作
	@OnClick({ R.id.act_goodsdet_blue, R.id.act_goodsdet_green,
			R.id.act_goodsdet_red, R.id.act_goodsdet_L, R.id.act_goodsdet_XL,
			R.id.act_goodsdet_XXL })
	public void onChooseClick(View v) {
		switch (v.getId()) {
		case R.id.act_goodsdet_blue:
			colorInt = 3;
			updateColor(3);
			break;
		case R.id.act_goodsdet_green:
			colorInt = 2;
			updateColor(2);
			break;
		case R.id.act_goodsdet_red:
			colorInt = 1;
			updateColor(1);
			break;
		case R.id.act_goodsdet_L:
			sizeInt = 1;
			updateSize(1);
			break;
		case R.id.act_goodsdet_XL:
			sizeInt = 2;
			updateSize(2);
			break;
		case R.id.act_goodsdet_XXL:
			sizeInt = 3;
			updateSize(3);
			break;

		default:
			break;
		}
	}

	/**
	 * 更新尺码选择的视图
	 * 
	 * @param i
	 */
	private void updateSize(int i) {
		if (i == 1) {
			sizeL.setBackgroundResource(R.drawable.red_stroke_shape);
			sizeXL.setBackgroundColor(Color.parseColor("#efefef"));
			sizeXXL.setBackgroundColor(Color.parseColor("#efefef"));
		} else if (i == 2) {
			sizeXL.setBackgroundResource(R.drawable.red_stroke_shape);
			sizeL.setBackgroundColor(Color.parseColor("#efefef"));
			sizeXXL.setBackgroundColor(Color.parseColor("#efefef"));
		} else if (i == 3) {
			sizeXXL.setBackgroundResource(R.drawable.red_stroke_shape);
			sizeL.setBackgroundColor(Color.parseColor("#efefef"));
			sizeXL.setBackgroundColor(Color.parseColor("#efefef"));
		}
	}

	/**
	 * 更新颜色选择的视图
	 * 
	 * @param i
	 */
	private void updateColor(int i) {
		if (i == 1) {
			red.setBackgroundResource(R.drawable.red_stroke_shape);
			blue.setBackgroundColor(Color.parseColor("#efefef"));
			green.setBackgroundColor(Color.parseColor("#efefef"));
		} else if (i == 2) {
			green.setBackgroundResource(R.drawable.red_stroke_shape);
			blue.setBackgroundColor(Color.parseColor("#efefef"));
			red.setBackgroundColor(Color.parseColor("#efefef"));
		} else {
			blue.setBackgroundResource(R.drawable.red_stroke_shape);
			red.setBackgroundColor(Color.parseColor("#efefef"));
			green.setBackgroundColor(Color.parseColor("#efefef"));
		}
	}

	// 收藏按钮
	@OnClick(R.id.act_goodsdet_fav)
	public void onFavClick(View v) {
		UserBean uba = BmobUser.getCurrentUser(getAct(), UserBean.class);
		if (uba == null) {
			toastShort("您还没有登录哦，快去登录吧");
			return;
		}
		// 如果当前登录用户已经收藏过了
		if (ub.getCollectIds().contains(gb.getObjectId())) {
			ub.getCollectIds().remove(gb.getObjectId());
			UserBean newUB = new UserBean();
			newUB.removeAll("collectIds", Arrays.asList(gb.getObjectId()));
			newUB.update(getAct(), ub.getObjectId(), new UpdateListener() {

				@Override
				public void onSuccess() {
					toastShort("取消收藏");
					fav.setImageResource(R.drawable.heartoff);
				}

				@Override
				public void onFailure(int arg0, String arg1) {
					toastShort("取消失败" + arg0 + "aaaaa" + arg1);
				}
			});

		} else {
			// 当前用户没有收藏过
			ub.getCollectIds().add(gb.getObjectId());
			UserBean newUB = new UserBean();
			newUB.addUnique("collectIds", gb.getObjectId());
			newUB.update(getAct(), ub.getObjectId(), new UpdateListener() {

				@Override
				public void onSuccess() {
					toastShort("收藏成功");
					fav.setImageResource(R.drawable.hearton);
				}

				@Override
				public void onFailure(int arg0, String arg1) {
					toastShort("收藏失败" + arg0 + arg1);
				}
			});
		}
	}

	// 购买按钮
	@OnClick(R.id.act_goodsdet_buy)
	public void onBuyClick(View v) {
		UserBean ub = BmobUser.getCurrentUser(getAct(), UserBean.class);
		// 如果用户为空就跳转到登录界面
		if (ub == null) {
			startActivity(new Intent(getAct(), LoginActivity.class));
			return;
		}
		CartBean cb = getBean();
		MyApplication.putData("CartBean", cb);
		startActivity(new Intent(getAct(), PayActivity.class));
	}

	// 添加到购物车
	@OnClick(R.id.act_goodsdet_jia)
	public void onCartClick(View v) {
		UserBean ub = BmobUser.getCurrentUser(getAct(), UserBean.class);
		// 如果用户为空就跳转到登录界面
		if (ub == null) {
			startActivity(new Intent(getAct(), LoginActivity.class));
			return;
		}
		CartBean cb = getBean();
		cb.save(getAct(), new SaveListener() {

			@Override
			public void onSuccess() {
				toastShort("加入购物车成功");
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				toastShort("加入购物车失败" + arg1);
			}
		});
	}

	/**
	 * 组装购物车Bean对象
	 * 
	 * @return 组装好的购物车
	 */
	private CartBean getBean() {
		CartBean cb = new CartBean();
		cb.setGoodsId(gb.getObjectId());
		cb.setGoodsName(gb.getGoodsName());
		cb.setGoodsPrice(gb.getPrice());
		cb.setUserId(ub.getObjectId());
		cb.setImgUrl(gb.getGoodsImgs().get(0).getFileUrl(getAct()));
		cb.setGoodsSize(sizeInt == 1 ? "L" : (sizeInt == 2 ? "XL" : "XXL"));
		cb.setGoodsColor(colorInt == 1 ? "红色" : (colorInt == 2 ? "绿色" : "蓝色"));
		int i = Integer.parseInt(num.getText().toString().trim());
		// 如果选择数量小于1改为默认1
		if (i <= 0) {
			cb.setGoodsNum(1);
		} else {
			cb.setGoodsNum(i);
		}
		return cb;
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// 再从本地拿一次对象保证数据准确
		ub = ((MyApplication) getApplication()).getUser();
	}

}
