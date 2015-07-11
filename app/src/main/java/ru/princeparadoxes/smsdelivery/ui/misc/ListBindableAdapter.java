package ru.princeparadoxes.smsdelivery.ui.misc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

public abstract class ListBindableAdapter<T> extends BindableAdapter<T> implements Filterable {
    private final Object mLock = new Object();
    private List<T> list = new ArrayList<>();
    private List<T> originalList = new ArrayList<>();
    private ArrayFilter mFilter;

    protected ListBindableAdapter(Context context) {
        super(context);
    }

    protected List<T> getList() {
        return originalList;
    }

    public void add(T item) {
        list.add(item);
        originalList.add(item);
    }

    public void addAll(List<? extends T> items) {
        list.addAll(items);
        originalList.addAll(items);
    }

    public void clear() {
        list.clear();
        originalList.clear();
    }

    public void removeItemByPosition(int position) {
        final T item = list.remove(position);
        for (T t : originalList) {
            if (t.equals(item)) {
                originalList.remove(t);
                break;
            }
        }
    }

    public void removeItem(T item) {
        list.remove(item);
        for (T t : originalList) {
            if (t.equals(item)) {
                originalList.remove(t);
                break;
            }
        }
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup container) {
        return inflater.inflate(layoutId(position), container, false);
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return originalList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @LayoutRes
    protected abstract int layoutId(int position);

    protected String itemToString(T item) {
        return item.toString();
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    protected ArrayList<T> doFilter(String prefixString, ArrayList<T> values) {
        int count = values.size();
        final ArrayList<T> newValues = new ArrayList<T>();

        for (int i = 0; i < count; i++) {
            final T value = values.get(i);
            final String valueText = itemToString(value).toLowerCase();
            if (valueText.isEmpty()) {
                newValues.add(value);
                continue;
            }
            // First match against the whole, non-splitted value
            if (valueText.startsWith(prefixString)) {
                newValues.add(value);
            } else {
                final String[] words = valueText.split(" ");
                final int wordCount = words.length;

                // Start at index 0, in case valueText starts with space(s)
                for (int k = 0; k < wordCount; k++) {
                    final String word = words[k];
                    if (word.startsWith(prefixString) || prefixString.contains(word)) {
                        newValues.add(value);
                        break;
                    }
                }
            }
        }
        return newValues;
    }

    public void deleteChild(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }

    /**
     * <p>An array filter constrains the content of the array adapter with
     * a prefix. Each item that does not start with the supplied prefix
     * is removed from the list.</p>
     */
    private class ArrayFilter extends Filter {
        @Override
        @SuppressLint("DefaultLocale")
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (originalList == null) {
                synchronized (mLock) {
                    originalList = new ArrayList<T>(list);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                ArrayList<T> list;
                synchronized (mLock) {
                    list = new ArrayList<T>(originalList);
                }
                results.values = list;
                results.count = list.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();

                ArrayList<T> values;
                synchronized (mLock) {
                    values = new ArrayList<T>(originalList);
                }

                final ArrayList<T> newValues = doFilter(prefixString, values);

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //noinspection unchecked
            list = (List<T>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
