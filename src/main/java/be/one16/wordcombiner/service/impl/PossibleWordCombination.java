package be.one16.wordcombiner.service.impl;

import java.util.List;

public record PossibleWordCombination(String toAchieveWord,
									  List<WordPieceLocation> wordPieceLocations) {

	public static PossibleWordCombination of(String word, List<WordPieceLocation> wordPieceLocations) {
		return new PossibleWordCombination(word, wordPieceLocations);
	}
}
