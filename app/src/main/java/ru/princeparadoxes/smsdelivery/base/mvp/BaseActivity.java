package ru.princeparadoxes.smsdelivery.base.mvp;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import javax.inject.Inject;

import cat.ppicas.customtypeface.CustomTypeface;
import cat.ppicas.customtypeface.CustomTypefaceFactory;
import ru.princeparadoxes.smsdelivery.SmsApplication;
import ru.princeparadoxes.smsdelivery.SmsComponent;
import ru.princeparadoxes.smsdelivery.R;
import ru.princeparadoxes.smsdelivery.base.tools.ToastPresenter;
import ru.princeparadoxes.smsdelivery.ui.AppContainer;
import ru.princeparadoxes.smsdelivery.util.Strings;

public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    AppContainer appContainer;

    @Inject
    ToastPresenter toastPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle params = getIntent().getExtras();
        if (params != null) {
            onExtractParams(params);
        }
        getLayoutInflater().setFactory(new CustomTypefaceFactory(this, CustomTypeface.getInstance()));
        super.onCreate(savedInstanceState);

        SmsApplication app = SmsApplication.get(this);
        onCreateComponent(app.component());
        if (appContainer == null) {
            throw new IllegalStateException("No injection happened. Add component.inject(this)"
                    + " in onCreateComponent() implementation.");
        }
        Registry.add(this, viewId(), presenter());
        final LayoutInflater layoutInflater = getLayoutInflater();
        ViewGroup container = appContainer.get(this);
        ViewGroup base = (ViewGroup) layoutInflater.inflate(R.layout.base_layout, container);
        ViewGroup my = (ViewGroup) layoutInflater.inflate(layoutId(), null);
        base.addView(my, 0);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        toastPresenter.attach(this);
    }

    @Override
    protected void onStop() {
        toastPresenter.detach();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    protected void onExtractParams(@NonNull Bundle params) {
    }

    public String uniqueKey() {
        return Strings.EMPTY;
    }

    protected abstract void onCreateComponent(SmsComponent component);

    @LayoutRes
    protected abstract int layoutId();

    protected abstract BasePresenter<? extends BaseView> presenter();

    @IdRes
    protected abstract int viewId();

}
