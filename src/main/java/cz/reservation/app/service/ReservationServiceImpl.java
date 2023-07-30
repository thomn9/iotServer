package cz.reservation.app.service;

import cz.reservation.app.ErrorCode;
import cz.reservation.app.model.ReservableState;
import cz.reservation.app.model.dto.ReservableScheduleBaseDto;
import cz.reservation.app.model.dto.ReservableScheduleDto;
import cz.reservation.app.model.dto.ReservationBaseDto;
import cz.reservation.app.model.dto.ReservationDetailDto;
import cz.reservation.app.model.entity.ReservableSchedule;
import cz.reservation.app.model.entity.Reservation;
import cz.reservation.app.repository.JpaReservableScheduleRepository;
import cz.reservation.app.repository.JpaReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private JpaReservationRepository reservationRepository;

    @Autowired
    private JpaReservableScheduleRepository reservableScheduleRepository;

    @Autowired
    private ConversionService conversionService;

    @Transactional
    @Override
    public List<ReservableScheduleDto> createReservation(ReservationBaseDto reservationBaseDto) throws Exception {
        Optional<ReservableSchedule> foundReservableSchedule = reservableScheduleRepository.findById(reservationBaseDto.getReservableScheduleId());

        if (foundReservableSchedule.isEmpty()) {
            throw new Exception(ErrorCode.RESERVABLE_SCHEDULE_NOT_FOUND.getKey());
        }

        ReservableSchedule targetReservableSchedule = foundReservableSchedule.get();

        if (!targetReservableSchedule.getReservableState().equals(ReservableState.AVAILABLE)) {
            throw new Exception(ErrorCode.RESERVABLE_SCHEDULE_NOT_AVAILABLE.getKey());
        }

        Reservation reservation = conversionService.convert(reservationBaseDto, Reservation.class);
        reservation.setReservationCode(UUID.randomUUID().toString());
        Reservation createdReservation = reservationRepository.saveAndFlush(reservation);
        targetReservableSchedule.setReservation(createdReservation);
        targetReservableSchedule.setReservableState(ReservableState.UNAVAILABLE);
        reservableScheduleRepository.save(targetReservableSchedule);
        return reservableScheduleRepository.findAll()
                .stream()
                .map(reservableSchedule -> conversionService.convert(reservableSchedule, ReservableScheduleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservableScheduleDto> lockReservableSchedule(Long reservableScheduleId) throws Exception {
        Optional<ReservableSchedule> foundReservableSchedule = reservableScheduleRepository.findById(reservableScheduleId);

        if (foundReservableSchedule.isEmpty()) {
            throw new Exception(ErrorCode.RESERVABLE_SCHEDULE_NOT_FOUND.getKey());
        }

        ReservableSchedule targetReservableSchedule = foundReservableSchedule.get();

        if (!targetReservableSchedule.getReservableState().equals(ReservableState.AVAILABLE)) {
            throw new Exception(ErrorCode.RESERVABLE_SCHEDULE_NOT_AVAILABLE.getKey());
        }
        targetReservableSchedule.setReservableState(ReservableState.LOCKED);
        reservableScheduleRepository.save(targetReservableSchedule);

        return reservableScheduleRepository.findAll()
                .stream()
                .map(reservableSchedule -> conversionService.convert(reservableSchedule,ReservableScheduleDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<ReservableScheduleDto> deleteReservation(String reservationCode) throws Exception {
        Optional<Reservation> foundReservation = reservationRepository.findByReservationCode(reservationCode);
        if (foundReservation.isEmpty()) {
            throw new Exception(ErrorCode.RESERVATION_NOT_FOUND.getKey());
        }

        Reservation targetReservation = foundReservation.get();

        ReservableSchedule targetReservableSchedule = reservableScheduleRepository.findByReservation(targetReservation.getId());
        targetReservableSchedule.setReservableState(ReservableState.AVAILABLE);
        targetReservableSchedule.setReservation(null);
        reservableScheduleRepository.save(targetReservableSchedule);
        reservationRepository.deleteByReservationCode(reservationCode);

        return reservableScheduleRepository.findAll()
                .stream()
                .map(reservableSchedule -> conversionService.convert(reservableSchedule,ReservableScheduleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReservationDetailDto getReservationDetail(String reservationCode) throws Exception {
        Optional<Reservation> foundReservation = reservationRepository.findByReservationCode(reservationCode);
        if (foundReservation.isEmpty()) {
            throw new Exception(ErrorCode.RESERVATION_NOT_FOUND.getKey());
        }
        Reservation targetReservation = foundReservation.get();
        return conversionService.convert(targetReservation,ReservationDetailDto.class);
    }



}
