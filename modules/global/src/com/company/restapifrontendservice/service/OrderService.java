package com.company.restapifrontendservice.service;

import com.company.restapifrontendservice.entity.Order;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface OrderService {
    String NAME = "restapifrontendservice_OrderService";

    List<Order> getAllOrders (Object object) throws Exception;

    Order createNewOrder(Order order) throws JsonProcessingException;

    String deleteOrderByOrderId(String OrderId) throws Exception;
}