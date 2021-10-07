package be.one16.wordcombiner.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import be.one16.wordcombiner.domain.WordCombination;
import be.one16.wordcombiner.entity.WordCombinerDataEntity;
import be.one16.wordcombiner.repository.WordCombinerRepository;
import be.one16.wordcombiner.service.WordCombinerService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class WordCombinerServiceImpl implements WordCombinerService {

	private final DataPreparerService dataPreparer;
	private final WordCombinerRepository repository;

	@Override
	public List<WordCombination> getWordCombinations(List<String> data, int toAchieveWordLength) {
		var possibleWordCombinations = dataPreparer.preparePossibleWordCombinations(data, toAchieveWordLength);

		return possibleWordCombinations.stream()
				.map(WordCombiner::combine)
				.collect(Collectors.toList());
	}

	@Override
	public void setWorkingData(List<String> data) {
		repository.deleteAll();

		List<WordCombinerDataEntity> entities = data.stream()
				.map(WordCombinerDataEntity::new)
				.collect(Collectors.toList());

		repository.saveAll(entities);
	}

	@Override
	public List<WordCombination> getWordCombinationsOfStoredData(int toAchieveWordLength) {
		List<String> data = repository.findAll().stream()
				.map(WordCombinerDataEntity::getValue)
				.collect(Collectors.toList());

		return getWordCombinations(data, toAchieveWordLength);
	}

}
