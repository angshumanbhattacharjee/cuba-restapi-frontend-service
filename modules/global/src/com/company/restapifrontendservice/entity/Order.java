package com.company.restapifrontendservice.entity;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseUuidEntity;

@MetaClass(name = "restapifrontendservice_Order")
@NamePattern("%s|description")
public class Order extends BaseUuidEntity {
    private static final long serialVersionUID = -8504933793782621815L;


    @MetaProperty
    private String number;

    @MetaProperty
    private String items;

    @MetaProperty
    private String description;

    @MetaProperty
    private String date;

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
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