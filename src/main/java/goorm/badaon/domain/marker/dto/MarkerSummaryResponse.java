package goorm.badaon.domain.marker.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MarkerSummaryResponse {

	private Long id;
	private final String markerName;
	private final String message;
	List<Double> recommend_scores;

	@Builder
	public MarkerSummaryResponse(Long id, String markerName, String message, List<Double> recommend_scores) {
		this.id = id;
		this.markerName = markerName;
		this.message = message;
		this.recommend_scores = recommend_scores;
	}
}
