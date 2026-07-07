package com.sabrina.ecommerce.service;

import com.sabrina.ecommerce.entity.Order;
import com.sabrina.ecommerce.entity.OrderItem;
import com.sabrina.ecommerce.entity.OrderStatus;
import com.sabrina.ecommerce.entity.Product;
import com.sabrina.ecommerce.entity.User;
import com.sabrina.ecommerce.exception.BadRequestException;
import com.sabrina.ecommerce.exception.ResourceNotFoundException;
import com.sabrina.ecommerce.mapper.OrderMapper;
import com.sabrina.ecommerce.repository.OrderItemRepository;
import com.sabrina.ecommerce.repository.OrderRepository;
import com.sabrina.ecommerce.repository.ProductRepository;
import com.sabrina.ecommerce.repository.UserRepository;
import com.sabrina.ecommerce.service.model.request.OrderItemRequest;
import com.sabrina.ecommerce.service.model.response.OrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public OrderResponse createOrder(String email) {
        log.info("Création d'une commande pour : {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable"));

        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        order.setUser(user);
        order.setItems(new ArrayList<>());

        Order saved = orderRepository.save(order);
        return OrderMapper.toResponse(saved);
    }

    public OrderResponse addItem(Long orderId, OrderItemRequest request) {
        log.info("Ajout d'un produit à la commande {}", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Commande introuvable id : " + orderId));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new BadRequestException("Impossible de modifier une commande " + order.getStatus());
        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Produit introuvable"));

        if (product.getStock() < request.getQuantity()) {
            throw new BadRequestException("Stock insuffisant. Disponible : " + product.getStock());
        }

        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(request.getQuantity());
        item.setPrice(product.getPrice());
        orderItemRepository.save(item);

        product.setStock(product.getStock() - request.getQuantity());
        productRepository.save(product);

        log.info("Produit {} ajouté à la commande {}", product.getName(), orderId);

        order.getItems().add(item);
        return OrderMapper.toResponse(order);
    }

    public List<OrderResponse> getMyOrders(String email) {
        log.info("Récupération des commandes de : {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable"));

        return orderRepository.findByUserId(user.getId())
                .stream()
                .map(OrderMapper::toResponse)
                .toList();
    }

    public OrderResponse getOrderById(Long id) {
        log.info("Récupération de la commande {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande introuvable id : " + id));
        return OrderMapper.toResponse(order);
    }

    public OrderResponse updateStatus(Long id, String status) {
        log.info("Mise à jour du statut de la commande {} → {}", id, status);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande introuvable id : " + id));

        try {
            order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Statut invalide : " + status);
        }

        Order updated = orderRepository.save(order);
        return OrderMapper.toResponse(updated);
    }

    public List<OrderResponse> getAllOrders() {
        log.info("Récupération de toutes les commandes");
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toResponse)
                .toList();
    }
}