package io.blace.microservices.summaryservice.mongo.summary;

import java.util.Date;
import java.util.List;

public interface SummaryRepositoryCustom {

	List<SummaryDetailEntry> findDetailByPeriodBetween(Date start, Date end, String product, String type, String category, String division, String region);
	List<SummaryEntry> findEntryByPeriodBetween(Date start, Date end, String product, String type, String category, String division, String region);
}
