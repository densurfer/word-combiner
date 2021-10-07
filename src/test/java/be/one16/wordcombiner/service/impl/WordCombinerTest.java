package be.one16.wordcombiner.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import be.one16.wordcombiner.domain.WordCombination;

class WordCombinerTest {

	@Test
	void combine_case1() {
		var one16 = PossibleWordCombination.of("one16", List.of(
				WordPieceLocation.of("one", LocationInWord.of(0, 2)),
				WordPieceLocation.of("16", LocationInWord.of(3, 4)),
				WordPieceLocation.of("on", LocationInWord.of(0, 1)),
				WordPieceLocation.of("e", LocationInWord.of(2, 2)))
		);

		WordCombination combination = WordCombiner.combine(one16);

		WordCombination solution = WordCombination.of("one16", List.of(
				List.of("one", "16"),
				List.of("on", "e", "16")
		));

		assertThat(combination).isEqualTo(solution);
	}

	@Test
	void combine_case2() {
		var one16 = PossibleWordCombination.of("one16", List.of(
				WordPieceLocation.of("one", LocationInWord.of(0, 2)),
				WordPieceLocation.of("16", LocationInWord.of(3, 4)),
				WordPieceLocation.of("1", LocationInWord.of(3, 3)),
				WordPieceLocation.of("6", LocationInWord.of(4, 4)),
				WordPieceLocation.of("on", LocationInWord.of(0, 1)),
				WordPieceLocation.of("e", LocationInWord.of(2, 2)))
		);

		WordCombination combination = WordCombiner.combine(one16);

		WordCombination solution = WordCombination.of("one16", List.of(
				List.of("one", "16"),
				List.of("one", "1", "6"),
				List.of("on", "e", "16"),
				List.of("on", "e", "1", "6")
		));

		assertThat(combination).isEqualTo(solution);
	}

	@Test
	void combine_case3() {
		var one16 = PossibleWordCombination.of("one16", List.of(
				WordPieceLocation.of("one", LocationInWord.of(0, 2)),
				WordPieceLocation.of("1", LocationInWord.of(3, 3)),
				WordPieceLocation.of("on", LocationInWord.of(0, 1)),
				WordPieceLocation.of("e", LocationInWord.of(2, 2)))
		);

		WordCombination combination = WordCombiner.combine(one16);

		WordCombination solution = WordCombination.of("one16", Collections.emptyList());

		assertThat(combination).isEqualTo(solution);
	}
}