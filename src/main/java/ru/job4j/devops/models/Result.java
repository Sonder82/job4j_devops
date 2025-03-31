package ru.job4j.devops.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.job4j.devops.enums.CalcEventType;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "results")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first")
    private double first;

    @Column(name = "second")
    private double second;

    @Column(name = "result")
    private double result;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "calc_event_type")
    private CalcEventType calcEventType;
}
