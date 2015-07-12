package ru.stefa.tizarhunter.stefasms.screens.numbers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.misc.BindableAdapter;

public class NumbersAdapter extends BindableAdapter<NumbersModel>
{
    private ArrayList<NumbersModel> mList;

    public NumbersAdapter(Context context, ArrayList<NumbersModel> list)
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
    public NumbersModel getItem(int position)
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
        return inflater.inflate(R.layout.number_listview_element, container, false);
    }

    @Override
    public void bindView(NumbersModel item, int position, View view)
    {
        ((TextView) view.findViewById(R.id.number_listview_element_text)).setText(item.getName().replaceAll("_", " "));
        if (item.getSize() != 0)
        {
            ((TextView) view.findViewById(R.id.number_listview_element_size)).setText(item.getSize() + "");
        }
        else
        {
            ((TextView) view.findViewById(R.id.number_listview_element_size)).setText("");
        }
    }
}
