package be.one16.wordcombiner.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import be.one16.wordcombiner.domain.WordCombination;
import be.one16.wordcombiner.service.WordCombinerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/word-combination")
public class WordCombinationController {

	@Value("${to.achieve.word.length.default}")
	private int defaultValue;

	private final WordCombinerService wordCombinerService;

	@GetMapping("/data")
	public List<WordCombination> combineStoredData(
			@RequestParam(
					required = false,
					defaultValue = "${to.achieve.word.length.default}",
					name = "to_achieve_word_length") Integer toAchieveWordLength) {
		return wordCombinerService.getWordCombinationsOfStoredData(toAchieveWordLength);
	}

	@PostMapping("/data")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void sendData(@RequestBody List<String> data) {
		wordCombinerService.setWorkingData(data);
	}

	@PostMapping()
	public List<WordCombination> wordCombination(@RequestBody WordCombinationRequestDto requestDto) {
		return wordCombinerService.getWordCombinations(
				requestDto.getData(),
				Optional.ofNullable(requestDto.getToAchieveWordLength())
						.orElse(defaultValue)
		);
	}
}
