package com.task.genderdetector.controller;

import com.task.genderdetector.domain.Gender;
import com.task.genderdetector.domain.NameList;
import com.task.genderdetector.evaluator.GenderEvaluator;
import com.task.genderdetector.reader.FileReaderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
public class GenderDetectController {

    private final GenderEvaluator evaluator;

    @Autowired
    public GenderDetectController(GenderEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    @GetMapping(value = "/detect/{byAllNames}/{name}")
    public String detectGender(@PathVariable Boolean byAllNames, @PathVariable String name) throws FileReaderException {
        return evaluator.evaluateGender(byAllNames, name);
    }

    @GetMapping(value = "/names/{gender}")
    public NameList getAvailableNames(@PathVariable Gender gender) throws FileReaderException {
        List<String> names = new ArrayList<>();
        if (Gender.FEMALE.equals(gender)) {
            names = evaluator.getReader().getFemaleNames();
        } else if (Gender.MALE.equals(gender)) {
            names = evaluator.getReader().getMaleNames();
        }
        return new NameList(names);
    }
}
