package com.petlog.record.infrastructure.rabbitmq;

import com.petlog.record.dto.CoinEarnMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class CoinEventProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.coin.exchange}")
    private String coinExchange;

    @Value("${rabbitmq.coin.earn-routing-key}")
    private String coinEarnRoutingKey;

    public void publishRecapCoinEarned(Long userId, Long recapId) {
        CoinEarnMessage message = CoinEarnMessage.builder()
                .userId(userId)
                .amount(30L)
                .type("RECAP")
                .reason("AI_RECAP_CREATED")
                .targetId(recapId)
                .occurredAt(LocalDateTime.now())
                .build();

        rabbitTemplate.convertAndSend(coinExchange, coinEarnRoutingKey, message);
        log.info("[RabbitMQ] 코인 적립 메시지 발행 완료: userId={}, recapId={}, amount={}",
                userId, recapId, message.getAmount());
    }
}
