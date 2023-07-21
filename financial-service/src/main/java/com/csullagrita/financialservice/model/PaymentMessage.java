package com.csullagrita.financialservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMessage {

    private long studentId;
    private int amountOfMoney;
}
