package be.one16.wordcombiner.service.impl;

import be.one16.wordcombiner.domain.WordCombination;
import be.one16.wordcombiner.entity.WordCombinerDataEntity;
import be.one16.wordcombiner.repository.WordCombinerRepository;
import be.one16.wordcombiner.service.WordCombinerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class WordCombinerServiceImpl implements WordCombinerService {

    private final DataPreparerService dataPreparer;
    private final WordCombinerRepository repository;

    @Override
    public List<WordCombination> getWordCombinations(Stream<String> data, int toAchieveWordLength) {
        var possibleWordCombinations = dataPreparer.preparePossibleWordCombinations(
                data,
                toAchieveWordLength
        );

        return possibleWordCombinations.parallelStream()
                .map(WordCombiner::combine)
                .toList();
    }

    @Override
    public List<WordCombination> getWordCombinations(MultipartFile file, int toAchieveWordLength) throws IOException {
        Stream<String> lines = new BufferedReader(new InputStreamReader(file.getInputStream())).lines();
        return getWordCombinations(lines, toAchieveWordLength);
    }

    @Override
    @Transactional
    public void setWorkingData(MultipartFile file) throws IOException {
        repository.deleteAll();

        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        reader.lines()
                .map(WordCombinerDataEntity::new)
                .forEach(repository::save);
    }

    @Override
    public List<WordCombination> getWordCombinationsOfStoredData(int toAchieveWordLength) {
        Stream<String> data = repository.findAll().stream()
                .map(WordCombinerDataEntity::getValue);

        return getWordCombinations(data, toAchieveWordLength);
    }
}
