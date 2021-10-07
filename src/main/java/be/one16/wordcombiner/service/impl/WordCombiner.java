package be.one16.wordcombiner.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.one16.wordcombiner.domain.WordCombination;

public class WordCombiner {

	private final int length;
	private final Map<Integer, List<WordPieceLocation>> wordPiecesMap;
	private final List<List<String>> finalList;
	private final String toAchieveWord;

	public static WordCombination combine(PossibleWordCombination possibleWordCombination) {
		WordCombiner wordCombination = new WordCombiner(possibleWordCombination);
		wordCombination.start();
		return wordCombination.getResult();
	}

	private WordCombiner(PossibleWordCombination possibleWordCombination) {
		this.wordPiecesMap = convertToMapPerStartingLocation(possibleWordCombination.wordPieceLocations());
		this.toAchieveWord = possibleWordCombination.toAchieveWord();
		this.length = possibleWordCombination.toAchieveWord().length();
		this.finalList = new ArrayList<>();
	}

	private WordCombination getResult() {
		return WordCombination.of(toAchieveWord, finalList);
	}

	private void start() {
		List<WordPieceLocation> startingPieces = wordPiecesMap.get(0);
		buildingList(new ArrayList<>(), startingPieces);
	}

	private void buildingList(List<String> workingList, List<WordPieceLocation> nextPiecesList) {
		if (nextPiecesList == null) {
			return;
		}
		for (WordPieceLocation currentPiece : nextPiecesList) {
			ArrayList<String> newWorkingList = new ArrayList<>(workingList);
			newWorkingList.add(currentPiece.wordPiece());

			int endLoc = currentPiece.locationInWord().to();
			boolean isWordCompleted = endLoc == length - 1;
			if (!isWordCompleted) {
				nextPiecesList = wordPiecesMap.get(endLoc + 1);
				buildingList(newWorkingList, nextPiecesList);
			} else {
				this.finalList.add(newWorkingList);
			}
		}
	}

	private Map<Integer, List<WordPieceLocation>> convertToMapPerStartingLocation(List<WordPieceLocation> wordPieceLocations) {
		Map<Integer, List<WordPieceLocation>> map = new HashMap<>();
		wordPieceLocations.forEach(piece -> addToListInMap(piece, map));
		return map;
	}

	private void addToListInMap(WordPieceLocation piece, Map<Integer, List<WordPieceLocation>> map) {
		int from = piece.locationInWord().from();
		List<WordPieceLocation> list = map.getOrDefault(from, new ArrayList<>());
		list.add(piece);
		map.putIfAbsent(from, list);
	}
}
