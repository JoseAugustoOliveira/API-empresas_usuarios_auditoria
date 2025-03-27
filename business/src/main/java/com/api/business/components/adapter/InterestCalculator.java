package com.api.business.components.adapter;

@FunctionalInterface
public interface InterestCalculator {
    Double calculate(int quantityInstallments);
}