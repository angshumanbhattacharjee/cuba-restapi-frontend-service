package com.company.restapifrontendservice.service;

import com.company.restapifrontendservice.entity.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service(OrderService.NAME)
@Log4j
public class OrderServiceBean implements OrderService {

    RestTemplate restTemplate = new RestTemplate();

    ObjectMapper mapper = new ObjectMapper();

    /*
    * Method connects to backend service application using REST Template
    * Fetches Order Items from backend API based on criteria fields
    * @Params Object values containing order fields
    * @Returns List of Order Entities
    * */
    @Override
    public List<Order> getAllOrders(Object object) {
        List<Order> orderObjects = new ArrayList<>();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(object.toString(), header);
        List<Map<String, Object>> response;
        try {
            String restServiceUri = "http://localhost:8585/api/v2/order-service/getOrdersByCriteria";
            response = restTemplate.exchange(restServiceUri, HttpMethod.POST, request, List.class).getBody();
            if(response != null && !response.isEmpty()) {
                int i=0;
                for (Map<String, Object> entry : response) {
                    Order order = new Order();
                    order.setId(UUID.fromString((String) entry.get("orderId")));
                    order.setNumber((String) entry.get("number"));
                    order.setItems((String) entry.get("items"));
                    order.setDate((String) entry.get("date"));
                    order.setDescription((String) entry.get("description"));
                    orderObjects.add(i, order);
                    i++;
                }
            }
        } catch (Exception e) {
            log.info("Error occurred while processing request: "+ e.getMessage());
        }
        return orderObjects;
    }

    /*
     * Method connects to backend service application using REST Template
     * Creates a new Order Entity using backend API
     * @Params Order entity object
     * @Returns Order entity object created in the database
     * */
    @Override
    public Order createNewOrder(Order order) throws JsonProcessingException {
        Map<String, Object> orderNameMap;
        orderNameMap = prepareObject(order);
        Object object = mapper.writeValueAsString(orderNameMap);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(object.toString(), header);
        Map<String, Object> map = new HashMap<>();
        Order order2 = new Order();
        try {
            String restServiceUri = "http://localhost:8585/api/v2/order-service/createOrder";
            order2 = restTemplate.postForObject(restServiceUri, request, Order.class);
        } catch (Exception e) {
            log.info("Error occurred while processing request: "+ e.getMessage());
        }
        return order2;
    }

    /*
     * Method connects to backend service application using REST Template
     * Deletes Order Entity based on orderId field
     * @Params String orderId
     * @Returns String response
     * */
    @Override
    public String deleteOrderByOrderId(String OrderId) throws Exception {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(OrderId, header);
        String response = "";
        try {
            String restServiceUri = "http://localhost:8585/api/v2/order-service/deleteOrderById";
            response = restTemplate.exchange(restServiceUri, HttpMethod.POST, request, String.class).getBody();
        } catch (Exception e) {
            log.info("Error occurred while processing request: "+ e.getMessage());
        }
        return response;
    }

    /*
     * Method used to prepare Object model for new creation or update
     * @Params Order entity
     * @Returns Map object containing the Order fields and values in <K,V> format
     * */
    private Map<String, Object> prepareObject(Order order) {
        Map<String, Object> orderNameMap = new HashMap<>();
        if(order.getId() != null){
            orderNameMap.put("orderId", order.getUuid());
            orderNameMap.put("number", order.getNumber());
            orderNameMap.put("items", order.getItems());
            orderNameMap.put("description", order.getDescription());
            orderNameMap.put("date", order.getDate());
        }
        else {
            orderNameMap.put("number", order.getNumber());
            orderNameMap.put("items", order.getItems());
            orderNameMap.put("description", order.getDescription());
            orderNameMap.put("date", order.getDate());
        }
        log.info("Order Entity prepared for POST request");
        return orderNameMap;
    }
}