package ru.job4j.devops.service;

import ru.job4j.devops.models.Result;

import java.util.List;

public interface ResultService {
    Long save(Result result);

    List<Result> findAll();
}

