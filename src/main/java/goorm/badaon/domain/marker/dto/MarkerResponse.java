package goorm.badaon.domain.marker.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MarkerResponse {
	private final Long id;
	private final String name;
	private final Double latitude;
	private final Double longitude;
	private final String activity;

	@Builder
	public MarkerResponse(Long id, String name, Double latitude, Double longitude, String activity) {
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.activity = activity;
	}
}
