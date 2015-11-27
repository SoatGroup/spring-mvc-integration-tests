package fr.soat.java.services;

import fr.soat.java.dto.OrderDto;
import fr.soat.java.exceptions.OrderNotFoundException;
import fr.soat.java.exceptions.TooManyProductsException;

public interface IOrderService {
    OrderDto saveOrder(OrderDto orderDto) throws TooManyProductsException;

    OrderDto updateOrder(OrderDto orderDto) throws TooManyProductsException;

    OrderDto getOrder(String orderId) throws OrderNotFoundException;

    void deleteOrder(String orderId);
}
