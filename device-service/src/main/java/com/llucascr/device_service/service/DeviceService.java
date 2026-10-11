package com.llucascr.device_service.service;

import com.llucascr.device_service.dto.DeviceDto;
import com.llucascr.device_service.entity.Device;
import com.llucascr.device_service.exception.DeviceNotFoundException;
import com.llucascr.device_service.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public DeviceDto createDevice(DeviceDto input) {
        final Device device = Device.builder()
                .name(input.name())
                .type(input.type())
                .location(input.location())
                .userId(input.userId())
                .build();

        final Device savedDevice = deviceRepository.save(device);
        return toDto(savedDevice);
    }

    public List<DeviceDto> getAllDevices() {
        return deviceRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public DeviceDto getDeviceById(Long id) {
        return toDto(findDeviceById(id));
    }

    public DeviceDto updateDevice(Long id, DeviceDto input) {
        Device device = findDeviceById(id);

        device.setName(input.name());
        device.setType(input.type());
        device.setLocation(input.location());
        device.setUserId(input.userId());

        return toDto(deviceRepository.save(device));
    }

    public void deleteDevice(Long id) {
        Device device = findDeviceById(id);
        deviceRepository.delete(device);
    }

    private Device findDeviceById(Long id) {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException("Device not found with id: " + id));
    }

    private DeviceDto toDto(Device device) {
        return DeviceDto.builder()
                .id(device.getId())
                .name(device.getName())
                .type(device.getType())
                .location(device.getLocation())
                .userId(device.getUserId())
                .build();
    }

}
