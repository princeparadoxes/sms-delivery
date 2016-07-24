package ru.stefa.tizarhunter.stefasms.screens.main.send.select.base.dialog;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.danil.recyclerbindableadapter.library.SimpleBindableAdapter;

import java.util.List;

import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.data.models.NumberBaseModel;

public class SelectBaseDialog extends BottomSheetDialog implements
        SelectBaseDialogItem.SelectBaseDialogItemListener{
    private RecyclerView recyclerView;
    private SimpleBindableAdapter<NumberBaseModel> adapter;
    private SelectBaseDialogListener listener;

    public SelectBaseDialog(final Context context) {
        super(context);
        setContentView(R.layout.select_base_dialog);
        findViews();
        initViews();
    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.select_base_dialog_recycler);
    }

    private void initViews() {
        adapter = new SimpleBindableAdapter<>(R.layout.select_base_dialog_item,
                SelectBaseDialogItem.class);
        adapter.setActionListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void bindData(List<NumberBaseModel> baseModels) {
        adapter.addAll(baseModels);
    }

    public void setListener(SelectBaseDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public void OnItemClickListener(int position, NumberBaseModel item) {
        dismiss();
        if (listener != null) listener.onSelectBaseDialogItemClick(item);
    }

    public interface SelectBaseDialogListener {
        void onSelectBaseDialogItemClick(NumberBaseModel numberBaseModel);
    }
}
