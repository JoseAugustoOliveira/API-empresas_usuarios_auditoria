package com.api.business.components.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.api.business.models.constants.QuantityInstallments.FORTY_EIGHT;
import static com.api.business.models.constants.QuantityInstallments.FORTY_NINE;
import static com.api.business.models.constants.QuantityInstallments.ONE;
import static com.api.business.models.constants.QuantityInstallments.SIXTY;
import static com.api.business.models.constants.QuantityInstallments.THIRTEEN;
import static com.api.business.models.constants.QuantityInstallments.THIRTY_SEVEN;
import static com.api.business.models.constants.QuantityInstallments.THIRTY_SIX;
import static com.api.business.models.constants.QuantityInstallments.TWELVE;
import static com.api.business.models.constants.QuantityInstallments.TWENTY_FIVE;
import static com.api.business.models.constants.QuantityInstallments.TWENTY_FOUR;

@Component
@RequiredArgsConstructor
public class InterestCalculatorAdapter {

    @Value("${first.value}")
    private Double firstValue;

    @Value("${second.value}")
    private Double secondValue;

    @Value("${third.value}")
    private Double thirdValue;

    @Value("${fourth.value}")
    private Double fourthValue;

    @Value("${fifth.value}")
    private Double fifthValue;

    public InterestCalculator getCalculator() {
        return quantityInstallments -> {
            if (quantityInstallments >= ONE && quantityInstallments <= TWELVE) {
                return firstValue;
            } else if (quantityInstallments >= THIRTEEN && quantityInstallments <= TWENTY_FOUR) {
                return secondValue;
            } else if (quantityInstallments >= TWENTY_FIVE && quantityInstallments <= THIRTY_SIX) {
                return thirdValue;
            } else if (quantityInstallments >= THIRTY_SEVEN && quantityInstallments <= FORTY_EIGHT) {
                return fourthValue;
            } else if (quantityInstallments >= FORTY_NINE && quantityInstallments <= SIXTY) {
                return fifthValue;
            } else {
                throw new IllegalArgumentException("Invalid quantity of installments: " + quantityInstallments);
            }
        };
    }
}
