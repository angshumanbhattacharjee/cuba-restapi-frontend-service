package com.company.restapifrontendservice.web.screens.order;

import com.company.restapifrontendservice.entity.Order;
import com.company.restapifrontendservice.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.screen.*;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

import javax.inject.Inject;
import java.util.*;

@UiController("restapifrontendservice_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
@LoadDataBeforeShow
@Log4j
public class OrderBrowse extends StandardLookup<Order> {

    List<Order> orderList = new ArrayList<>();
    @Inject
    OrderService orderService;
    @Inject
    private Notifications notifications;
    @Inject
    private GroupTable<Order> ordersTable;

    ObjectMapper mapper = new ObjectMapper();

    @Subscribe
    public void onInit (InitEvent initEvent) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("", "");
        Object object = mapper.writeValueAsString(map);
        orderList = orderService.getAllOrders(object);
        log.info("Fetching all Order Items from Backend Service");
    }

    @Install(to = "ordersDl", target = Target.DATA_LOADER)
    private List<Order> ordersDlLoadDelegate(LoadContext<Order> loadContext) {
        return orderList;
    }

    @Install(to = "ordersTable.edit", subject = "afterCommitHandler")
    private void ordersTableEditAfterCommitHandler(Order order) throws JsonProcessingException {
        log.info("Received order entity for Edit request with order Id: "+ order.getId());
        try {
            Order order1 = orderService.createNewOrder(order);
            if (order1 != null){
                notifications.create().withCaption("Order edited").show();
                log.info("Order entity updated for order Id: "+ order.getId());
            }
            else{
                notifications.create().withCaption("Error in editing Order Item").show();
                log.info("Error occurred in updating Order entity for order Id: "+ order.getId());
            }
        } catch (Exception e) {
            notifications.create().withCaption("Error occurred while processing request").show();
            log.error("Error occurred while processing request: "+ e.getMessage());
        }
    }

    @Install(to = "ordersTable.create", subject = "afterCommitHandler")
    private void ordersTableCreateAfterCommitHandler(Order order) throws JsonProcessingException {
        log.info("Received new order entity for Create request with order Id: "+ order.getId());
        try {
            Order order1 = orderService.createNewOrder(order);
            if (order1 != null) {
                notifications.create().withCaption("New Order Created").show();
                log.info("Created new order entity with order Id: "+ order1.getId());
            }
            else {
                notifications.create().withCaption("Error Occurred while creating new Order").show();
            }
        } catch (Exception e) {
            notifications.create().withCaption("Error occurred while processing request").show();
            log.error("Error occurred while processing request: "+ e.getMessage());
        }
    }

    @Subscribe("ordersTable.remove")
    public void onOrdersTableRemove(Action.ActionPerformedEvent event) throws Exception {
        String orderId = null;
        String response;
        try {
            Set<Order> set = ordersTable.getSelected();
            for (Order order: set) {
                orderId = order.getId().toString();
            }
            response = orderService.deleteOrderByOrderId(orderId);
            if (response != null){
                notifications.create().withCaption(response).show();
            }
            else notifications.create().withCaption("Error in deleting Order Item");
        } catch (Exception e) {
            notifications.create().withCaption("Error occurred while processing request").show();
            log.error("Error occurred while processing request: "+ e.getMessage());
        }
    }
}