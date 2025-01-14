package org.egov.win.utils;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.HashMap;

import org.egov.common.contract.request.RequestInfo;
import org.egov.win.model.SearcherRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component
public class CronUtils {

	@Value("${egov.searcher.host}")
	private String searcherHost;

	@Value("${egov.searcher.endpoint}")
	private String searcherEndpoint;

	@Value("${egov.impact.emailer.interval.in.secs}")
	private Long timeInterval;

	private static final String MODULE_NAME = "{moduleName}";

	private static final String SEARCH_NAME = "{searchName}";

	/**
	 * Prepares request and uri for data search
	 * 
	 * @param uri
	 * @param defName
	 * @return SearcherRequest
	 */
	public SearcherRequest preparePlainSearchReq(StringBuilder uri, String defName) {
		uri.append(searcherHost);
		String endPoint = searcherEndpoint.replace(MODULE_NAME, CronConstants.SEARCHER_CRON_MOD_NAME)
				.replace(SEARCH_NAME, defName);
		uri.append(endPoint);
		HashMap<String, Long> param = new HashMap<>();
		param.put("intervalinsecs", timeInterval);
		SearcherRequest searcherRequest = SearcherRequest.builder().requestInfo(new RequestInfo()).searchCriteria(param)
				.build();
		return searcherRequest;
	}

	/**
	 * Returns mapper with all the appropriate properties reqd in our
	 * functionalities.
	 * 
	 * @return ObjectMapper
	 */
	public ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		return mapper;
	}

	/**
	 * Fetches day and month alongwith suffix of the data being fetched.
	 * 
	 * @param epochTime
	 * @return
	 */
	public String getDayAndMonth(Long epochTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(epochTime);
		StringBuilder date = new StringBuilder();
		String suffix = null;
		if (Calendar.DAY_OF_MONTH >= 11 && Calendar.DAY_OF_MONTH <= 13) {
			suffix = "th ";
		} else {
			Integer dateEndingwith = calendar.get(Calendar.DAY_OF_MONTH) % 10;
			switch (dateEndingwith) {
			case 1:
				suffix = "st ";
				break;
			case 2:
				suffix = "nd ";
				break;
			case 3:
				suffix = "rd ";
				break;
			default:
				suffix = "th ";
				break;
			}
		}

		date.append(calendar.get(Calendar.DAY_OF_MONTH)).append(suffix)
				.append(new DateFormatSymbols().getMonths()[calendar.get(Calendar.MONTH)]);
		return date.toString();
	}

}
