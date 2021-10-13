package be.one16.wordcombiner.controller;

import be.one16.wordcombiner.domain.WordCombination;
import be.one16.wordcombiner.entity.WordCombinerDataEntity;
import be.one16.wordcombiner.repository.WordCombinerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class WordCombinationControllerTest {

    private static final String BASE_URL = "/word-combination";
    @Autowired
    private MockMvc mvc;
    @Autowired
    private WordCombinerRepository repository;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void wordCombination_wordLengthGiven() throws Exception {
        var requestDto = new WordCombinationRequestDto();
        requestDto.setData(List.of("one", "16", "one16"));
        requestDto.setToAchieveWordLength(5);

        var result = List.of(
                WordCombination.of("one16", List.of(List.of("one", "16")))
        );

        mvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(result)));
    }

    @Test
    void wordCombination_defaultWordLength() throws Exception {
        var requestDto = new WordCombinationRequestDto();
        requestDto.setData(List.of("one", "16", "one16"));

        var result = Collections.emptyList();

        mvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(result)));
    }

    @Test
    void combineFromFile() throws Exception {
        var result = List.of(
                WordCombination.of("one16", List.of(List.of("one", "16")))
        );

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "one\n16\none16".getBytes());

        mvc.perform(multipart(BASE_URL + "/file")
                        .file(file)
                        .param("to_achieve_word_length", "5"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(result)));
    }

    @Test
    void setDataFromFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "one\n16\none16".getBytes());

        mvc.perform(multipart(BASE_URL + "/data/file")
                        .file(file))
                .andExpect(status().isNoContent());

        List<WordCombinerDataEntity> all = repository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(3);
    }

    @Test
    void combineStoredData() throws Exception {
        repository.saveAll(List.of(
                word("one"), word("16"), word("one16")
        ));

        var result = List.of(
                WordCombination.of("one16", List.of(List.of("one", "16")))
        );

        mvc.perform(get(BASE_URL + "/data")
                        .queryParam("to_achieve_word_length", "5"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(result)));
    }

    private WordCombinerDataEntity word(String value) {
        return new WordCombinerDataEntity(value);
    }
}
