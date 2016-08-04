package ru.stefa.tizarhunter.stefasms.screens.number.list;

import android.view.View;
import android.widget.TextView;

import com.danil.recyclerbindableadapter.library.view.BindableViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.data.models.NumberModel;
import ru.stefa.tizarhunter.stefasms.data.models.NumbersBaseModel;

/**
 * Created by Danil on 25.07.2016.
 */
public class NumberListItem extends BindableViewHolder<NumberModel,
        NumberListItem.NumberListItemListener> {
    @BindView(R.id.number_list_item_number)
    TextView number;

    public NumberListItem(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindView(int position, NumberModel item, NumberListItemListener actionListener) {
        super.bindView(position, item, actionListener);
        number.setText(item.getNumber());
    }

    interface NumberListItemListener extends BindableViewHolder.ActionListener<NumberModel> {
    }
}
