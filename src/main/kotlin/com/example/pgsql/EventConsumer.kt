package com.example.pgsql

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.pulsar.client.api.SubscriptionType
import org.apache.pulsar.common.schema.SchemaType
import org.springframework.pulsar.annotation.PulsarListener
import org.springframework.stereotype.Service
import org.slf4j.LoggerFactory

@Service
class EventConsumer {

    private val log = LoggerFactory.getLogger(EventConsumer::class.java)

    @PulsarListener(
        topics = ["\${spring.pulsar.producer.topic-name1}"],
        subscriptionName = "my-subscription",
        subscriptionType = arrayOf(SubscriptionType.Shared)
    )
    fun consumeTextEvent(msg: String) {
        log.info("EventConsumer:: consumeTextEvent consumed events {}", msg)
    }

    @PulsarListener(
        topics = ["\${spring.pulsar.producer.topic-name2}"],
        subscriptionName = "my-subscription",
        schemaType = SchemaType.JSON,
        subscriptionType = arrayOf(SubscriptionType.Shared)
    )
    @Throws(JsonProcessingException::class)
    fun consumeRawEvent(customer: Customer) {
        log.info(
            "EventConsumer:: consumeTextEvent consumed events {}",
            ObjectMapper().writeValueAsString(customer)
        )
    }
}
