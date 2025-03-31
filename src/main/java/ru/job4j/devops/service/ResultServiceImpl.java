package ru.job4j.devops.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.devops.models.Result;
import ru.job4j.devops.repository.ResultRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;

    @Override
    public Long save(Result result) {
        return resultRepository.save(result).getId();
    }

    @Override
    public List<Result> findAll() {
        return resultRepository.findAll();
    }
}
