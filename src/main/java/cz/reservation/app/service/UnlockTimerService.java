package cz.reservation.app.service;

import cz.reservation.app.model.ReservableState;
import cz.reservation.app.model.dto.ReservableScheduleUpdateEventDto;
import cz.reservation.app.model.entity.ReservableSchedule;
import cz.reservation.app.model.event.ReservableScheduleEvent;
import cz.reservation.app.repository.JpaReservableScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UnlockTimerService {

    @Autowired
    private JpaReservableScheduleRepository reservableScheduleRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private TransactionService transactionService;


    private long UNLOCK_AFTER = 60000L;


    //todo impl mechanism for canceling timertasks in case or lock change https://stackoverflow.com/questions/29349725/get-instance-of-a-running-timer-in-java

    public void scheduleUnlockTimer(UUID sessionId, Long reservableScheduleId){
        TimerTask task = new TimerTask() {

            public void run() {
                transactionService.executeTransaction(() -> {
                    Optional<ReservableSchedule> foundReservableSchedule = reservableScheduleRepository.findBySessionIdAndId(sessionId, reservableScheduleId);
                    if(foundReservableSchedule.isPresent()){
                        ReservableSchedule reservableScheduleToUnlock = foundReservableSchedule.get();
                        reservableScheduleToUnlock.setReservableState(ReservableState.AVAILABLE);
                        reservableScheduleToUnlock.setSessionId(null);
                        reservableScheduleRepository.save(reservableScheduleToUnlock);
                        applicationEventPublisher.publishEvent(ReservableScheduleEvent.builder().reservableScheduleUpdateEventDtoList(
                                List.of(ReservableScheduleUpdateEventDto.builder().id(reservableScheduleToUnlock.getId()).newReservableState(reservableScheduleToUnlock.getReservableState()).build())
                        ).build());
                    }
                });
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, UNLOCK_AFTER);
    }
}
