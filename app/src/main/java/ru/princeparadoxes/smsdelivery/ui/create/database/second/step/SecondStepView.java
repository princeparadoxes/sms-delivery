package ru.princeparadoxes.smsdelivery.ui.create.database.second.step;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ru.princeparadoxes.smsdelivery.R;
import ru.princeparadoxes.smsdelivery.base.ComponentFinder;
import ru.princeparadoxes.smsdelivery.base.mvp.BaseView;
import ru.princeparadoxes.smsdelivery.ui.create.database.CreateDatabaseActivity;
import ru.princeparadoxes.smsdelivery.ui.create.database.CreateDatabaseComponent;

public class SecondStepView extends FrameLayout implements BaseView {

    @Inject
    SecondStepPresenter secondStepPresenter;

    @Inject
    CreateDatabaseActivity.Presenter presenter;

    @InjectView(R.id.create_database_name)
    TextView name;

    @OnClick(R.id.create_database_first_scene_button)
    protected void toFirstScene() {
        presenter.toFirstScene();
    }

    @OnClick(R.id.create_database_finish_button)
    protected void finish() {
        presenter.finish();
    }

    public SecondStepView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            CreateDatabaseComponent component = ComponentFinder.findActivityComponent(context);
            component.inject(this);
        }

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ButterKnife.inject(this);
        secondStepPresenter.takeView(this);
        name.setText(presenter.getName());
    }

    @Override
    protected void onDetachedFromWindow() {
        secondStepPresenter.dropView(this);
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
