package com.csullagrita.school.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentMessage {
    private long studentId;
    private int amountOfMoney;
}
