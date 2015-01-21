package ru.stefa.tizarhunter.stefasms.screens.archive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Date;
import java.util.List;

import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.misc.BindableAdapter;

public class ArchiveAdapter extends BindableAdapter<ArchiveModel>
{

    private List<ArchiveModel> mList;

    public ArchiveAdapter(Context context, List<ArchiveModel> list)
    {
        super(context);
        mList = list;
    }

    @Override
    public int getCount()
    {
        return mList.size();
    }

    @Override
    public ArchiveModel getItem(int position)
    {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup container)
    {
        return inflater.inflate(R.layout.archive_listview_element, container, false);
    }

    @Override
    public void bindView(ArchiveModel item, int position, View view)
    {
        ((TextView) view.findViewById(R.id.archive_listview_element_text)).setText(item.getText());
        ((TextView) view.findViewById(R.id.archive_listview_element_number)).setText(item.getNumberSends() + "");
        Date date = new Date(item.getDateTime());
        ((TextView) view.findViewById(R.id.archive_listview_element_date)).setText(date.toString());
    }
}
