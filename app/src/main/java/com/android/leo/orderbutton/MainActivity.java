package com.android.leo.orderbutton;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Author: Owen Chan
 * DATE: 2017-01-05.
 */
public class MainActivity extends AppCompatActivity {
    private RelativeLayout relativeLayout;
    private TextView countTextView;
    private ImageView shopCarImage;
    private int totalCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countTextView = (TextView) findViewById(R.id.order_count);
        shopCarImage = (ImageView) findViewById(R.id.car_image);
        relativeLayout = (RelativeLayout) findViewById(R.id.container);
        ListView listView = (ListView) findViewById(R.id.list_view);
        GoodsAdapter goodsAdapter = new GoodsAdapter(MainActivity.this);
        listView.setAdapter(goodsAdapter);

        goodsAdapter.setOnButtonClickListener(new GoodsAdapter.ButtonClick() {
            @Override
            public void buttonClick(int position, int count, OrderView orderView) {
                addShopCar(orderView, count);
            }
        });
    }

    //购物车动画
    private void addShopCar(OrderView orderView, final int variable) {
        //容器的位置
        int containerLoc[] = new int[2];
        relativeLayout.getLocationInWindow(containerLoc);
        //点击View的位置
        int startLoc[] = new int[2];
        orderView.getLocationInWindow(startLoc);
        // 结束View的位置
        int endLoc[] = new int[2];
        shopCarImage.getLocationInWindow(endLoc);

        final View viewTag = orderView.getTagView();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeLayout.addView(viewTag, params);

        float startX = startLoc[0] - containerLoc[0] + orderView.getWidth() / 2;
        float startY = startLoc[1] - containerLoc[1] + orderView.getHeight() / 2;

        float toX = endLoc[0] - containerLoc[0];
        float toY = endLoc[1] - containerLoc[1];


        Path path = new Path();
        path.moveTo(startX, startY);
        path.quadTo((startX + toX) / 2, startY, toX, toY);
        final PathMeasure mPathMeasure = new PathMeasure(path, false);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
                float[] mCurrentPosition = new float[2];
                mPathMeasure.getPosTan(value, mCurrentPosition, null);
                viewTag.setTranslationX(mCurrentPosition[0]);
                viewTag.setTranslationY(mCurrentPosition[1]);
            }
        });

        valueAnimator.setDuration(800);
        // 匀速插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                relativeLayout.removeView(viewTag);
                 totalCount += variable;
                countTextView.setText("￥" + totalCount + "");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
