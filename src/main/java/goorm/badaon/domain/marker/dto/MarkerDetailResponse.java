package goorm.badaon.domain.marker.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MarkerDetailResponse {
	private final Long id;
	private final int hour;
	private final int score;
	public final Map<String, String> feedback = new HashMap<>();
	;

	@Builder
	public MarkerDetailResponse(Long id, int hour, int score) {
		this.id = id;
		this.hour = hour;
		this.score = score;
	}

	public void addFeedback(String fieldName, String message) {
		feedback.put(fieldName, message);
	}
}
