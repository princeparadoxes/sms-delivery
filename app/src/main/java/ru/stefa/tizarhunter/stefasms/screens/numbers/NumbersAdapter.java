package ru.stefa.tizarhunter.stefasms.screens.numbers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.misc.BindableAdapter;

public class NumbersAdapter extends BindableAdapter<String>
{
    private ArrayList<String> mList;

    public NumbersAdapter(Context context,  ArrayList<String> list)
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
    public String getItem(int position)
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
        return inflater.inflate(R.layout.listview_ticket, container, false);
    }

    @Override
    public void bindView(String item, int position, View view)
    {
        if (position==0)
        {
            ((TextView) view.findViewById(R.id.listview_element_text)).setText("Добавить новую базу номеров");
            ((ImageView) view.findViewById(R.id.listView_element_image)).setVisibility(View.VISIBLE);
        }
        else
        {
            ((TextView) view.findViewById(R.id.listview_element_text)).setText(item);
        }

    }
}
