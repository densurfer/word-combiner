package be.one16.wordcombiner.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class DataPreparerService {

	List<PossibleWordCombination> preparePossibleWordCombinations(List<String> data,
																  int toAchieveWordLength) {
		var allOtherWordPieces = data.stream()
				.filter(wordDoesNotHaveLength(toAchieveWordLength))
				.collect(Collectors.toList());

		return data.stream()
				.filter(wordHasLength(toAchieveWordLength))
				.map(toAchieveWord -> assemblePossibleWordCombination(toAchieveWord, allOtherWordPieces))
				.collect(Collectors.toList());
	}

	private PossibleWordCombination assemblePossibleWordCombination(String toAchieveWord,
																	List<String> allOtherWordPieces) {
		List<WordPieceLocation> wordPieceLocations = allOtherWordPieces.stream()
				.filter(toAchieveWord::contains)
				.map(piece -> assembleWordPieceLocation(piece, toAchieveWord))
				.flatMap(Collection::stream)
				.collect(Collectors.toList());

		return PossibleWordCombination.of(toAchieveWord, wordPieceLocations);
	}

	private List<WordPieceLocation> assembleWordPieceLocation(String wordPiece, String toAchieveWord) {
		ArrayList<WordPieceLocation> wordPieceLocations = new ArrayList<>();

		int wordPieceLength = wordPiece.length();
		int newStartLoc;

		for (int beginLoc = toAchieveWord.indexOf(wordPiece);  beginLoc != -1; beginLoc = newStartLoc) {
			int endLoc = beginLoc + wordPieceLength - 1;
			WordPieceLocation piece = WordPieceLocation.of(wordPiece, LocationInWord.of(beginLoc, endLoc));
			wordPieceLocations.add(piece);
			newStartLoc = toAchieveWord.indexOf(wordPiece, endLoc + 1);
		}
		return wordPieceLocations;
	}


	private Predicate<String> wordHasLength(int resultWordLength) {
		return word -> wordHasLength(word, resultWordLength);
	}

	private Predicate<String> wordDoesNotHaveLength(int resultWordLength) {
		return word -> !wordHasLength(word, resultWordLength);
	}

	private boolean wordHasLength(String word, int length) {
		return word.length() == length;
	}

}
