package ru.job4j.devops.service;

import org.springframework.stereotype.Service;
import ru.job4j.devops.enums.CalcEventType;
import ru.job4j.devops.models.CalcEvent;
import ru.job4j.devops.models.User;
import ru.job4j.devops.repository.CalcEventRepository;

@Service
public class CalcService {

    private CalcEventRepository calcEventRepository;

    public CalcService(CalcEventRepository calcEventRepository) {
        this.calcEventRepository = calcEventRepository;
    }

    public Long add(User user, int first, int second) {
        CalcEvent calcEvent = new CalcEvent();
        calcEvent.setUserId(user.getId());
        calcEvent.setFirst(first);
        calcEvent.setSecond(second);
        calcEvent.setResult((double) first + second);
        calcEvent.setCalcEventType(CalcEventType.ADDITIONAL);
        return calcEventRepository.save(calcEvent).getUserId();
    }
}
