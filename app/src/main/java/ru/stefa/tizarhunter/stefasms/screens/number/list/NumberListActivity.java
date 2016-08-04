package ru.stefa.tizarhunter.stefasms.screens.number.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.danil.recyclerbindableadapter.library.SimpleBindableAdapter;
import com.danil.recyclerbindableadapter.library.view.BindableViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.SmsApplication;
import ru.stefa.tizarhunter.stefasms.data.DataService;
import ru.stefa.tizarhunter.stefasms.data.models.NumberModel;
import ru.stefa.tizarhunter.stefasms.data.models.NumbersBaseModel;
import ru.stefa.tizarhunter.stefasms.navigation.Router;

/**
 * Created by Danil on 04.08.2016.
 */
public class NumberListActivity extends AppCompatActivity implements NumberListItem.NumberListItemListener {
    @BindView(R.id.number_list_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.number_list_fab)
    FloatingActionButton fab;

    private SimpleBindableAdapter<NumberModel> adapter;
    private DataService dataService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_list_activity);
        ButterKnife.bind(this);
        dataService = ((SmsApplication) getApplication()).getDataService();
        initViews();
        extractParams();
    }

    private void extractParams(){
        Bundle params = getIntent().getExtras();
        if (params != null && params.containsKey(Router.NUMBER_LIST_BASE)) {
            NumbersBaseModel model = params.getParcelable(Router.NUMBER_LIST_BASE);
            adapter.addAll(model.getNumberList());
        }
    }

    private void initViews() {
        adapter = new SimpleBindableAdapter<>(R.layout.number_list_item, NumberListItem.class);
        adapter.setActionListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab.setOnClickListener(v -> {
//            NewBaseDialog newBaseDialog = new NewBaseDialog(getActivity(), adapter.getItems());
//            newBaseDialog.setNewBaseDialogListener(this);
//            newBaseDialog.show();
        });
    }

    @Override
    public void OnItemClickListener(int position, NumberModel item) {

    }
}
