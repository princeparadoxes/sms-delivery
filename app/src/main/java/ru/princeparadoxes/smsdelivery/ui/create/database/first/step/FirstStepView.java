package ru.princeparadoxes.smsdelivery.ui.create.database.first.step;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.FrameLayout;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import ru.princeparadoxes.smsdelivery.R;
import ru.princeparadoxes.smsdelivery.base.ComponentFinder;
import ru.princeparadoxes.smsdelivery.base.mvp.BaseView;
import ru.princeparadoxes.smsdelivery.ui.create.database.CreateDatabaseActivity;
import ru.princeparadoxes.smsdelivery.ui.create.database.CreateDatabaseComponent;

public class FirstStepView extends FrameLayout implements BaseView {

    @Inject
    FirstStepPresenter firstStepPresenter;

    @Inject
    CreateDatabaseActivity.Presenter presenter;

    @InjectView(R.id.create_database_name)
    protected EditText name;

    @Optional
    @OnClick(R.id.create_database_first_scene_button)
    protected void toNextScene() {
        presenter.toSecondScene(name.getText().toString());
    }


    public FirstStepView(Context context, AttributeSet attrs) {
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
        firstStepPresenter.takeView(this);
        name.setText(presenter.getName());
    }

    @Override
    protected void onDetachedFromWindow() {
        firstStepPresenter.dropView(this);
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
