package fr.soat.java.services.impl.test;


import fr.soat.java.dao.IOrderRepository;
import fr.soat.java.dto.OrderDto;
import fr.soat.java.dto.ProductDto;
import fr.soat.java.exceptions.TooManyProductsException;
import fr.soat.java.model.OrderEntity;
import fr.soat.java.model.ProductEntity;
import fr.soat.java.services.impl.OrderService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    private IOrderRepository orderRepository;

    @Before
    public void before() {

    }

    @Test(expected = TooManyProductsException.class)
    public void testMoreThanProductLimitSaveOrder() throws Exception {
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(null);
        OrderDto orderDto = new OrderDto();
        orderDto.getProductList().add(new ProductDto());
        orderDto.getProductList().add(new ProductDto());
        orderDto.getProductList().add(new ProductDto());
        orderService.saveOrder(orderDto);
    }

    @Test
    public void testSaveOrder() throws Exception {
        OrderDto orderDto = new OrderDto();
        orderDto.getProductList().add(new ProductDto());
        OrderEntity mockEntity = new OrderEntity();
        mockEntity.getProductList().add(new ProductEntity());
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(mockEntity);
        OrderDto res = orderService.saveOrder(orderDto);
        Assert.assertTrue(res.getProductList().size() == 1);
    }
}