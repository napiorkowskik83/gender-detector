package com.task.genderdetector.evaluator;

import com.task.genderdetector.domain.Gender;
import com.task.genderdetector.reader.FileReaderException;
import com.task.genderdetector.reader.NamesReader;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GenderEvaluatorTest {

    private final String INCONCLUSIVE = "INCONCLUSIVE";
    private final List<String> maleNames = Arrays.asList("ADAM", "JAN", "KRZYSZTOF", "MARIUSZ", "PAWEŁ", "PIOTR");
    private final List<String> femaleNames = Arrays.asList("ANNA", "GRAŻYNA", "JOANNA", "PAULINA", "MAGDALENA", "MARIA");

    @InjectMocks
    GenderEvaluator evaluator;

    @Mock
    NamesReader reader;

    @Test
    public void shouldReturnFemaleByOnlyFirstName() throws FileReaderException {
        //Given
        String name = "Maria Jan Rokita";

        when(reader.getMaleNames()).thenReturn(maleNames);
        when(reader.getFemaleNames()).thenReturn(femaleNames);


        //When
        String result = evaluator.evaluateGender(false, name);

        //Then
        assertEquals(Gender.FEMALE.name(), result);
    }

    @Test
    public void shouldReturnMaleByOnlyFirstName() throws FileReaderException {
        //Given
        String name = "Jan Maria Rokita";

        System.out.println(reader);

        when(reader.getMaleNames()).thenReturn(maleNames);
        when(reader.getFemaleNames()).thenReturn(femaleNames);

        System.out.println(maleNames);
        System.out.println(femaleNames);

        //When
        String result = evaluator.evaluateGender(false, name);

        //Then
        assertEquals(Gender.MALE.name(), result);
    }

    @Test
    public void shouldReturnInconclusiveByOnlyFirstName() throws FileReaderException {
        //Given
        String name = "Susan Maria Rokita";

        System.out.println(reader);

        when(reader.getMaleNames()).thenReturn(maleNames);
        when(reader.getFemaleNames()).thenReturn(femaleNames);

        System.out.println(maleNames);
        System.out.println(femaleNames);

        //When
        String result = evaluator.evaluateGender(false, name);

        //Then
        assertEquals(INCONCLUSIVE, result);
    }

    @Test
    public void shouldReturnFemale() throws FileReaderException {
        //Given
        String name = "Maria Jan Anna Rokita";

        when(reader.getMaleNames()).thenReturn(maleNames);
        when(reader.getFemaleNames()).thenReturn(femaleNames);


        //When
        String result = evaluator.evaluateGender(true, name);

        //Then
        assertEquals(Gender.FEMALE.name(), result);
    }

    @Test
    public void shouldReturnMale() throws FileReaderException {
        //Given
        String name = "Jan Maria Paweł Rokita";

        System.out.println(reader);

        when(reader.getMaleNames()).thenReturn(maleNames);
        when(reader.getFemaleNames()).thenReturn(femaleNames);

        //When
        String result = evaluator.evaluateGender(true, name);

        //Then
        assertEquals(Gender.MALE.name(), result);
    }

    @Test
    public void shouldReturnInconclusive() throws FileReaderException {
        //Given
        String name = "Grażyna Paweł Maria Krzysztof Rokita";

        System.out.println(reader);

        when(reader.getMaleNames()).thenReturn(maleNames);
        when(reader.getFemaleNames()).thenReturn(femaleNames);

        System.out.println(maleNames);
        System.out.println(femaleNames);

        //When
        String result = evaluator.evaluateGender(true, name);

        //Then
        assertEquals(INCONCLUSIVE, result);
    }
}