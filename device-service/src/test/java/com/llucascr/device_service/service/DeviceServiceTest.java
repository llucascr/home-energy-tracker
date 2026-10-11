package com.llucascr.device_service.service;

import com.llucascr.device_service.dto.DeviceDto;
import com.llucascr.device_service.entity.Device;
import com.llucascr.device_service.exception.DeviceNotFoundException;
import com.llucascr.device_service.model.DeviceType;
import com.llucascr.device_service.repository.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceService deviceService;

    @Test
    void shouldReturnDeviceWhenIdExists() {
        Device device = Device.builder()
                .id(1L)
                .name("Living room light")
                .type(DeviceType.LIGHT)
                .location("Living room")
                .userId(10L)
                .build();

        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));

        DeviceDto result = deviceService.getDeviceById(1L);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Living room light");
        assertThat(result.type()).isEqualTo(DeviceType.LIGHT);
        assertThat(result.location()).isEqualTo("Living room");
        assertThat(result.userId()).isEqualTo(10L);
    }

    @Test
    void shouldThrowExceptionWhenDeviceDoesNotExist() {
        when(deviceRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> deviceService.getDeviceById(99L))
                .isInstanceOf(DeviceNotFoundException.class)
                .hasMessage("Device not found with id: 99");
    }
}
