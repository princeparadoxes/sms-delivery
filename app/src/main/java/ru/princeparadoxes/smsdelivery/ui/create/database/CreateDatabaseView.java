package ru.princeparadoxes.smsdelivery.ui.create.database;

import android.content.Context;
import android.transitions.everywhere.ChangeBounds;
import android.transitions.everywhere.Scene;
import android.transitions.everywhere.Slide;
import android.transitions.everywhere.TransitionManager;
import android.transitions.everywhere.TransitionSet;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ru.princeparadoxes.smsdelivery.R;
import ru.princeparadoxes.smsdelivery.base.ComponentFinder;
import ru.princeparadoxes.smsdelivery.base.mvp.BaseView;

public class CreateDatabaseView extends LinearLayout implements BaseView {

    @Inject
    CreateDatabaseActivity.Presenter presenter;

    @InjectView(R.id.create_database_layout)
    protected ViewGroup rootScene;

    private Scene firstStepScene;
    private Scene secondStepScene;

    public CreateDatabaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            CreateDatabaseComponent component = ComponentFinder.findActivityComponent(context);
            component.inject(this);
        }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ButterKnife.inject(this);
        firstStepScene = Scene.getSceneForLayout(rootScene, R.layout.create_database_first_step_view, getContext());
        secondStepScene = Scene.getSceneForLayout(rootScene, R.layout.create_database_second_step_view, getContext());
        toFirstScene();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ButterKnife.reset(this);
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

    public void toFirstScene() {
        TransitionSet set = new TransitionSet();
        Slide tittle = new Slide(Gravity.TOP);
        tittle.addTarget(R.id.create_database_first_scene_tittle);
        set.addTransition(tittle);
        Slide finish = new Slide(Gravity.RIGHT);
        finish.addTarget(R.id.create_database_finish_button);
        set.addTransition(finish);
        set.addTransition(new ChangeBounds());
        set.setOrdering(TransitionSet.ORDERING_TOGETHER);
        set.setDuration(350);
        TransitionManager.go(firstStepScene, set);
    }

    public void toSecondScene() {
        TransitionSet set = new TransitionSet();
        Slide tittle = new Slide(Gravity.TOP);
        tittle.addTarget(R.id.create_database_first_scene_tittle);
        set.addTransition(tittle);
        Slide finish = new Slide(Gravity.RIGHT);
        finish.addTarget(R.id.create_database_finish_button);
        set.addTransition(finish);
        set.addTransition(new ChangeBounds());
        set.setOrdering(TransitionSet.ORDERING_TOGETHER);
        set.setDuration(350);
        TransitionManager.go(secondStepScene, set);
    }
}
