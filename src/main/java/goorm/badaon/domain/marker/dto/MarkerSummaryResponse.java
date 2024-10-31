package goorm.badaon.domain.marker.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import goorm.badaon.global.enums.Activity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MarkerSummaryResponse {

	private final Long id;
	private final String markerName;
	private final String activity;
	private final List<ScoreAndMessage> recommendScores = new ArrayList<>();

	@Builder
	public MarkerSummaryResponse(Long id, String markerName, String message, Activity activity) {
		this.id = id;
		this.markerName = markerName;
		this.activity = activity.getValue();
	}

	public void addRecommendScores(String message, int score, int hour, LocalDate date) {
		ScoreAndMessage scoreAndMessage = ScoreAndMessage.builder()
			.message(message)
			.score(score)
			.hour(hour)
			.date(date)
			.build();
		recommendScores.add(scoreAndMessage);
	}

	@Getter
	public static class ScoreAndMessage {
		private int hour;
		private String message;
		private int score;
		private LocalDate date;

		@Builder
		public ScoreAndMessage(String message, int score, int hour, LocalDate date) {
			this.message = message;
			this.score = score;
			this.hour = hour;
			this.date = date;
		}
	}
}
