package com.pcgs.kafka.producer.product.service.service;

import com.pcgs.kafka.producer.product.service.model.CreateProductRestModel;

public interface ProductService {
    String createProduct(CreateProductRestModel productRestModel) throws Exception ;
}
