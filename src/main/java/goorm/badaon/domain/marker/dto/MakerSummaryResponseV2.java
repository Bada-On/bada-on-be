package goorm.badaon.domain.marker.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;

// "id": 2,
// 	"hour": 1,
// 	"score": 11,
// 	"feedback": {
// 	"swimming": "잔잔한 파도로 편안하게 즐기기 좋아요.",
// 	"snorkeling": "맑은 시야덕분에 강력하게 추천!"
// 	}
@Getter
public class MakerSummaryResponseV2 {
	private Long id;
	private int hour;
	private int score;
	private Map<String, String> feedback = new HashMap<>();

	@Builder
	public MakerSummaryResponseV2(Long id, int hour, int score) {
		this.id = id;
		this.hour = hour;
		this.score = score;
	}

	public void addFeedback(String activity, String message) {
		feedback.put(activity, message);
	}
}
