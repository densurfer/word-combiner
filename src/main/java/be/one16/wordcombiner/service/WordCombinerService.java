package be.one16.wordcombiner.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import be.one16.wordcombiner.domain.WordCombination;
import org.springframework.web.multipart.MultipartFile;

public interface WordCombinerService {

	List<WordCombination> getWordCombinations(Stream<String> data, int toAchieveWordLength);

	List<WordCombination> getWordCombinationsOfStoredData(int toAchieveWordLength);

	List<WordCombination> getWordCombinations(MultipartFile file, int toAchieveWordLength) throws IOException;

	void setWorkingData(MultipartFile file) throws IOException;
}
