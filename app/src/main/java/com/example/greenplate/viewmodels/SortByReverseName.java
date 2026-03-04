package com.example.greenplate.viewmodels;

import com.example.greenplate.models.Recipe;
import com.example.greenplate.models.SortingStrategy;

import java.util.Arrays;
import java.util.Collections;

public class SortByReverseName implements SortingStrategy {
    public Recipe[] sortRecipes(Recipe[] recipeList) {
        Arrays.sort(recipeList, Collections.reverseOrder());
        return recipeList;
    }
}
