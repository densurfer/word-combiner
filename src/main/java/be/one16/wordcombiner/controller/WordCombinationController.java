package be.one16.wordcombiner.controller;

import be.one16.wordcombiner.domain.WordCombination;
import be.one16.wordcombiner.service.WordCombinerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/word-combination")
public class WordCombinationController {

    @Value("${to.achieve.word.length.default}")
    private int defaultValue;

    private final WordCombinerService wordCombinerService;

    @PostMapping()
    public List<WordCombination> wordCombination(@RequestBody WordCombinationRequestDto requestDto) {
        return wordCombinerService.getWordCombinations(
                requestDto.getData().stream(),
                Optional.ofNullable(requestDto.getToAchieveWordLength())
                        .orElse(defaultValue)
        );
    }

    @GetMapping("/data")
    public List<WordCombination> combineStoredData(
            @RequestParam(
                    required = false,
                    defaultValue = "${to.achieve.word.length.default}",
                    name = "to_achieve_word_length") Integer toAchieveWordLength) {
        return wordCombinerService.getWordCombinationsOfStoredData(toAchieveWordLength);
    }

    @PostMapping("/file")
    public List<WordCombination> combineFromFile(@RequestParam("file") MultipartFile file,
                                                  @RequestParam(
                                                          required = false,
                                                          defaultValue = "${to.achieve.word.length.default}",
                                                          name = "to_achieve_word_length") Integer toAchieveWordLength) {
        try {
            return wordCombinerService.getWordCombinations(file, toAchieveWordLength);
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "A problem occurred with processing the file."
            );
        }
    }

    @PostMapping("/data/file")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setDataFromFile(@RequestParam("file") MultipartFile file) throws IOException {
        wordCombinerService.setWorkingData(file);
    }
}
