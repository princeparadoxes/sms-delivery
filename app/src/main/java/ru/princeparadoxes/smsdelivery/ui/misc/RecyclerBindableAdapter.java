package ru.princeparadoxes.smsdelivery.ui.misc;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerBindableAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerViewHeaderFooterAdapter<T, VH> {

    private LayoutInflater inflater;
    private List<T> dataset = new ArrayList<>();

    public RecyclerBindableAdapter(Context context, RecyclerView.LayoutManager manager) {
        super(manager);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getRealItemCount() {
        return dataset.size();
    }

    public T getItem(int position) {
        return dataset.get(position);
    }

    public void add(int position, T item) {
        dataset.add(position, item);
        notifyItemInserted(position);
    }

    public void set(int position, T item) {
        dataset.set(position, item);
        notifyItemInserted(position);
    }

    public void addAll(List<? extends T> items) {
        final int size = dataset.size();
        dataset.addAll(items);
        notifyItemRangeInserted(size, items.size());
    }

    public void deleteChild(int position) {
        dataset.remove(position - getHeadersCount());
        notifyItemRemoved(position);
        if (position == dataset.size())
        notifyItemRangeChanged(position, dataset.size() - position - 1);
    }

    public void clear() {
        final int size = dataset.size();
        dataset.clear();
        notifyDataSetChanged();
    }

    public void moveChildToTop(int position) {
        final T item = dataset.remove(position - getHeadersCount());
        dataset.add(0, item);
        notifyItemMoved(position, getHeadersCount());
        notifyItemRangeChanged(getHeadersCount(), dataset.size());
    }

    @Override
    protected VH onCreteItemViewHolder(ViewGroup parent, int type) {
        return viewHolder(inflater.inflate(layoutId(type), parent, false), type);
    }

    protected abstract @LayoutRes int layoutId(int type);
    protected abstract VH viewHolder(View view, int type);

}
