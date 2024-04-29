package com.nelumbo.parking.application.handler;

import com.nelumbo.parking.application.dto.request.HistorialRequest;
import com.nelumbo.parking.application.dto.response.MetricVehicleResponse;

import java.util.List;
import java.util.Map;

public interface IHistorialHandler {
    Map<String, Long> registerVehicleEntry(HistorialRequest vehicleEntryRequest);
    Map<String, String> registerVehicleDeparture(HistorialRequest vehicleDepartureRequest);
    List<MetricVehicleResponse> getTop10MostParkedVehicles();
    List<MetricVehicleResponse> getTop10MostParkedVehiclesByParking(Long id);
}
