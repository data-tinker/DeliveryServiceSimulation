package com.deliveryservice.services.orders;

import com.deliveryservice.models.domain.Order;

public interface OrdersService {
    Order getNext();
    boolean hasNext();
}
