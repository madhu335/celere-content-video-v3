/**
 * 
 */
package com.sorc.content.elasticsearch.video.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sorc.content.core.dao.IDataTransfer;
import com.sorc.content.core.dao.MissingValidationException;
import com.sorc.content.core.dao.ValidationException;
import com.sorc.content.core.dto.TraceInfo;
import com.sorc.content.elasticsearch.core.constant.ElasticSearchVideoFieldConstants;
import com.sorc.content.elasticsearch.core.dao.ElasticSearchClusterConfiguration;
import com.sorc.content.elasticsearch.core.dao.ElasticSearchDAO;
import com.sorc.content.elasticsearch.core.dto.ElasticSearchFilterDataTransfer;
import com.sorc.content.video.dao.data.ElasticSearchVideo;

/**
 * @author rakesh.moradiya
 *
 */
@Repository
public class ElasticSearchVideoDAO extends ElasticSearchDAO<ElasticSearchVideo, String> {

	@Autowired
	public ElasticSearchVideoDAO(ElasticSearchClusterConfiguration clusterConfiguration) {
		super(clusterConfiguration);		
	}

	@Override
	public Map<String, Object> getRootDetailList(ElasticSearchFilterDataTransfer esfdt) throws Exception {		
		return null;
	}

	@Override
	public Map<String, Object> getDetailList(ElasticSearchFilterDataTransfer esfdt) throws Exception {
		return search(esfdt.getPagination(), esfdt.getIndex(), esfdt.getFilters(), esfdt.getSorting(), esfdt.getScriptFilter(), false);		
	}

	@Override
	public Map<String, Object> getCustomDetailList(ElasticSearchFilterDataTransfer esfdt) throws Exception {		
		return null;
	}

	@Override
	public List<Object> getFacetList(ElasticSearchFilterDataTransfer esfdt) throws Exception {		
		return searchFacet(esfdt.getPagination(), esfdt.getIndex(), esfdt.getFacets(), esfdt.getFilters(), esfdt.getSorting(), false);		
	}	

	@Override
	public List<Object> getDetailFacetList(ElasticSearchFilterDataTransfer esfdt, String callType)
			throws Exception {	
		if(ElasticSearchVideoFieldConstants.CALL_TYPE_APPLEUMC_CATALOG.equalsIgnoreCase(callType))
			return getElasticSearchAppleUmcFeedDetailFacetResult(esfdt.getPagination(), esfdt.getIndex(), 
					esfdt.getFacets(), esfdt.getFilters(), esfdt.getFacetFields(), esfdt.getAdditionalFacetColumns(), esfdt.getSorting(), esfdt.getAggDetailSorting(), callType);
		else if(ElasticSearchVideoFieldConstants.CALL_TYPE_APPLEUMC_AVAILABILITY.equalsIgnoreCase(callType))
			return getElasticSearchAppleUmcAvailabilityFeedDetailFacetResult(esfdt.getPagination(), esfdt.getIndex(), 
					esfdt.getFacets(), esfdt.getFilters(), esfdt.getFacetFields(), esfdt.getAdditionalFacetColumns(), esfdt.getSorting(), esfdt.getAggDetailSorting(), callType);
		return null;
	}

	@Override
	public List<Object> getMultiFacetList(ElasticSearchFilterDataTransfer esfdt) throws Exception {		
		return null;
	}

	@Override
	public Object create(IDataTransfer arg0) throws ValidationException, MissingValidationException {		
		return null;
	}

	@Override
	public List getDistinctTagAttributes(Integer arg0, String arg1, String arg2, Integer arg3, TraceInfo arg4) {		
		return null;
	}

	@Override
	public ElasticSearchVideo getVideoDetail(ElasticSearchFilterDataTransfer esfdt) throws Exception {
		
		Map<String, Object> resultMap = search(esfdt.getPagination(), esfdt.getIndex(), esfdt.getFilters(), esfdt.getSorting(), esfdt.getScriptFilter(), false);	
		Long totalCount = 0L;
		List<ElasticSearchVideo> videoList = new ArrayList<ElasticSearchVideo>();
		if(resultMap != null && !resultMap.isEmpty()) {
			if(resultMap.get(ElasticSearchVideoFieldConstants.TOTAL_COUNT) != null 
					&& resultMap.get(ElasticSearchVideoFieldConstants.ELASTICSEARCH_VIDEO_LIST) != null) {
				totalCount = (Long) resultMap.get(ElasticSearchVideoFieldConstants.TOTAL_COUNT);
				videoList = (List<ElasticSearchVideo>) resultMap.get(ElasticSearchVideoFieldConstants.ELASTICSEARCH_VIDEO_LIST);
				if(videoList != null && videoList.size() > 0)
					return videoList.get(0);
			}
		}
		
		return null;
	}

	@Override
	public String getNextSeason(ElasticSearchFilterDataTransfer esfdt, String previousSeason) throws Exception {
		
		String nextSeason = null;
		boolean isPreviousSeasonFound = false;
		List<Object> seasonList = getFacetList(esfdt);
		
		for(Object seasonStr : seasonList)
		{
			if(isPreviousSeasonFound)
			{
				nextSeason = seasonStr.toString();
				break;
			}
			if(seasonStr != null && seasonStr.toString().equalsIgnoreCase(previousSeason))
				isPreviousSeasonFound = true;		
		}
		return nextSeason;
	}
}
