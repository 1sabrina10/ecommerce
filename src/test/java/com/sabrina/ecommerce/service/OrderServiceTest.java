package com.sabrina.ecommerce.service;

import com.sabrina.ecommerce.entity.Order;
import com.sabrina.ecommerce.entity.OrderItem;
import com.sabrina.ecommerce.entity.OrderStatus;
import com.sabrina.ecommerce.entity.Product;
import com.sabrina.ecommerce.entity.Role;
import com.sabrina.ecommerce.entity.User;
import com.sabrina.ecommerce.repository.OrderItemRepository;
import com.sabrina.ecommerce.repository.OrderRepository;
import com.sabrina.ecommerce.repository.ProductRepository;
import com.sabrina.ecommerce.repository.UserRepository;
import com.sabrina.ecommerce.service.model.request.OrderItemRequest;
import com.sabrina.ecommerce.service.model.response.OrderResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderService orderService;

    private User user;
    private Order order;
    private Product product;
    private OrderItemRequest orderItemRequest;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("sabrina@test.com");
        user.setRole(Role.CLIENT);

        order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.PENDING);
        order.setUser(user);
        order.setItems(new ArrayList<>());

        product = new Product();
        product.setId(1L);
        product.setName("iPhone 15");
        product.setPrice(new BigDecimal("999.99"));
        product.setStock(50);

        orderItemRequest = new OrderItemRequest();
        orderItemRequest.setProductId(1L);
        orderItemRequest.setQuantity(2);
    }

    @Test
    void createOrder_shouldCreateAndReturnOrder() {
        // Mock
        doReturn(Optional.of(user)).when(userRepository).findByEmail(user.getEmail());
        doReturn(order).when(orderRepository).save(any(Order.class));

        // When
        OrderResponse result = orderService.createOrder(user.getEmail());

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo("PENDING");
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void addItem_shouldAddItemToOrder() {
        // Given
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(2);
        orderItem.setPrice(product.getPrice());

        doReturn(Optional.of(order)).when(orderRepository).findById(1L);
        doReturn(Optional.of(product)).when(productRepository).findById(1L);
        doReturn(orderItem).when(orderItemRepository).save(any(OrderItem.class));
        doReturn(product).when(productRepository).save(any(Product.class));

        // When
        OrderResponse result = orderService.addItem(1L, orderItemRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getItems()).hasSize(1);

        verify(orderItemRepository).save(any(OrderItem.class));
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void getMyOrders_shouldReturnOrdersOfUser() {
        // Mock
        doReturn(Optional.of(user)).when(userRepository).findByEmail(user.getEmail());
        doReturn(List.of(order)).when(orderRepository).findByUserId(1L);

        // When
        List<OrderResponse> result = orderService.getMyOrders(user.getEmail());

        // Then
        assertThat(result).isNotNull().hasSize(1);
        assertThat(result.getFirst().getStatus()).isEqualTo("PENDING");
    }

    @Test
    void getOrderById_shouldReturnOrder_whenExists() {
        // Mock
        doReturn(Optional.of(order)).when(orderRepository).findById(1L);

        // When
        OrderResponse result = orderService.getOrderById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getStatus()).isEqualTo("PENDING");
    }


}
