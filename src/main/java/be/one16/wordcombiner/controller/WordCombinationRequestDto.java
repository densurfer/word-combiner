package be.one16.wordcombiner.controller;

import java.util.List;

import lombok.Data;

@Data
public class WordCombinationRequestDto {

	private List<String> data;
	private Integer toAchieveWordLength;
}
