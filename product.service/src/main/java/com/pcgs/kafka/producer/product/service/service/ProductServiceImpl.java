package com.pcgs.kafka.producer.product.service.service;

import com.pcgs.kafka.producer.product.service.model.CreateProductRestModel;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public String createProduct(CreateProductRestModel productRestModel) throws Exception {
        return "";
    }
}
