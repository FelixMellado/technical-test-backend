package com.playtomic.tests.wallet.model.dto;

import com.playtomic.tests.wallet.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);
    PaymentDto paymentToPaymentDTO(Payment payment);
    Payment paymentDtoToPayment(PaymentDto paymentDTO);
}
