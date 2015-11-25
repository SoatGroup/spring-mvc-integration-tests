package fr.soat.java.services;

import fr.soat.java.dto.OrderDto;
import fr.soat.java.exceptions.BusinessException;

public interface IOrderService {
    OrderDto saveOrder(OrderDto orderDto) throws BusinessException;

    OrderDto getOrder(String orderId);

    void deleteOrder(String orderId);
}
