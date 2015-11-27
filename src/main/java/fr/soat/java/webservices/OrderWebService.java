package fr.soat.java.webservices;

import fr.soat.java.dto.OrderDto;
import fr.soat.java.dto.ProductDto;
import fr.soat.java.exceptions.BusinessException;
import fr.soat.java.payload.Order;
import fr.soat.java.payload.Product;
import fr.soat.java.payload.wrappers.ResponseWrapper;
import fr.soat.java.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderWebService {

    @Autowired
    private IOrderService orderService;

    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    public ResponseWrapper<Order> getOrder(
            @PathVariable String orderId) {
        ResponseWrapper<Order> response = new ResponseWrapper<>();
        response.setData(fromOrderDto(orderService.getOrder(orderId)));
        return response;
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseWrapper<Order> saveOrder(
            @RequestBody Order order) throws BusinessException {
        ResponseWrapper<Order> response = new ResponseWrapper<>();
        response.setData(fromOrderDto(orderService.saveOrder(toOrderDto(order))));
        return response;
    }

    @RequestMapping(value = "/{orderId}", method = RequestMethod.PUT)
    public ResponseWrapper<Order> updateOrder(
            @PathVariable String orderId,
            @RequestBody Order order) throws BusinessException {
        order.setId(orderId);
        ResponseWrapper<Order> response = new ResponseWrapper<>();
        response.setData(fromOrderDto(orderService.updateOrder(toOrderDto(order))));
        return response;
    }

    @RequestMapping(value = "/{orderId}", method = RequestMethod.DELETE)
    public ResponseWrapper<Order> deleteOrder(
            @PathVariable String orderId) {
        ResponseWrapper<Order> response = new ResponseWrapper<>();
        orderService.deleteOrder(orderId);
        response.setData(null);
        return response;
    }

    private Order fromOrderDto(OrderDto dto) {
        Order order = new Order();
        order.setCreationDate(dto.getCreationDate());
        order.setId(dto.getId());
        order.setModificationDate(dto.getModificationDate());
        dto.getProductList().forEach((productDto -> order.getProducts().add(fromProductDto(productDto))));
        return order;
    }

    private Product fromProductDto(ProductDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        return product;
    }

    private OrderDto toOrderDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setCreationDate(order.getCreationDate());
        dto.setId(order.getId());
        dto.setModificationDate(order.getModificationDate());
        order.getProducts().forEach((product -> dto.getProductList().add(toProductDto(product))));
        return dto;
    }

    private ProductDto toProductDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setName(product.getName());
        return dto;
    }
}