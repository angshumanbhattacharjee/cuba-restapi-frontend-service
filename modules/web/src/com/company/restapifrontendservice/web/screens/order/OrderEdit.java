package com.company.restapifrontendservice.web.screens.order;

import com.haulmont.cuba.gui.screen.*;
import com.company.restapifrontendservice.entity.Order;

@UiController("restapifrontendservice_Order.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
@LoadDataBeforeShow
public class OrderEdit extends StandardEditor<Order> {
}