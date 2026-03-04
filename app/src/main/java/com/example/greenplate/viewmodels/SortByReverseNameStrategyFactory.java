package com.example.greenplate.viewmodels;

import com.example.greenplate.models.SortingStrategy;
import com.example.greenplate.models.SortingStrategyFactory;

public class SortByReverseNameStrategyFactory implements SortingStrategyFactory {
    @Override
    public SortingStrategy createFactorySortingStrategy() {
        return new SortByReverseName();
    }
}
