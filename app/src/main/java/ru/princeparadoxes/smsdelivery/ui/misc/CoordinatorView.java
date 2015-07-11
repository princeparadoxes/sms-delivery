package ru.princeparadoxes.smsdelivery.ui.misc;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import ru.princeparadoxes.smsdelivery.ui.view.SmsDeliveryToolbar;
import ru.princeparadoxes.smsdelivery.util.view.SlidingTabLayout;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ru.princeparadoxes.smsdelivery.R;

public class CoordinatorView extends FrameLayout {

    @InjectView(R.id.ozome_toolbar)
    protected SmsDeliveryToolbar toolbar;

    @InjectView(R.id.coordinator_tabs)
    protected SlidingTabLayout mSlidingTabLayout;

    @InjectView(R.id.coordinator_pager)
    protected ViewPager mViewPager;

    private CoordinatorPageAdapter mPageAdapter;

    public CoordinatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);

        mSlidingTabLayout.setDistributeEvenly(true);
        // Setting Custom Color for the Scroll bar indicator of the Tab View
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.WHITE;
            }
        });
        // Setting the ViewPager For the SlidingTabsLayout
//        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mSlidingTabLayout.setOnPageChangeListener(listener);
    }

    public View getChildPageView(CoordinatorPageAdapter.Item item) {
        return mViewPager.findViewWithTag(String.valueOf(item.getResId()));
    }

    public void addScreens(List<CoordinatorPageAdapter.Item> pages) {
        mPageAdapter = new CoordinatorPageAdapter(getContext());
        mViewPager.setOffscreenPageLimit(pages.size());
        mViewPager.setAdapter(mPageAdapter);
        mPageAdapter.addAll(pages);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    public void setCurrentPage(int page) {
        mViewPager.setCurrentItem(page, true);
    }

    public int getCurrentPagePosition() {
        return mViewPager.getCurrentItem();
    }

    public CoordinatorPageAdapter.Item getPageItem(int position) {
        return mPageAdapter.getItem(position);
    }
}
