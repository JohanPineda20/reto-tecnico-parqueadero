package com.nelumbo.parking.application.handler.impl;

import com.nelumbo.parking.application.dto.request.HistorialRequest;

import com.nelumbo.parking.application.handler.IHistorialHandler;
import com.nelumbo.parking.domain.model.VehicleModel;
import com.nelumbo.parking.domain.ports.in.IHistorialServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
