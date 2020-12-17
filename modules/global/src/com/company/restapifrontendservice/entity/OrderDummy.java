package com.company.restapifrontendservice.entity;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseUuidEntity;

import java.util.UUID;

@MetaClass(name = "restapifrontendservice_OrderDummy")
@NamePattern("%s|description")
public class OrderDummy extends BaseUuidEntity {
    private static final long serialVersionUID = 4928847599649451573L;

    @MetaProperty
    private String number;

    @MetaProperty
    private String items;

    @MetaProperty
    private String description;

    @MetaProperty
    private String date;

    @MetaProperty
    private UUID orderId;

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}