package com.nelumbo.parking.infraestructure.out.jpa.adapter;

import com.nelumbo.parking.domain.model.HistorialModel;
import com.nelumbo.parking.domain.ports.out.IHistorialPersistencePort;
import com.nelumbo.parking.infraestructure.out.jpa.mapper.HistorialEntityMapper;
import com.nelumbo.parking.infraestructure.out.jpa.repository.HistorialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class HistorialPostgresqlAdapter implements IHistorialPersistencePort {

    private final HistorialRepository historialRepository;
    private final HistorialEntityMapper historialEntityMapper;

    @Override
    public List<HistorialModel> getAllHistorialByParkingIdAndDepartureDateIsNull(Long parkingId) {
        return historialRepository.findByParkingIdAndDepartureDateIsNull(parkingId)
                .stream()
                .map(historialEntityMapper::toHistorialModel)
                .collect(Collectors.toList());
    }
    @Override
    public HistorialModel findByVehicleIdAndDepartureDateIsNull(Long vehicleId) {
        return historialEntityMapper.toHistorialModel(historialRepository.findByVehicleIdAndDepartureDateIsNull(vehicleId));
    }
    @Override
    public HistorialModel save(HistorialModel historialModel) {
        return historialEntityMapper.toHistorialModel(historialRepository.save(historialEntityMapper.toHistorialEntity(historialModel)));
    }
    @Override
    public List<HistorialModel> getAllHistorialByParkingIdAndDepartureDateIsNullPagination(Integer page, Integer size, Long parkingId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "vehicle.licensePlate"));
        return historialRepository.findByParkingIdAndDepartureDateIsNull(pageable, parkingId).getContent()
                .stream()
                .map(historialEntityMapper::toHistorialModel)
                .collect(Collectors.toList());
    }
    @Override
    public HistorialModel findByVehicleIdAndParkingIdAndDepartureDateIsNull(Long vehicleId, Long parkingId) {
        return historialEntityMapper.toHistorialModel(historialRepository.findByVehicleIdAndParkingIdAndDepartureDateIsNull(vehicleId, parkingId));
    }

    @Override
    public List<Object[]> getTop10MostParkedVehiclesInSocioParkings(Long socioId) {
        return historialRepository.getTop10MostParkedVehiclesInSocioParkings(socioId);
    }

    @Override
    public List<Object[]> getTop10MostParkedVehicles() {
        return historialRepository.getTop10MostParkedVehicles();
    }

    @Override
    public List<Object[]> getTop10MostParkedVehiclesByParking(Long parkingId) {
        return historialRepository.getTop10MostParkedVehiclesByParking(parkingId);
    }

    @Override
    public List<Object[]> getFirstTimeParkedVehiclesByParking(Long parkingId) {
        return historialRepository.getFirstTimeParkedVehiclesByParking(parkingId);
    }

    @Override
    public List<HistorialModel> getVehicleByLicensePlateInSocioParkings(Long socioId, String licensePlate) {
        return historialRepository.findByVehicleLicensePlateContainingIgnoreCaseAndDepartureDateIsNullInSocioParkings(socioId, licensePlate)
                .stream()
                .map(historialEntityMapper::toHistorialModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<HistorialModel> getVehicleByLicensePlate(String licensePlate) {
        return historialRepository.findByVehicleLicensePlateContainingIgnoreCaseAndDepartureDateIsNull(licensePlate)
                .stream()
                .map(historialEntityMapper::toHistorialModel)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, BigDecimal> getCashIncomeByParking(Long parkingId, LocalDate date){
        Map<String, BigDecimal> cashIncomes = new HashMap<>();
        int week = date.get(WeekFields.ISO.weekOfWeekBasedYear());
        int month = date.getMonthValue();
        int year = date.getYear();
        cashIncomes.put("today",historialRepository.findTotalPaymentByDay(parkingId, date));
        cashIncomes.put("week", historialRepository.findTotalPaymentByWeek(parkingId, week, year ));
        cashIncomes.put("month",historialRepository.findTotalPaymentByMonth(parkingId, month, year));
        cashIncomes.put("year", historialRepository.findTotalPaymentByYear(parkingId, year));
        return cashIncomes;
    }
}
