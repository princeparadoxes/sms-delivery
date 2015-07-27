package ru.princeparadoxes.smsdelivery.ui.main.databases;

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
import ru.princeparadoxes.smsdelivery.ui.misc.OnRecyclerItemClickListener;

public class DatabasesView extends FrameLayout implements BaseView {

    @Inject
    DatabasesPresenter presenter;

    @InjectView(R.id.main_numbers_recycler_view)
    protected RecyclerView recyclerView;
    @InjectView(R.id.main_numbers_center_text)
    protected TextView centerText;

    private RecyclerView.LayoutManager layoutManager;
    private DatabasesAdapter databasesAdapter;

    public DatabasesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            MainComponent component = ComponentFinder.findActivityComponent(context);
            component.inject(this);
        }
        layoutManager = new LinearLayoutManager(context);
        databasesAdapter = new DatabasesAdapter(context, layoutManager);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.takeView(this);
        ButterKnife.inject(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        LinearLayout header = (LinearLayout) inflater.inflate(
                R.layout.main_databases_header, null, false);
        header.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addDatabaseNumbers();
            }
        });
        databasesAdapter.addHeader(header);
        databasesAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(databasesAdapter);
        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(getContext(),
                new OnRecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View childView, int position) {
                            presenter.openDialog(position);
                    }

                    @Override
                    public void onItemLongPress(View childView, int position) {

                    }
                }));
    }

    public void setData(List<DatabaseOfPhoneNumbers> databaseOfPhoneNumberses) {
        if (databaseOfPhoneNumberses.size() > 0) {
            databasesAdapter.addAll(databaseOfPhoneNumberses);
        } else {
            centerText.setText("Bases not found");
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        ButterKnife.reset(this);
        presenter.dropView(this);
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


    public void deleteElement(int position) {
        databasesAdapter.deleteChild(position);
    }

    public void moveToTop(int position) {
        databasesAdapter.moveChildToTop(position);
    }
}
