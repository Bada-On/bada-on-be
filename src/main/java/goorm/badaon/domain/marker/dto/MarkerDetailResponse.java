package goorm.badaon.domain.marker.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MarkerDetailResponse {
	private Long id;
	private String message;

	@Builder
	public MarkerDetailResponse(Long id, String message) {
		this.id = id;
		this.message = message;
	}
}
