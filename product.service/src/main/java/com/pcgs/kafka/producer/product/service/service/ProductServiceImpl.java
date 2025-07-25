package com.pcgs.kafka.producer.product.service.service;

import com.pcgs.kafka.producer.product.service.events.ProductCreatedEvent;
import com.pcgs.kafka.producer.product.service.model.CreateProductRestModel;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductServiceImpl implements ProductService {

    KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
    private final Logger LOGGER  = LoggerFactory.getLogger(this.getClass());

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(CreateProductRestModel productRestModel) throws Exception {
        String productId = UUID.randomUUID().toString();

        // TODO: Persist Product Details into database table before publishing an Event

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productId,
                productRestModel.getTitle(), productRestModel.getPrice(),
                productRestModel.getQuantity());

        LOGGER.info("Before publishing a ProductCreatedEvent");


        ProducerRecord<String, ProductCreatedEvent> record = new ProducerRecord<>(
                "product-created-events-topic",
                productId,
                productCreatedEvent);
        record.headers().add("messageId", UUID.randomUUID().toString().getBytes());

        SendResult<String, ProductCreatedEvent> result =
                kafkaTemplate.send(record).get();

//        // Sync call
//        SendResult<String, ProductCreatedEvent> result =
//                kafkaTemplate.send("product-created-events-topic",productId, productCreatedEvent).get();

        //Async call

//        CompletableFuture<SendResult<String, ProductCreatedEvent>> future =
//                kafkaTemplate.send("product-created-events-topic",productId, productCreatedEvent);
//
//        future.whenComplete((result1, exception) -> {
//            if (exception != null) {
//                LOGGER.error("Error in sending product created event", exception.getMessage());
//            }else {
//                LOGGER.info("Product created event sent successfully", result1.getRecordMetadata());
//            }
//        });

        // Makes Asyc call as synchronous
        // future.join();

        LOGGER.info("Partition: " + result.getRecordMetadata().partition());
        LOGGER.info("Topic: " + result.getRecordMetadata().topic());
        LOGGER.info("Offset: " + result.getRecordMetadata().offset());

        LOGGER.info("***** Returning product id");

        return productId;
    }
}
