package com.service.bookverse.feature.order.service;

import com.service.bookverse.feature.auth.model.UserProfile;
import com.service.bookverse.feature.cart.model.Cart;
import com.service.bookverse.feature.cart.model.CartItem;
import com.service.bookverse.feature.cart.repository.CartRepository;
import com.service.bookverse.feature.order.dto.OrderItemDto;
import com.service.bookverse.feature.order.dto.OrderResponseDto;
import com.service.bookverse.feature.order.model.Order;
import com.service.bookverse.feature.order.model.OrderItem;
import com.service.bookverse.feature.order.model.OrderStatus;
import com.service.bookverse.feature.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    // if anything fails -> rollback
    @Transactional
    public void placeOrder(UserProfile userProfile) {

        Cart cart = cartRepository.findByUserProfile(userProfile)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUserProfile(userProfile);
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;


        for (CartItem cartItem : cart.getItems()) {

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());

            total += cartItem.getQuantity() * cartItem.getBook().getPrice();

            orderItems.add(orderItem);
        }

        order.setItems(orderItems);
        order.setTotalAmount(total);


        orderRepository.save(order);


        cart.getItems().clear();
        cartRepository.save(cart);
    }

    public List<OrderResponseDto> getUserOrders(UserProfile userProfile) {

        List<Order> orders = orderRepository.findByUserProfile(userProfile);

        List<OrderResponseDto> responseList = new ArrayList<>();

        for (Order order : orders) {

            List<OrderItemDto> itemDtos = new ArrayList<>();

            for (OrderItem item : order.getItems()) {

                OrderItemDto dto = new OrderItemDto();
                dto.setBookId(item.getBook().getId());
                dto.setTitle(item.getBook().getTitle());
                dto.setQuantity(item.getQuantity());
                dto.setPrice(item.getBook().getPrice());

                double total = item.getQuantity() * item.getBook().getPrice();
                dto.setTotal(total);

                itemDtos.add(dto);
            }

            OrderResponseDto response = new OrderResponseDto();
            response.setOrderId(order.getId());
            response.setItems(itemDtos);
            response.setTotalAmount(order.getTotalAmount());
            response.setStatus(order.getStatus().name());
            response.setCreatedAt(order.getCreatedAt());

            responseList.add(response);
        }

        return responseList;
    }
}