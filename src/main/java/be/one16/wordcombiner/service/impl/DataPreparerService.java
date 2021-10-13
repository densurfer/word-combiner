package be.one16.wordcombiner.service.impl;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DataPreparerService {

    List<PossibleWordCombination> preparePossibleWordCombinations(Stream<String> data,
                                                                  int toAchieveWordLength) {
        var toAchieveWordLengthMap = data
                .collect(Collectors.groupingBy(word -> wordHasLength(word, toAchieveWordLength)));

        List<String> toAchieveWords = toAchieveWordLengthMap.getOrDefault(true, new ArrayList<>());
        List<String> allOtherWordPieces = toAchieveWordLengthMap.getOrDefault(false, new ArrayList<>());

        return toAchieveWords.parallelStream()
                .map(toAchieveWord -> assemblePossibleWordCombination(toAchieveWord, allOtherWordPieces))
                .toList();
    }

    private PossibleWordCombination assemblePossibleWordCombination(String toAchieveWord,
                                                                    List<String> allOtherWordPieces) {
        List<WordPieceLocation> wordPieceLocations = allOtherWordPieces.parallelStream()
                .filter(toAchieveWord::contains)
                .distinct()
                .map(piece -> assembleWordPieceLocation(piece, toAchieveWord))
                .flatMap(Collection::stream)
                .toList();

        return PossibleWordCombination.of(toAchieveWord, wordPieceLocations);
    }

    private List<WordPieceLocation> assembleWordPieceLocation(String wordPiece, String toAchieveWord) {
        ArrayList<WordPieceLocation> wordPieceLocations = new ArrayList<>();

        int wordPieceLength = wordPiece.length();
        int newStartLoc;

        for (int beginLoc = toAchieveWord.indexOf(wordPiece); beginLoc != -1; beginLoc = newStartLoc) {
            int endLoc = beginLoc + wordPieceLength - 1;
            WordPieceLocation piece = WordPieceLocation.of(wordPiece, LocationInWord.of(beginLoc, endLoc));
            wordPieceLocations.add(piece);
            newStartLoc = toAchieveWord.indexOf(wordPiece, endLoc + 1);
        }
        return wordPieceLocations;
    }

    private boolean wordHasLength(String word, int length) {
        return word.length() == length;
    }

}
