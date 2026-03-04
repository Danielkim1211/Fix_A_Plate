package com.example.greenplate.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.greenplate.R;
import com.example.greenplate.viewmodels.ShoppingListViewModel;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingListAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<ShoppingListItem> list = new ArrayList<ShoppingListItem>();
    private Context context;
    private HashMap<Integer, CheckBox> checkBoxMap = new HashMap<>();

    public ShoppingListAdapter(ArrayList<ShoppingListItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.shopping_list_row, null);
        }

        TextView listItemName = (TextView) view.findViewById(R.id.recipe_name);
        listItemName.setText(list.get(position).getName());

        TextView listQuantity = (TextView) view.findViewById(R.id.quantity);
        listQuantity.setText(String.valueOf(list.get(position).getQuantity()));

        CheckBox buyBox = (CheckBox) view.findViewById(R.id.myCheckbox);
        checkBoxMap.put(position, buyBox);

        buyBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buyBox.isChecked()) {
                    ShoppingListViewModel.selectItem(list.get(position));
                } else {
                    ShoppingListViewModel.unselectItem(list.get(position));
                }
            }
        });

        Button increaseQuantity = (Button) view.findViewById(R.id.incrButton);
        Button decreaseQuantity = (Button) view.findViewById(R.id.decrButton);

        increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).increaseQuantity();
                notifyDataSetChanged();
            }
        });

        decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).decreaseQuantity();
                notifyDataSetChanged();
            }
        });

        return view;
    }

    public void uncheckAllCheckboxes() {
        for (CheckBox cb : checkBoxMap.values()) {
            if (cb != null) {
                cb.setChecked(false);
            }
        }
        notifyDataSetChanged();  // Update the ListView
    }
}
