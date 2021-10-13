package be.one16.wordcombiner.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DataPreparerServiceTest {

	@Test
	void preparePossibleWordCombinations() {
		var dataPreparer = new DataPreparerService();

		var data = List.of(
				"one16",
				"one",
				"16",
				"on",
				"e",
				"e",
				"e",
				"abc",
				"abcde"
		);

		var wordCombos = dataPreparer.preparePossibleWordCombinations(data.stream(), 5);

		var one16 = PossibleWordCombination.of("one16", List.of(
				WordPieceLocation.of("one", LocationInWord.of(0, 2)),
				WordPieceLocation.of("16", LocationInWord.of(3, 4)),
				WordPieceLocation.of("on", LocationInWord.of(0, 1)),
				WordPieceLocation.of("e", LocationInWord.of(2, 2)))
		);
		var abcde = PossibleWordCombination.of("abcde", List.of(
				WordPieceLocation.of("e", LocationInWord.of(4, 4)),
				WordPieceLocation.of("abc", LocationInWord.of(0, 2)))
		);

		assertThat(wordCombos).isEqualTo(List.of(one16, abcde));
	}
}
