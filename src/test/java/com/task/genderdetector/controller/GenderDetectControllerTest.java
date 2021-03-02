package com.task.genderdetector.controller;

import com.task.genderdetector.domain.Gender;
import com.task.genderdetector.evaluator.GenderEvaluator;
import com.task.genderdetector.reader.NamesReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenderDetectController.class)
@ExtendWith(SpringExtension.class)
public class GenderDetectControllerTest {

    private final String INCONCLUSIVE = "INCONCLUSIVE";
    private final List<String> maleNames = Arrays.asList("ADAM", "JAN", "KRZYSZTOF", "MARIUSZ", "PAWEŁ", "PIOTR");
    private final List<String> femaleNames = Arrays.asList("ANNA", "GRAŻYNA", "JOANNA", "PAULINA", "MAGDALENA", "MARIA");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    GenderEvaluator evaluator;

    @MockBean
    NamesReader reader;

    @Test
    public void detectGenderByOnlyFirstName() throws Exception {

        //Given
        when(evaluator.evaluateGender(false, "Susan Maria Rokita")).thenReturn(INCONCLUSIVE);

        //When & Then
        mockMvc.perform(get("/v1/detect/false/Susan Maria Rokita").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(INCONCLUSIVE)));
    }

    @Test
    public void detectGender() throws Exception {

        //Given
        when(evaluator.evaluateGender(true, "Jan Paweł Rokita")).thenReturn(Gender.MALE.name());

        //When & Then
        mockMvc.perform(get("/v1/detect/true/Jan Paweł Rokita").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(Gender.MALE.name())));
    }

    @Test
    public void getAvailableMaleNames() throws Exception {

        //Given
        when(evaluator.getReader()).thenReturn(reader);
        when(reader.getMaleNames()).thenReturn(maleNames);

        //When & Then
        mockMvc.perform(get("/v1/names/MALE").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableNames", hasSize(maleNames.size())))
                .andExpect(jsonPath("$.availableNames[0]", is(maleNames.get(0))));
    }

    @Test
    public void getAvailableFemaleNames() throws Exception {

        //Given
        when(evaluator.getReader()).thenReturn(reader);
        when(reader.getFemaleNames()).thenReturn(femaleNames);

        //When & Then
        mockMvc.perform(get("/v1/names/FEMALE").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableNames", hasSize(femaleNames.size())))
                .andExpect(jsonPath("$.availableNames[3]", is(femaleNames.get(3))));
    }
}