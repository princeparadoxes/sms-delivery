package ru.stefa.tizarhunter.stefasms.screens.main.base.list;

import android.view.View;
import android.widget.TextView;

import com.danil.recyclerbindableadapter.library.view.BindableViewHolder;

import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.data.models.NumbersBaseModel;

/**
 * Created by Danil on 25.07.2016.
 */
public class NumbersBaseListItem extends BindableViewHolder<NumbersBaseModel,
        NumbersBaseListItem.NumberBaseListItemListener> {
    private TextView name;
    private TextView count;

    public NumbersBaseListItem(View itemView) {
        super(itemView);
        initViews();
    }

    private void initViews() {
        name = (TextView) itemView.findViewById(R.id.number_base_list_item_name);
        count = (TextView) itemView.findViewById(R.id.number_base_list_item_count);
    }

    @Override
    public void bindView(int position, NumbersBaseModel item, NumberBaseListItemListener actionListener) {
        super.bindView(position, item, actionListener);
        name.setText(item.getName());
        count.setText(itemView.getResources().getString(
                R.string.number_base_list_item_count_numbers, item.getCountNumbers()));
    }

    interface NumberBaseListItemListener extends BindableViewHolder
            .ActionListener<NumbersBaseModel> {
    }
}
