package com.owl.tripmanager.dto;

import com.owl.tripmanager.model.TravelExpense;

public record TravelExpenseResponse(
    Long id,
    Long tripId,
    Integer value,
    String comment
) {
    public static TravelExpenseResponse fromEntity(TravelExpense expense) {
        return new TravelExpenseResponse(
            expense.getId(),
            expense.getTrip().getId(),
            expense.getValue(),
            expense.getComment()
        );
    }
}
