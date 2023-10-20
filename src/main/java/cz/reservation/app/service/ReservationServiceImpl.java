package cz.reservation.app.service;

import cz.reservation.app.ErrorCode;
import cz.reservation.app.model.ReservableState;
import cz.reservation.app.model.dto.ReservableScheduleDto;
import cz.reservation.app.model.dto.ReservableScheduleUpdateEventDto;
import cz.reservation.app.model.dto.ReservationBaseDto;
import cz.reservation.app.model.dto.ReservationDetailDto;
import cz.reservation.app.model.entity.ReservableSchedule;
import cz.reservation.app.model.entity.Reservation;
import cz.reservation.app.model.event.ReservableScheduleEvent;
import cz.reservation.app.repository.JpaReservableScheduleRepository;
import cz.reservation.app.repository.JpaReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private JpaReservationRepository reservationRepository;

    @Autowired
    private JpaReservableScheduleRepository reservableScheduleRepository;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    UnlockTimerService unlockTimerService;

    @Transactional
    @Override
    public ReservationDetailDto createReservation(ReservationBaseDto reservationBaseDto) throws Exception {
        Optional<ReservableSchedule> foundReservableSchedule = reservableScheduleRepository.findById(reservationBaseDto.getReservableScheduleId());

        if (foundReservableSchedule.isEmpty()) {
            throw new Exception(ErrorCode.RESERVABLE_SCHEDULE_NOT_FOUND.getKey());
        }
        ReservableSchedule targetReservableSchedule = foundReservableSchedule.get();

        UUID sessionId = UUID.fromString(RequestContextHolder.getRequestAttributes().getSessionId());

        if (!targetReservableSchedule.getReservableState().equals(ReservableState.LOCKED) &&
                !targetReservableSchedule.getSessionId().equals(sessionId)) {
            throw new Exception(ErrorCode.RESERVABLE_SCHEDULE_NOT_AVAILABLE.getKey());
        }

        Reservation reservation = conversionService.convert(reservationBaseDto, Reservation.class);
        reservation.setReservationCode(UUID.randomUUID().toString());
        Reservation createdReservation = reservationRepository.saveAndFlush(reservation);
        targetReservableSchedule.setReservation(createdReservation);
        targetReservableSchedule.setReservableState(ReservableState.UNAVAILABLE);
        targetReservableSchedule.setSessionId(null);
        reservableScheduleRepository.save(targetReservableSchedule);
        applicationEventPublisher.publishEvent(ReservableScheduleEvent.builder().reservableScheduleUpdateEventDtoList(
                List.of(ReservableScheduleUpdateEventDto.builder().id(targetReservableSchedule.getId()).newReservableState(targetReservableSchedule.getReservableState()).build())
        ).build());
        return conversionService.convert(createdReservation, ReservationDetailDto.class);
    }

    @Transactional
    @Override
    public ReservableScheduleDto lockReservableSchedule(Long reservableScheduleId) throws Exception {
        Optional<ReservableSchedule> foundReservableSchedule = reservableScheduleRepository.findById(reservableScheduleId);

        if (foundReservableSchedule.isEmpty()) {
            throw new Exception(ErrorCode.RESERVABLE_SCHEDULE_NOT_FOUND.getKey());
        }

        ReservableSchedule targetReservableSchedule = foundReservableSchedule.get();
        if (!targetReservableSchedule.getReservableState().equals(ReservableState.AVAILABLE)) {
            throw new Exception(ErrorCode.RESERVABLE_SCHEDULE_NOT_AVAILABLE.getKey());
        }

        List<ReservableScheduleUpdateEventDto> reservableScheduleUpdateEventDtoList = new ArrayList<>();

        UUID sessionId = UUID.fromString(RequestContextHolder.getRequestAttributes().getSessionId());
        Optional<ReservableSchedule> foundAlreadyLockedReservableSchedule = reservableScheduleRepository.findBySessionId(sessionId);
        if(foundAlreadyLockedReservableSchedule.isPresent()){
            unlockTimerService.cancelUnlockTimer(sessionId);
            ReservableSchedule alreadyLockedReservableSchedule = foundAlreadyLockedReservableSchedule.get();
            alreadyLockedReservableSchedule.setReservableState(ReservableState.AVAILABLE);
            alreadyLockedReservableSchedule.setSessionId(null);
            reservableScheduleUpdateEventDtoList
                    .add(ReservableScheduleUpdateEventDto.builder()
                            .id(alreadyLockedReservableSchedule.getId())
                            .newReservableState(alreadyLockedReservableSchedule.getReservableState())
                            .build());
            reservableScheduleRepository.saveAndFlush(alreadyLockedReservableSchedule);
        }

        targetReservableSchedule.setReservableState(ReservableState.LOCKED);
        targetReservableSchedule.setSessionId(sessionId);
        reservableScheduleUpdateEventDtoList
                .add(ReservableScheduleUpdateEventDto.builder()
                        .id(targetReservableSchedule.getId())
                        .newReservableState(targetReservableSchedule.getReservableState())
                        .build());
        reservableScheduleRepository.save(targetReservableSchedule);
        applicationEventPublisher.publishEvent(ReservableScheduleEvent.builder().reservableScheduleUpdateEventDtoList(reservableScheduleUpdateEventDtoList).build());
        unlockTimerService.scheduleUnlockTimer(sessionId, targetReservableSchedule.getId());
        return conversionService.convert(reservableScheduleRepository.save(targetReservableSchedule), ReservableScheduleDto.class);
    }

    @Transactional
    @Override
    public void deleteReservation(String reservationCode) throws Exception {
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
        applicationEventPublisher.publishEvent(ReservableScheduleEvent.builder().reservableScheduleUpdateEventDtoList(
                List.of(ReservableScheduleUpdateEventDto.builder().id(targetReservableSchedule.getId()).newReservableState(targetReservableSchedule.getReservableState()).build())
        ).build());

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
