package fr.soat.java.services.impl;

import fr.soat.java.dao.IOrderRepository;
import fr.soat.java.dto.OrderDto;
import fr.soat.java.dto.ProductDto;
import fr.soat.java.exceptions.OrderNotFoundException;
import fr.soat.java.exceptions.TooManyProductsException;
import fr.soat.java.model.OrderEntity;
import fr.soat.java.model.ProductEntity;
import fr.soat.java.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository orderRepository;

    private static final int NB_MAX_PRODUCTS = 2;

    @Override
    public OrderDto saveOrder(OrderDto orderDto) throws TooManyProductsException {
        if (orderDto.getProducts().size() > NB_MAX_PRODUCTS) {
            throw new TooManyProductsException();
        }
        orderDto.setCreationDate(Instant.now().toString());
        return fromOrderEntity(orderRepository.save(toOrderEntity(orderDto)));
    }

    @Override
    public OrderDto updateOrder(OrderDto orderDto) throws TooManyProductsException {
        orderDto.setModificationDate(Instant.now().toString());
        return saveOrder(orderDto);
    }

    @Override
    public OrderDto getOrder(String orderId) throws OrderNotFoundException{
        OrderEntity found = orderRepository.findOne(orderId);
        if(found == null){
            throw new OrderNotFoundException();
        }
        return fromOrderEntity(found);
    }

    @Override
    public void deleteOrder(String orderId) {
        orderRepository.delete(orderId);
    }

    private OrderDto fromOrderEntity(OrderEntity entity) {
        OrderDto order = new OrderDto();
        order.setCreationDate(entity.getCreationDate());
        order.setId(entity.get_id());
        order.setModificationDate(entity.getModificationDate());
        entity.getProductList().forEach((productEntity -> order.getProducts().add(fromProductEntity(productEntity))));
        return order;
    }

    private ProductDto fromProductEntity(ProductEntity entity) {
        ProductDto product = new ProductDto();
        product.setName(entity.getName());
        return product;
    }

    private OrderEntity toOrderEntity(OrderDto order) {
        OrderEntity entity = new OrderEntity();
        entity.setCreationDate(order.getCreationDate());
        entity.set_id(order.getId());
        entity.setModificationDate(order.getModificationDate());
        order.getProducts().forEach((product -> entity.getProductList().add(toProductEntity(product))));
        return entity;
    }

    private ProductEntity toProductEntity(ProductDto product) {
        ProductEntity entity = new ProductEntity();
        entity.setName(product.getName());
        return entity;
    }
}
