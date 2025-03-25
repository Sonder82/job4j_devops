package ru.job4j.devops.listener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.job4j.devops.models.CalcEvent;
import ru.job4j.devops.repository.CalcEventRepository;

@Component
@AllArgsConstructor
@Slf4j
public class CalcEventListener {

    private CalcEventRepository calcEventRepository;

    @KafkaListener(topics = "signUpCalcEvent", groupId = "job4j")
    public void signUpCalcEvent(CalcEvent calcEvent) {
        log.debug("Received event: {}", calcEvent.getCalcEventType());
        calcEventRepository.save(calcEvent);
    }
}
