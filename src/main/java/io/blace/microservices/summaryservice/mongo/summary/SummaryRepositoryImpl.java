package io.blace.microservices.summaryservice.mongo.summary;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;

public class SummaryRepositoryImpl implements SummaryRepositoryCustom {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MongoTemplate mongotemplate;
	
	@Override
	public List<SummaryDetailEntry> findDetailByPeriodBetween(Date start, Date end, String product, String type, String category, String division, String region) {
		
		MatchOperation matchStage = Aggregation.match(new Criteria().andOperator(Criteria.where("period").gte(start), Criteria.where("period").lte(end)));
		LookupOperation lookupStage = Aggregation.lookup("product" ,"product", "_id", "productdesc");
		UnwindOperation unwind = Aggregation.unwind("productdesc");
		
		Criteria matchcriteria = new Criteria();
		
		if( !product.equals(""))
			matchcriteria = matchcriteria.and("product").is(product);
		
		if( !type.equals(""))
			matchcriteria = matchcriteria.and("productdesc.type").is(type);
		
		if( !category.equals(""))
			matchcriteria = matchcriteria.and("productdesc.category").is(category);
		
		if( !division.equals(""))
			matchcriteria = matchcriteria.and("productdesc.division").is(division);
		
		if( !region.equals(""))
			matchcriteria = matchcriteria.and("productdesc.region").is(region);
		
		MatchOperation matchparams = Aggregation.match(matchcriteria);
		
		ProjectionOperation projectToMatchModel = Aggregation.project()
				  .andExpression("period").as("period")
				  .andExpression("actual").as("actual")
				  .andExpression("budget").as("budget")
				  .andExpression("forecast").as("forecast")
				  .andExpression("product").as("product._id")
				  .andExpression("productdesc.description").as("product.description")
				  .andExpression("productdesc.type").as("product.type")
				  .andExpression("productdesc.category").as("product.category")
				  .andExpression("productdesc.division").as("product.division")
				  .andExpression("productdesc.region").as("product.region");
	            
		Aggregation aggregation = Aggregation.newAggregation(matchStage, lookupStage, unwind, matchparams, projectToMatchModel);
		
		AggregationResults<SummaryDetailEntry> output 
		  = mongotemplate.aggregate(aggregation, "summaryEntry", SummaryDetailEntry.class);
		
		logger.info("query completed for " +  product + " " +  type + " " +  category + " " +  region + " " +  product + " " + start + " " + end);
		
		return output.getMappedResults();
	}
	
	@Override
	public List<SummaryEntry> findEntryByPeriodBetween(Date start, Date end, String product, String type, String category, String division, String region) {
		
		MatchOperation matchStage = Aggregation.match(new Criteria().andOperator(Criteria.where("period").gte(start), Criteria.where("period").lte(end)));
		LookupOperation lookupStage = Aggregation.lookup("product" ,"product", "_id", "productdesc");
		UnwindOperation unwind = Aggregation.unwind("productdesc");
		
		Criteria matchcriteria = new Criteria();
		
		if( !product.equals(""))
			matchcriteria = matchcriteria.and("product").is(product);
		
		if( !type.equals(""))
			matchcriteria = matchcriteria.and("productdesc.type").is(type);
		
		if( !category.equals(""))
			matchcriteria = matchcriteria.and("productdesc.category").is(category);
		
		if( !division.equals(""))
			matchcriteria = matchcriteria.and("productdesc.division").is(division);
		
		if( !region.equals(""))
			matchcriteria = matchcriteria.and("productdesc.region").is(region);
		
		MatchOperation matchparams = Aggregation.match(matchcriteria);
		
		ProjectionOperation projectToMatchModel = Aggregation.project()
				  .andExpression("_id").as("_id")
				  .andExpression("period").as("period")
				  .andExpression("actual").as("actual")
				  .andExpression("budget").as("budget")
				  .andExpression("forecast").as("forecast")
				  .andExpression("product").as("product");
	            
		Aggregation aggregation = Aggregation.newAggregation(matchStage, lookupStage, unwind, matchparams, projectToMatchModel);
		
		AggregationResults<SummaryEntry> output 
		  = mongotemplate.aggregate(aggregation, "summaryEntry", SummaryEntry.class);
		
		logger.info("query completed for " +  product + " " +  type + " " +  category + " " +  region + " " +  product + " " + start + " " + end);
		
		return output.getMappedResults();
	}
}
