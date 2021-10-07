package be.one16.wordcombiner.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "WORD_COMBINER_DATA")
public class WordCombinerDataEntity {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "VALUE")
	private String value;

	public WordCombinerDataEntity(String value) {
		this.value = value;
	}
}
