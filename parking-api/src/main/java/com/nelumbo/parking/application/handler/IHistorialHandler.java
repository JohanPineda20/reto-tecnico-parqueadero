package com.nelumbo.parking.application.handler;

import com.nelumbo.parking.application.dto.request.HistorialRequest;

import java.util.Map;

public interface IHistorialHandler {
    Map<String, Long> registerVehicleEntry(HistorialRequest vehicleEntryRequest);
    Map<String, String> registerVehicleDeparture(HistorialRequest vehicleDepartureRequest);
}
