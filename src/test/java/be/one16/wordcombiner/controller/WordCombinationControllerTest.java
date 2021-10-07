package be.one16.wordcombiner.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import be.one16.wordcombiner.domain.WordCombination;
import be.one16.wordcombiner.entity.WordCombinerDataEntity;
import be.one16.wordcombiner.repository.WordCombinerRepository;

@SpringBootTest
@AutoConfigureMockMvc

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
	void sendData() throws Exception {
		var requestDto = List.of("one", "16", "one16");

		mvc.perform(post(BASE_URL + "/data")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestDto)))
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