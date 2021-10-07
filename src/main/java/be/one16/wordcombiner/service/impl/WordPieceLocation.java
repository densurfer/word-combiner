package be.one16.wordcombiner.service.impl;

public record WordPieceLocation(String wordPiece,
								LocationInWord locationInWord) {

	public static WordPieceLocation of(String wordPiece, LocationInWord location) {
		return new WordPieceLocation(wordPiece, location);
	}
}
