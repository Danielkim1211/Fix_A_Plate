package com.example.greenplate.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.greenplate.R;
import com.example.greenplate.viewmodels.IngredientsViewModel;

import java.util.ArrayList;

public class IngredientAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Ingredient> list = new ArrayList<Ingredient>();
    private Context context;

    public IngredientAdapter(ArrayList<Ingredient> list, Context context) {
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
            view = inflater.inflate(R.layout.ingredient_row, null);
        }

        TextView ingredientName = (TextView) view.findViewById(R.id.ingredient_name);
        ingredientName.setText(list.get(position).getName());

        TextView ingredientQuantity = (TextView) view.findViewById(R.id.quantity);
        ingredientQuantity.setText(String.valueOf(list.get(position).getQuantity()));

        Button increaseButton = (Button) view.findViewById(R.id.incrButton);
        Button decreaseButton = (Button) view.findViewById(R.id.decrButton);

        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).increaseQuantity();
                IngredientsViewModel.updateIngredient(list.get(position));
                ingredientQuantity.setText(String.valueOf(list.get(position).getQuantity()));
            }
        });

        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newQuantity = list.get(position).decreaseQuantity();
                IngredientsViewModel.updateIngredient(list.get(position));
                if (newQuantity > 0) {
                    ingredientQuantity.setText(String.valueOf(list.get(position).getQuantity()));
                } else {
                    notifyDataSetChanged();
                }
            }
        });

        return view;
    }
}
