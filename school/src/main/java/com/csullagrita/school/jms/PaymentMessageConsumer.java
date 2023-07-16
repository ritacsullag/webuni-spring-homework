package com.csullagrita.school.jms;

import com.csullagrita.school.model.PaymentMessage;
import com.csullagrita.school.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentMessageConsumer {

    Logger logger = LoggerFactory.getLogger(PaymentMessageConsumer.class);
    private final StudentService studentService;

    @JmsListener(destination = "payments")
    public void onPaymentMessage(PaymentMessage message) {
        logger.info(String.format("Message is: %s", message.toString()));
        studentService.saveStudentPayment(message.getStudentId(), message.getAmountOfMoney());
    }
}
