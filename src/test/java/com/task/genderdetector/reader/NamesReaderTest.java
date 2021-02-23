package com.task.genderdetector.reader;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
class NamesReaderTest{

    @Autowired
    NamesReader reader;

    @Test
    void getMaleNames() throws FileReaderException {
        //Given & When
        List<String> result = reader.getMaleNames();

        //Then
        assertNotNull(result);
        assertTrue(result.size() > 0);
    }

    @Test
    void getFemaleNames() throws FileReaderException {
        //Given & When
        List<String> result = reader.getFemaleNames();

        //Then
        assertNotNull(result);
        assertTrue(result.size() > 0);
    }
}