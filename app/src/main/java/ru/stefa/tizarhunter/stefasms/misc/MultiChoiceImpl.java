package ru.stefa.tizarhunter.stefasms.misc;

import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.stefa.tizarhunter.stefasms.R;

public class MultiChoiceImpl implements AbsListView.MultiChoiceModeListener {
    private AbsListView listView;
    private OnClickMenuListener mOnClickMenuListener;

    public MultiChoiceImpl(AbsListView listView, OnClickMenuListener onClickMenuListener) {
        this.listView = listView;
        this.mOnClickMenuListener = onClickMenuListener;
    }

    @Override
    //Метод вызывается при любом изменении состояния выделения рядов
    public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
//        Log.d(MyActivity.TAG, "onItemCheckedStateChanged");
        int selectedCount = listView.getCheckedItemCount();
        //Добавим количество выделенных рядов в Context Action Bar
        setSubtitle(actionMode, selectedCount);
    }

    @Override
    //Здесь надуваем CAB из xml
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
//        Log.d(MainActivity.TAG, "onCreateActionMode");
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
//        Log.d(MainActivity.TAG, "onPrepareActionMode");
        return false;
    }

    @Override
    //Вызывается при клике на любой Item из СAB
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        if (menuItem.getTitle().equals("delete"))
        {
            String text = "Action - " + menuItem.getTitle() + " ; Selected items: " + getSelectedElements();
            Toast.makeText(listView.getContext(), text , Toast.LENGTH_LONG).show();
            if (mOnClickMenuListener != null)
            {
                mOnClickMenuListener.OnDeleteClick(getSelectedElements());
            }
        }
        return false;
    }


    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
//        Log.d(MainActivity.TAG, "onDestroyActionMode");
    }

    private void setSubtitle(ActionMode mode, int selectedCount) {
        switch (selectedCount) {
            case 0:
                mode.setSubtitle(null);
                break;
            default:
                mode.setTitle(String.valueOf(selectedCount));
                break;
        }
    }

    private List<String> getSelectedElements() {
        List<String> selectedElements = new ArrayList<String>();

        SparseBooleanArray sparseBooleanArray = listView.getCheckedItemPositions();
        for (int i = 0; i < sparseBooleanArray.size(); i++) {
            if (sparseBooleanArray.valueAt(i)) {
                String selectedItem = (String) listView.getItemAtPosition(sparseBooleanArray.keyAt(i));
                selectedElements.add(selectedItem);
            }
        }
        return selectedElements;
    }

    public static interface OnClickMenuListener
    {
        public void OnDeleteClick(List<String> selectedElements);
    }
}
