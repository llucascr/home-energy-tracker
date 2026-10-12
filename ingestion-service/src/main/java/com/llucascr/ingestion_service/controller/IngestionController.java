package com.llucascr.ingestion_service.controller;

import com.llucascr.ingestion_service.dto.EnergyUsageDto;
import com.llucascr.ingestion_service.service.IngestionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/ingestion")
public class IngestionController {

    private IngestionService ingestionService;

    public IngestionController(IngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void ingestData(@RequestBody EnergyUsageDto energyUsageDto) {
        ingestionService.ingestData(energyUsageDto);
    }
}
