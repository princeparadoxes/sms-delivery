package ru.princeparadoxes.smsdelivery.ui.main.archive;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import ru.princeparadoxes.smsdelivery.base.ComponentFinder;
import ru.princeparadoxes.smsdelivery.base.mvp.BaseView;
import ru.princeparadoxes.smsdelivery.ui.main.MainActivity;
import ru.princeparadoxes.smsdelivery.ui.main.MainComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class ArchiveView extends FrameLayout implements BaseView {

    @Inject
    MainActivity.Presenter presenter;
    @Inject
    ArchivePresenter myPresenter;

    public ArchiveView(Context context, AttributeSet attrs) {
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
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        myPresenter.takeView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        myPresenter.dropView(this);
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
