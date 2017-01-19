package com.android.leo.orderbutton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import rx.functions.Action2;

/**
 * Author: Owen Chan
 * DATE: 2017-01-18.
 */

public class GoodsAdapter extends BaseAdapter {
    private Context mContext;
    private ButtonClick mButtonClick;

    public GoodsAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
           final OrderView orderView = (OrderView) convertView.findViewById(R.id.order_view);
            orderView.setCallback(new Action2<Integer, Integer>() {
                @Override
                public void call(Integer integer, Integer integer2) {
                    mButtonClick.buttonClick(position, integer2, orderView);
                }
            });
            return convertView;
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.goods_item_view, null);
            final OrderView orderView = (OrderView) view.findViewById(R.id.order_view);
            orderView.setCallback(new Action2<Integer, Integer>() {
                @Override
                public void call(Integer integer, Integer integer2) {
                    mButtonClick.buttonClick(position, integer2, orderView);
                }
            });
            return view;
        }
    }

    public void setOnButtonClickListener(ButtonClick buttonClick) {
        this.mButtonClick = buttonClick;
    }

    interface ButtonClick {
        void buttonClick(int position, int count, OrderView orderView);
    }
}
