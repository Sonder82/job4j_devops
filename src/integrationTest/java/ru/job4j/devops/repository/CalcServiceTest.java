package ru.job4j.devops.repository;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.job4j.devops.config.ContainersConfig;
import ru.job4j.devops.models.CalcEvent;
import ru.job4j.devops.models.User;
import ru.job4j.devops.service.CalcService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CalcServiceTest extends ContainersConfig {

    @Autowired
    private CalcEventRepository calcEventRepository;

    @Autowired
    private CalcService calcService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldReturnFiveWhenTwoPlusThree() {
        User testUser = new User();
        testUser.setName("Test");
        userRepository.save(testUser);
        Long calcEventId = calcService.add(testUser, 2, 3);
        Optional<CalcEvent> testCalcEvent = calcEventRepository.findById(calcEventId);
        assertThat(testCalcEvent).isPresent();
        assertThat(testCalcEvent.get().getResult()).isEqualTo(5);
        assertThat(testCalcEvent.get().getUserId()).isEqualTo(testUser.getId());
    }
}
