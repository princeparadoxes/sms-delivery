package ru.stefa.tizarhunter.stefasms.screens.number.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.danil.recyclerbindableadapter.library.SimpleBindableAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.SmsApplication;
import ru.stefa.tizarhunter.stefasms.data.DataService;
import ru.stefa.tizarhunter.stefasms.data.models.ContactModel;
import ru.stefa.tizarhunter.stefasms.data.models.NumbersBaseModel;
import ru.stefa.tizarhunter.stefasms.navigation.Router;

/**
 * Created by Danil on 04.08.2016.
 */
public class ContactListActivity extends AppCompatActivity implements ContactListItem.NumberListItemListener {
    @BindView(R.id.contact_list_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.contact_list_fab)
    FloatingActionButton fab;

    private SimpleBindableAdapter<ContactModel> adapter;
    private DataService dataService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        adapter = new SimpleBindableAdapter<>(R.layout.contact_list_item, ContactListItem.class);
        adapter.setActionListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab.setOnClickListener(v -> {
            NewContactDialog newContactDialog = new NewContactDialog(this);
            newContactDialog.show();
//            NewBaseDialog newBaseDialog = new NewBaseDialog(getActivity(), adapter.getItems());
//            newBaseDialog.setNewBaseDialogListener(this);
//            newBaseDialog.show();
        });
    }

    @Override
    public void OnItemClickListener(int position, ContactModel item) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
