package com.petlog.record.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoinEarnMessage {

    private Long userId;
    private Long amount;
    private String type;
    private String reason;
    private Long targetId;
    private LocalDateTime occurredAt;
}
