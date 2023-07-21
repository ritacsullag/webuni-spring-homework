package com.csullagrita.financialservice.controller;

import com.csullagrita.financialservice.model.PaymentMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/finance")
public class FinancialController {
    Logger logger = LoggerFactory.getLogger(FinancialController.class);

    private final JmsTemplate jmsTemplate;

    @PostMapping("/registerpayment")
    public void registerPayment(@RequestBody PaymentMessage paymentDto) {
        logger.info("Registering payment");
        PaymentMessage paymentMessage = new PaymentMessage(paymentDto.getStudentId(), paymentDto.getAmountOfMoney());
        logger.info(String.format("Message: %s", paymentMessage));

        this.jmsTemplate.convertAndSend("payments", paymentMessage);
    }


}
