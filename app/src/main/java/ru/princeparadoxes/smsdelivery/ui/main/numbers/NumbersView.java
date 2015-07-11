package ru.princeparadoxes.smsdelivery.ui.main.numbers;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import ru.princeparadoxes.smsdelivery.base.ComponentFinder;
import ru.princeparadoxes.smsdelivery.base.mvp.BaseView;
import ru.princeparadoxes.smsdelivery.ui.main.MainActivity;
import ru.princeparadoxes.smsdelivery.ui.main.MainComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class NumbersView extends FrameLayout implements BaseView {

    @Inject
    MainActivity.Presenter presenter;
    @Inject
    NumbersPresenter numbersPresenter;

    public NumbersView(Context context, AttributeSet attrs) {
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
        numbersPresenter.takeView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        numbersPresenter.dropView(this);
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
