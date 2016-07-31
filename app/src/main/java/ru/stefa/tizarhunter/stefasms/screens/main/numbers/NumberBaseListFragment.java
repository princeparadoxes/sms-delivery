package ru.stefa.tizarhunter.stefasms.screens.main.numbers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.danil.recyclerbindableadapter.library.SimpleBindableAdapter;

import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.SmsApplication;
import ru.stefa.tizarhunter.stefasms.data.DataService;
import ru.stefa.tizarhunter.stefasms.data.models.NewBaseDialogModel;
import ru.stefa.tizarhunter.stefasms.data.models.NumberBaseModel;
import ru.stefa.tizarhunter.stefasms.screens.main.numbers.newbasedialog.NewBaseDialog;

public class NumberBaseListFragment extends Fragment implements NumberBaseListItem
        .NumberBaseListItemListener, NewBaseDialog.NewBaseDialogListener {
    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    private SimpleBindableAdapter<NumberBaseModel> adapter;
    private DataService dataService;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_number_base_list, container, false);
        dataService = ((SmsApplication) getActivity().getApplication()).getDataService();
        findViews(rootView);
        initViews();
        loadData();
        return rootView;
    }

    private void findViews(View root) {
        recyclerView = (RecyclerView) root.findViewById(R.id.number_base_list_recycler);
        fab = (FloatingActionButton) root.findViewById(R.id.number_base_list_fab);
    }

    private void initViews() {
        adapter = new SimpleBindableAdapter<>(R.layout.number_base_list_item,
                NumberBaseListItem.class);
        adapter.setActionListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fab.setOnClickListener(v -> {
            NewBaseDialog newBaseDialog = new NewBaseDialog(getActivity());
            newBaseDialog.setNewBaseDialogListener(this);
            newBaseDialog.show();
        });
    }

    private void loadData() {
        dataService.getNumberBaseList()
                .compose(DataService.applySchedulers())
                .subscribe(adapter::addAll, throwable -> {
                });
    }

    @Override
    public void OnItemClickListener(int position, NumberBaseModel item) {

    }

    @Override
    public void onOkButtonClick(String name, NewBaseDialogModel newBaseDialogModel) {
        
    }
}