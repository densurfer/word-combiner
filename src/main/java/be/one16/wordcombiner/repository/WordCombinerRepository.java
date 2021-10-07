package be.one16.wordcombiner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.one16.wordcombiner.entity.WordCombinerDataEntity;

@Repository
public interface WordCombinerRepository extends JpaRepository<WordCombinerDataEntity, Long> {
}
