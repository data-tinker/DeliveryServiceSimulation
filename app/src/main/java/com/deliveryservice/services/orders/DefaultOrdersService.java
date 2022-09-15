package com.deliveryservice.services.orders;

import com.deliveryservice.App;
import com.deliveryservice.config.AppConfig;
import com.deliveryservice.models.domain.Order;
import com.deliveryservice.models.entities.OrderEntity;
import com.deliveryservice.models.errors.OrdersLoadError;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.deliveryservice.mappers.OrderMapper.mapOrderEntityToOrderDomain;

public class DefaultOrdersService implements OrdersService {
    private final ObjectMapper mapper;
    private final Iterator<OrderEntity> iterator;

    public DefaultOrdersService() {
        this.mapper = new ObjectMapper();
        List<OrderEntity> orderEntities = loadOrders();
        this.iterator = orderEntities.iterator();
    }

    private List<OrderEntity> loadOrders() {
        String dataFilePath = AppConfig.getInstance().getDataFilePath();
        try {
            InputStream inputStream = App.class.getClassLoader().getResourceAsStream(dataFilePath);
            return Arrays.asList(mapper.readValue(inputStream, OrderEntity[].class));
        } catch (IOException e) {
            throw new OrdersLoadError(dataFilePath);
        }
    }

    @Override
    public Order getNext() {
        return mapOrderEntityToOrderDomain(iterator.next());
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }
}
