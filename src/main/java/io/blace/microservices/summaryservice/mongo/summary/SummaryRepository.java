package io.blace.microservices.summaryservice.mongo.summary;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface SummaryRepository extends MongoRepository<SummaryEntry, String>, SummaryRepositoryCustom {

	SummaryEntry findById(String id);
	
	@Query(value = "{ 'period' : {$gte : ?0, $lte: ?1 }}")
	List<SummaryEntry> findByPeriodBetween(Date start, Date end);
	
	@Query(value = "{ 'product': ?0, 'period' : {$gte : ?1, $lte: ?2 }}")
	List<SummaryEntry> findByProductAndPeriodBetween(String product, Date start, Date end);
	
	List<SummaryEntry> findAll();
	
}
