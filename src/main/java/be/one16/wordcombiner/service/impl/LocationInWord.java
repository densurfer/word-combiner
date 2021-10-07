package be.one16.wordcombiner.service.impl;

public record LocationInWord(int from, int to) {

	public static LocationInWord of(int from, int to) {
		return new LocationInWord(from, to);
	}
}
