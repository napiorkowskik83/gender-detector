package com.task.genderdetector.evaluator;


import com.task.genderdetector.domain.Gender;
import com.task.genderdetector.reader.FileReaderException;
import com.task.genderdetector.reader.NamesReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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

        when(reader.getMaleNames()).thenReturn(maleNames);
        when(reader.getFemaleNames()).thenReturn(femaleNames);

        //When
        String result = evaluator.evaluateGender(false, name);

        //Then
        assertEquals(Gender.MALE.name(), result);
    }

    @Test
    public void shouldReturnInconclusiveByOnlyFirstName() throws FileReaderException {
        //Given
        String name = "Susan Maria Rokita";

        when(reader.getMaleNames()).thenReturn(maleNames);
        when(reader.getFemaleNames()).thenReturn(femaleNames);

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

        when(reader.getMaleNames()).thenReturn(maleNames);
        when(reader.getFemaleNames()).thenReturn(femaleNames);

        //When
        String result = evaluator.evaluateGender(true, name);

        //Then
        assertEquals(INCONCLUSIVE, result);
    }
}