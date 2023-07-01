package com.bmo.common.market_service.core.configs;

import com.bmo.common.market_service.core.configs.properties.KafkaClusterProperties;
import com.bmo.common.market_service.core.configs.properties.KafkaConsumerProperties;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaConsumerConfig {

  @Bean
  @ConfigurationProperties("kafka.consumer.delivery-status-event")
  public KafkaConsumerProperties deliveryStatusEventConsumerProperties() {
    return new KafkaConsumerProperties();
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, Object> deliveryStatusEventListenerContainerFactory(
      KafkaConsumerProperties deliveryStatusEventConsumerProperties) {
    return createMultiObjectsKafkaListener(deliveryStatusEventConsumerProperties);
  }

  private <T> ConcurrentKafkaListenerContainerFactory<String, T> createMultiObjectsKafkaListener(
      KafkaConsumerProperties consumerProperties) {
    var containerFactory = new ConcurrentKafkaListenerContainerFactory<String, T>();

    JsonDeserializer<T> tJsonDeserializer = new JsonDeserializer<>();
    tJsonDeserializer.addTrustedPackages("*");

    var valueDeserializer = new ErrorHandlingDeserializer<>(tJsonDeserializer);

    containerFactory.setConsumerFactory(
        new DefaultKafkaConsumerFactory<>(
            buildConsumerConfigs(consumerProperties),
            new StringDeserializer(),
            valueDeserializer
        ));

    containerFactory.getContainerProperties().setAckMode(AckMode.MANUAL);

    return containerFactory;
  }

  private Map<String, Object> buildConsumerConfigs(KafkaConsumerProperties consumerProperties) {

    KafkaClusterProperties clusterProperties = consumerProperties.getCluster();

    Map<String, Object> clusterConfigs = Map.of(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, clusterProperties.getBootstrapServers(),
        ConsumerConfig.GROUP_ID_CONFIG, consumerProperties.getConsumerGroupId(),
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
        ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false"
    );
    Map<String, Object> securityConfigs = Map.of();
    Map<String, Object> trustStoreConfigs = Map.of();

    return Stream.of(clusterConfigs, securityConfigs, trustStoreConfigs)
        .flatMap(m -> m.entrySet().stream())
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
}
