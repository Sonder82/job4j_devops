package ru.job4j.devops.service;

import ru.job4j.devops.models.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultFakeService implements ResultService {
    private final Map<Long, Result> mem = new HashMap<>();
    private long genId = 0;

    @Override
    public Long save(Result result) {
        mem.put(genId++, result);
        return result.getId();
    }

    @Override
    public List<Result> findAll() {
        return new ArrayList<>(mem.values());
    }
}
