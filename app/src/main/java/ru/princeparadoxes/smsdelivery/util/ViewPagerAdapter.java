package ru.princeparadoxes.smsdelivery.util;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;


public abstract class ViewPagerAdapter<T extends Object> extends PagerAdapter {
    @NonNull
    protected final Context mContext;
    @NonNull
    protected final
    LayoutInflater mLayoutInflater;
    @NonNull
    protected List<T> mList = Collections.emptyList();

    public ViewPagerAdapter(@NonNull Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void addAll(List<T> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        T item = getItem(position);
        View view = inflateView(container, item);
        if (view == null) {
            throw new IllegalStateException("newView result must not be null.");
        }
        bindView(item, position, view);
        container.addView(view, 0);
        return view;
    }

    @LayoutRes
    protected abstract int getItemLayoutId(T item);


    public abstract void bindView(T item, int position, View view);

    protected View inflateView(ViewGroup container, T item) {
        return mLayoutInflater.inflate(getItemLayoutId(item), container, false);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }


    public T getItem(int position) {
        return mList.get(position);
    }

    public int getItemPosition(Object object) {
        T item = (T) object;
        for (int i = 0; i < mList.size(); i++) {
            if(mList.get(i).equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @NonNull
    public Context getContext() {
        return mContext;
    }
}
