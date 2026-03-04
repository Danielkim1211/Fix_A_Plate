package com.example.greenplate.models;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.greenplate.R;
import com.example.greenplate.views.ViewRecipeActivity;
import com.example.greenplate.views.ViewRecipeActivityNeedIngredients;

import java.util.ArrayList;

public class RecipeListAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Recipe> list = new ArrayList<Recipe>();
    private Context context;

    public RecipeListAdapter(ArrayList<Recipe> list, Context context) {
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
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (list.get(position).isCanMake()) {
            view = inflater.inflate(R.layout.recipe_row, null);
        } else {
            view = inflater.inflate(R.layout.recipe_row_need_ingredients, null);
        }


        Button navigateScreen = (Button) view.findViewById(R.id.navigate_to_recipe_button);
        TextView recipeName;

        recipeName = view.findViewById(R.id.recipe_name);
        if (recipeName != null) {
            recipeName.setText(list.get(position).getRecipeName());
        } else {
            recipeName = view.findViewById(R.id.name);
            if (recipeName != null) {
                recipeName.setText(list.get(position).getRecipeName());
            }
        }

        navigateScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class<?> destinationClass = list.get(position).isCanMake()
                        ? ViewRecipeActivity.class : ViewRecipeActivityNeedIngredients.class;

                Intent intent = new Intent(context, destinationClass);
                intent.putExtra("recipe", list.get(position).getRecipeName());
                context.startActivity(intent);
            }
        });

        return view;
    }
}
