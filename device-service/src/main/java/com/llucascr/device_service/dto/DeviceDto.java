package com.llucascr.device_service.dto;

import com.llucascr.device_service.model.DeviceType;
import lombok.Builder;

@Builder
public record DeviceDto(
        Long id,
        String name,
        DeviceType type,
        String location,
        Long userId
) {
}
