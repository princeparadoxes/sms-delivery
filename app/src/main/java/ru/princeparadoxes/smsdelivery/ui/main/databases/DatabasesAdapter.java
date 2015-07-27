package ru.princeparadoxes.smsdelivery.ui.main.databases;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.princeparadoxes.smsdelivery.R;
import ru.princeparadoxes.smsdelivery.data.model.DatabaseOfPhoneNumbers;
import ru.princeparadoxes.smsdelivery.ui.misc.RecyclerBindableAdapter;

public class DatabasesAdapter extends RecyclerBindableAdapter<DatabaseOfPhoneNumbers, DatabasesAdapter.ViewHolder> {

    private final Context context;

    public DatabasesAdapter(Context context,
                            RecyclerView.LayoutManager manager) {
        super(context, manager);
        this.context = context;
    }

    @Override
    protected int getItemType(int position) {
        return 0;
    }

    @Override
    protected int layoutId(int type) {
        return R.layout.main_databases_item;
    }

    @Override
    protected ViewHolder viewHolder(View view, int type) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindItemViewHolder(ViewHolder viewHolder, int position, int type) {
        viewHolder.bindView(getItem(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(DatabaseOfPhoneNumbers item) {
            ((TextView) itemView.findViewById(R.id.number_listview_element_text)).setText(item.getName()
                    + " " + item.getId());
//            if (item.getNumbers().size() != 0)
//            {
//                ((TextView) itemView.findViewById(R.id.number_listview_element_size)).setText(item.getNumbers().size() + "");
//            }
//            else
//            {
//                ((TextView) itemView.findViewById(R.id.number_listview_element_size)).setText("");
//            }
        }
    }
}
