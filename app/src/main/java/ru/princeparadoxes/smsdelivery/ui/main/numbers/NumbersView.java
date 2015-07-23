package ru.princeparadoxes.smsdelivery.ui.main.numbers;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ru.princeparadoxes.smsdelivery.R;
import ru.princeparadoxes.smsdelivery.base.ComponentFinder;
import ru.princeparadoxes.smsdelivery.base.mvp.BaseView;
import ru.princeparadoxes.smsdelivery.data.model.DatabaseOfPhoneNumbers;
import ru.princeparadoxes.smsdelivery.ui.main.MainComponent;

public class NumbersView extends FrameLayout implements BaseView {

    @Inject
    NumbersPresenter numbersPresenter;

    @InjectView(R.id.main_numbers_recycler_view)
    protected RecyclerView recyclerView;
    @InjectView(R.id.main_numbers_center_text)
    protected TextView centerText;

    private RecyclerView.LayoutManager layoutManager;
    private NumbersAdapter numbersAdapter;

    public NumbersView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            MainComponent component = ComponentFinder.findActivityComponent(context);
            component.inject(this);
        }
        layoutManager = new LinearLayoutManager(context);
        numbersAdapter = new NumbersAdapter(context, layoutManager);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        numbersPresenter.takeView(this);
        ButterKnife.inject(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        LinearLayout header = (LinearLayout) inflater.inflate(
                R.layout.number_listview_header, null, false);
        header.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                numbersPresenter.addDatabaseNumbers();
            }
        });
        numbersAdapter.addHeader(header);
        numbersAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(numbersAdapter);
    }

    public void setData(List<DatabaseOfPhoneNumbers> databaseOfPhoneNumberses) {
        if (databaseOfPhoneNumberses.size() > 0) {
        numbersAdapter.addAll(databaseOfPhoneNumberses);
        } else {
            centerText.setText("Bases not found");
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        ButterKnife.reset(this);
        numbersPresenter.dropView(this);
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
