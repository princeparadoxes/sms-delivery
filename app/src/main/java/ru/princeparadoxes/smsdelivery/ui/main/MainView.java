package ru.princeparadoxes.smsdelivery.ui.main;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ru.princeparadoxes.smsdelivery.R;
import ru.princeparadoxes.smsdelivery.base.ComponentFinder;
import ru.princeparadoxes.smsdelivery.base.mvp.BaseView;
import ru.princeparadoxes.smsdelivery.ui.misc.CoordinatorPageAdapter;
import ru.princeparadoxes.smsdelivery.ui.misc.CoordinatorView;
import ru.princeparadoxes.smsdelivery.ui.view.SmsDeliveryToolbar;

public class MainView extends FrameLayout implements BaseView {

    @Inject
    MainActivity.Presenter presenter;

    @InjectView(R.id.main_drawer_layout)
    protected DrawerLayout drawerLayout;

    @InjectView(R.id.coordinator_view)
    protected CoordinatorView coordinatorView;

    @InjectView(R.id.ozome_toolbar)
    protected SmsDeliveryToolbar toolbar;

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            MainComponent component = ComponentFinder.findActivityComponent(context);
            component.inject(this);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);

        toolbar.setTitleVisibility(true);
        toolbar.setTitle(R.string.main_screen_title);
        toolbar.setLogoVisibility(false);
        toolbar.setDrawerIconVisibility(true);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View view, float v) {
                // nothing;
            }

            @Override
            public void onDrawerOpened(View view) {
            }

            @Override
            public void onDrawerClosed(View view) {
                // nothing;
            }

            @Override
            public void onDrawerStateChanged(int i) {
                // nothing;
            }
        });

        final List<CoordinatorPageAdapter.Item> pages = MainScreens.getList();
        coordinatorView.addScreens(pages);
        coordinatorView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset == .0f && positionOffsetPixels == 0) {
                    final MainScreens screen = (MainScreens) pages.get(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void showLoading() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void showContent() {
    }

    @Override
    public void showError(Throwable throwable) {
    }

    public void showMainContent() {
    }

    public void openFirstScreen() {
        showMainContent();
        coordinatorView.setCurrentPage(0);
    }

    public boolean onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
            return true;
        }
        if (coordinatorView.getCurrentPagePosition() != 0){
            coordinatorView.setCurrentPage(0);
            return true;
        }
        return false;
    }
}
