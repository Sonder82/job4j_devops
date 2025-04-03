package ru.job4j.devops.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.job4j.devops.enums.CalcEventType;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "calc_event")
public class CalcEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first")
    private double first;

    @Column(name = "second")
    private double second;

    @Column(name = "result")
    private double result;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "calc_event_type")
    private CalcEventType calcEventType;
}
