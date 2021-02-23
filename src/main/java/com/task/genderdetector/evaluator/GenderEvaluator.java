package com.task.genderdetector.evaluator;

import com.task.genderdetector.domain.Gender;
import com.task.genderdetector.reader.FileReaderException;
import com.task.genderdetector.reader.NamesReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class GenderEvaluator {

    private final static String INCONCLUSIVE = "INCONCLUSIVE";
    private final NamesReader reader;

    @Autowired
    public GenderEvaluator(NamesReader reader) {
        this.reader = reader;
    }

    public String evaluateGender(boolean byAllNames, String name) throws FileReaderException {
        String[] namesArray = name.split(" ");
        List<String> maleNames = reader.getMaleNames();
        List<String> femaleNames = reader.getFemaleNames();
        if (!byAllNames) {
            String nameToCheck = namesArray[0];
            if (maleNames.contains(nameToCheck.toUpperCase(Locale.ROOT))) {
                return Gender.MALE.name();
            } else if (femaleNames.contains(nameToCheck.toUpperCase(Locale.ROOT))) {
                return Gender.FEMALE.name();
            }
            return INCONCLUSIVE;
        }
        int maleCount = (int) Arrays.stream(namesArray)
                .filter(s -> maleNames.contains(s.toUpperCase(Locale.ROOT))).count();
        int femaleCount = (int) Arrays.stream(namesArray)
                .filter(s -> femaleNames.contains(s.toUpperCase(Locale.ROOT))).count();

        if (maleCount > femaleCount) {
            return Gender.MALE.name();
        } else if (maleCount < femaleCount) {
            return Gender.FEMALE.name();
        }
        return INCONCLUSIVE;
    }

    public NamesReader getReader() {
        return reader;
    }
}
