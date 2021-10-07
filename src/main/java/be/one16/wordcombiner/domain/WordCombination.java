package be.one16.wordcombiner.domain;

import java.util.List;

public record WordCombination(String toAchieveWord,
							  List<List<String>> combinations) {

	public static WordCombination of(String toAchieveWord, List<List<String>> combinations) {
		return new WordCombination(toAchieveWord, combinations);
	}
}
