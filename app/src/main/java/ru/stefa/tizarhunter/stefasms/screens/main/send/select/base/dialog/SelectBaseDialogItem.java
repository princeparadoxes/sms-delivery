package ru.stefa.tizarhunter.stefasms.screens.main.send.select.base.dialog;

import android.view.View;
import android.widget.TextView;

import com.danil.recyclerbindableadapter.library.view.BindableViewHolder;

import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.data.models.NumberBaseModel;

/**
 * Created by Danil on 25.07.2016.
 */
public class SelectBaseDialogItem extends BindableViewHolder<NumberBaseModel,
        SelectBaseDialogItem.SelectBaseDialogItemListener> {
    private TextView name;
    private TextView count;

    public SelectBaseDialogItem(View itemView) {
        super(itemView);
        initViews();
    }

    private void initViews() {
        name = (TextView) itemView.findViewById(R.id.select_base_dialog_item_name);
        count = (TextView) itemView.findViewById(R.id.select_base_dialog_item_count);
    }

    @Override
    public void bindView(int position, NumberBaseModel item, SelectBaseDialogItemListener actionListener) {
        super.bindView(position, item, actionListener);
        name.setText(item.getName());
        count.setText(String.valueOf(item.getCountNumbers()));
    }

    interface SelectBaseDialogItemListener extends BindableViewHolder
            .ActionListener<NumberBaseModel> {
    }
}
