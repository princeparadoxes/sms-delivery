package ru.princeparadoxes.smsdelivery.ui.main.send;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import javax.inject.Inject;

import butterknife.ButterKnife;
import ru.princeparadoxes.smsdelivery.R;
import ru.princeparadoxes.smsdelivery.base.ComponentFinder;
import ru.princeparadoxes.smsdelivery.base.mvp.BaseView;
import ru.princeparadoxes.smsdelivery.ui.main.MainActivity;
import ru.princeparadoxes.smsdelivery.ui.main.MainComponent;

public class SendView extends LinearLayout implements BaseView {

    @Inject
    MainActivity.Presenter mainPresenter;
    @Inject
    SendPresenter sendPresenter;


    private final GridLayoutManager layoutManager;

    public SendView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            MainComponent component = ComponentFinder.findActivityComponent(context);
            component.inject(this);
        }

        layoutManager = new GridLayoutManager(context,
                getContext().getResources().getInteger(R.integer.column_count),
                StaggeredGridLayoutManager.VERTICAL, false);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
        sendPresenter.takeView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        sendPresenter.dropView(this);
        ButterKnife.reset(this);
        super.onDetachedFromWindow();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(Throwable throwable) {

    }
}
