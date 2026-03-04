package com.example.greenplate.viewmodels;

import com.example.greenplate.models.Recipe;
import com.example.greenplate.models.SortingStrategy;

import java.util.Arrays;

public class SortByName implements SortingStrategy {
    public Recipe[] sortRecipes(Recipe[] recipeList) {
        Arrays.sort(recipeList);
        return recipeList;
    }
}
