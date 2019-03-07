/**
 * 
 */
package com.sorc.content.services.video.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.elasticsearch.index.query.BoolQueryBuilder;

import com.sorc.content.elasticsearch.video.filter.input.ElasticSearchVideoFilterQueryBuilder;
import com.sorc.content.video.filter.input.ElasticSearchVideoFilter;

/**
 * @author rakesh.moradiya
 *
 */
public class VideoParameterValidator {

	public static BoolQueryBuilder validateCustomParameters(Set<Integer> websiteIds, String mainCategory, Set<String> mainCategoryNotIn, Integer videoDuration,
			String countryCode, String videoId, String status, String text,
			Integer showId, Integer seasonId, String showName, String seasonName, 
			Integer episodeNum, String showCategory) {
		ElasticSearchVideoFilter filter = new ElasticSearchVideoFilter();
		
		if(websiteIds != null && !websiteIds.isEmpty())
			filter.setWebsiteIds(websiteIds);
		
		if(mainCategory != null)
			filter.setMainCategory(mainCategory);
		
		if(mainCategoryNotIn != null && !mainCategoryNotIn.isEmpty())
			filter.setMainCategoryNotIn(mainCategoryNotIn);
		
		if(videoDuration != null)
			filter.setVideoDuration(videoDuration);
		
		if(countryCode != null)
			filter.setCountryCode(countryCode);
		
		if(videoId != null)
			filter.setVideoId(videoId);
		
		if(status != null)
			filter.setStatus(status.trim().toUpperCase());
		
		if(text != null && text.trim().length() > 0)
			filter.setText(text);
		
		if(showId != null)
			filter.setShowId(showId);
		
		if(seasonId != null)
			filter.setSeasonId(seasonId);
		
		if(showName != null)
			filter.setShowName(showName);
		
		if(seasonName != null)
			filter.setSeasonName(seasonName);
		
		if(episodeNum != null)
			filter.setEpisodeNum(episodeNum);
		
		if(showCategory != null)
			filter.setShowCategory(showCategory);
		
		ElasticSearchVideoFilterQueryBuilder qb = new ElasticSearchVideoFilterQueryBuilder(filter);
		return qb.buildQuery();
	}
	
	@SuppressWarnings("unused")
	private static List<String> getSearchableTextList(String text)
	{
		List<String> textList = null;
		if(text != null)		
			textList = Arrays.asList(text.split(" "));			
		else
			return null;
		
		return textList;
	}
}
