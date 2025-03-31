package ru.job4j.devops.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.devops.enums.CalcEventType;
import ru.job4j.devops.models.Result;
import ru.job4j.devops.models.TwoArgs;
import ru.job4j.devops.service.ResultService;

import java.util.List;

@RestController
@RequestMapping("calc")
@AllArgsConstructor
public class CalcController {

    private final ResultService resultService;

    @PostMapping("summarise")
    public ResponseEntity<Result> summarise(@RequestBody TwoArgs twoArgs) {
        var first = twoArgs.getFirst();
        var second = twoArgs.getSecond();

        Result result = new Result();
        result.setFirst(first);
        result.setSecond(second);
        result.setResult(first + second);
        result.setCalcEventType(CalcEventType.ADDITIONAL);
        resultService.save(result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/")
    public ResponseEntity<List<Result>> logs() {
        return ResponseEntity.ok(resultService.findAll());
    }
}
