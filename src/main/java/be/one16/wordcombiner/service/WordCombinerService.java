package be.one16.wordcombiner.service;

import java.util.List;

import be.one16.wordcombiner.domain.WordCombination;

public interface WordCombinerService {

	List<WordCombination> getWordCombinations(List<String> data, int toAchieveWordLength);

	void setWorkingData(List<String> data);

	List<WordCombination> getWordCombinationsOfStoredData(int toAchieveWordLength);
}
