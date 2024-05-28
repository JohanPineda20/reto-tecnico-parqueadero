package com.nelumbo.parking.application.handler.impl;

import com.nelumbo.parking.application.dto.request.HistorialRequest;

import com.nelumbo.parking.application.dto.response.MetricVehicleResponse;
import com.nelumbo.parking.application.dto.response.VehicleResponse;
import com.nelumbo.parking.application.handler.IHistorialHandler;
import com.nelumbo.parking.domain.model.VehicleModel;
import com.nelumbo.parking.domain.ports.in.IHistorialServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class HistorialHandler implements IHistorialHandler {

    private final IHistorialServicePort historialServicePort;
    @Override
    public Map<String, Long> registerVehicleEntry(HistorialRequest historialRequest) {
        VehicleModel vehicleModel = new VehicleModel(null, historialRequest.getDescription(), historialRequest.getLicensePlate().toUpperCase());
        return historialServicePort.registerVehicleEntry(vehicleModel, historialRequest.getParkingId());
    }
    @Override
    public Map<String, String> registerVehicleDeparture(HistorialRequest historialRequest) {
        return historialServicePort.registerVehicleDeparture(historialRequest.getLicensePlate().toUpperCase(), historialRequest.getParkingId());
    }
    @Override
    public List<MetricVehicleResponse> getTop10MostParkedVehicles() {
        return historialServicePort.getTop10MostParkedVehicles()
                .stream()
                .map(objectArray -> {
                    VehicleResponse vehicleResponse = new VehicleResponse();
                    vehicleResponse.setId((Long) objectArray[0]);
                    vehicleResponse.setLicensePlate(objectArray[1].toString());
                    MetricVehicleResponse metricVehicleResponse = new MetricVehicleResponse();
                    metricVehicleResponse.setVehicle(vehicleResponse);
                    metricVehicleResponse.setCount((Long) objectArray[2]);
                    return metricVehicleResponse;
                }).toList();
    }
    @Override
    public List<MetricVehicleResponse> getTop10MostParkedVehiclesByParking(Long id) {
        return historialServicePort.getTop10MostParkedVehiclesByParking(id)
                .stream()
                .map(objectArray -> {
                    VehicleResponse vehicleResponse = new VehicleResponse();
                    vehicleResponse.setId((Long) objectArray[0]);
                    vehicleResponse.setLicensePlate(objectArray[1].toString());
                    MetricVehicleResponse metricVehicleResponse = new MetricVehicleResponse();
                    metricVehicleResponse.setVehicle(vehicleResponse);
                    metricVehicleResponse.setCount((Long) objectArray[2]);
                    return metricVehicleResponse;
                }).toList();
    }
    @Override
    public List<VehicleResponse> getFirstTimeParkedVehiclesByParking(Long id) {
        return historialServicePort.getFirstTimeParkedVehiclesByParking(id)
                .stream()
                .map(objectArray ->{
                    VehicleResponse vehicleResponse = new VehicleResponse();
                    vehicleResponse.setId((Long) objectArray[0]);
                    vehicleResponse.setLicensePlate(objectArray[1].toString());
                    return vehicleResponse;
                }).toList();
    }
    @Override
    public List<VehicleResponse> getVehicleByLicensePlate(String licensePlate) {
        return historialServicePort.getVehicleByLicensePlate(licensePlate)
                .stream()
                .map(historialModel -> {
                    VehicleResponse vehicleResponse = new VehicleResponse();
                    vehicleResponse.setId(historialModel.getVehicle().getId());
                    vehicleResponse.setLicensePlate(historialModel.getVehicle().getLicensePlate());
                    return vehicleResponse;
                }).toList();
    }
    @Override
    public Map<String, BigDecimal> getCashIncomeByParking(Long id) {
        return historialServicePort.getCashIncomeByParking(id);
    }
}
