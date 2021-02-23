package com.task.genderdetector.reader;

import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class NamesReader {
    public List<String> getMaleNames() throws FileReaderException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("files/maleNames.txt").getFile());

        return getList(file);
    }

    public List<String> getFemaleNames() throws FileReaderException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("files/femaleNames.txt").getFile());

        return getList(file);
    }

    private List<String> getList(File file) throws FileReaderException {
        try (Stream<String> fileLines = Files.lines(Paths.get(file.getPath()))) {
            return fileLines.map(s -> s.toUpperCase(Locale.ROOT))
                    .distinct().sorted().collect(Collectors.toList());
        } catch (IOException e) {
            throw new FileReaderException();
        }
    }
}
