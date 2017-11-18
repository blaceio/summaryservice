package io.blace.microservices.summaryservice.http;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.blace.microservices.summaryservice.mongo.summary.SummaryDetailEntry;
import io.blace.microservices.summaryservice.mongo.summary.SummaryEntry;
import io.blace.microservices.summaryservice.mongo.summary.SummaryRepository;

@RestController
public class SummaryRestController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SummaryRepository summaryrepo;
		
    @CrossOrigin
    @RequestMapping(value = "summary")
    public ResponseEntity<List<SummaryDetailEntry>> getbyoptional(
    		@RequestParam("start") @DateTimeFormat(iso = ISO.DATE) Date start, 
    		@RequestParam("end") @DateTimeFormat(iso = ISO.DATE) Date end,
    		@RequestParam(value = "product", defaultValue = "") String product,
    		@RequestParam(value = "type", defaultValue = "") String type, 
    		@RequestParam(value = "category", defaultValue = "") String category, 
    		@RequestParam(value = "division", defaultValue = "") String division, 
    		@RequestParam(value = "region", defaultValue = "") String region
    		){
    		logger.info("getbyoptional optional requested for " +  product + " " +  type + " " +  category + " " +  region + " " +  product + " " + start + " " + end);
    		return new ResponseEntity<List<SummaryDetailEntry>>(summaryrepo.findDetailByPeriodBetween(start, end, product, type, category, division, region), HttpStatus.OK);
    }
    
    @CrossOrigin
    @PostMapping("/summary")
    public ResponseEntity<SummaryEntry> createsummaryentry(@RequestBody SummaryEntry summaryentry) {
    		logger.info("createsummaryentry requested for " +  summaryentry.toString());
    		summaryrepo.save(summaryentry);
        return new ResponseEntity<SummaryEntry>(HttpStatus.CREATED);
    }
    
    @CrossOrigin
    @PostMapping("/summaryupdate")
    public ResponseEntity<List<SummaryDetailEntry>> updatesummaries(@RequestBody SummaryUpdate summaryupdate) {
    		logger.info("updatesummaries requested for " +  summaryupdate.toString());
    		
    		List<SummaryEntry> toupdate = summaryrepo.findEntryByPeriodBetween(summaryupdate.getStart(), summaryupdate.getEnd(), summaryupdate.getProduct(), summaryupdate.getType(), summaryupdate.getCategory(), summaryupdate.getDivision(), summaryupdate.getRegion()); 
    		
    		for( SummaryEntry summaryentry : toupdate ) {
    			summaryentry.setBudget(summaryentry.getBudget() + ((summaryentry.getBudget() / 100) * summaryupdate.getUpdateby()));
    			summaryrepo.save(summaryentry);
    		}
    		
        return new ResponseEntity<List<SummaryDetailEntry>>(summaryrepo.findDetailByPeriodBetween(summaryupdate.getStart(), summaryupdate.getEnd(), summaryupdate.getProduct(), summaryupdate.getType(), summaryupdate.getCategory(), summaryupdate.getDivision(), summaryupdate.getRegion()), HttpStatus.CREATED);
    }
    
    @CrossOrigin
    @PostMapping("/summaryentries")
    public ResponseEntity<SummaryEntry> createsummaryentries(@RequestBody List<SummaryEntry> summaryentries) {
    		logger.info("createsummaryentries requested");
    		
    		for( SummaryEntry summaryentry : summaryentries) {
        		summaryrepo.save(summaryentry);
    		}
    		
        return new ResponseEntity<SummaryEntry>(HttpStatus.CREATED);
    }
    
    @CrossOrigin
    @DeleteMapping(value = "/summary")
    public ResponseEntity<SummaryEntry> deletesummaryentry(@RequestParam("deleteid") String deleteid) {
		logger.info("deletesummaryentry requested for " +  deleteid);
		SummaryEntry summaryentry = summaryrepo.findById(deleteid);
    		summaryrepo.delete(summaryentry);
    		return new ResponseEntity<SummaryEntry>(HttpStatus.ACCEPTED);
    }
	
}
