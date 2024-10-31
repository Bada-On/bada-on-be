package goorm.badaon.global.enums;

import lombok.Getter;

@Getter
public enum Activity {
	SNORKELING("snorkeling"),
	DIVING("diving"),
	SWIMMING("swimming"),
	SURFING("surfing"),
	SCUBA_DIVING("scubaDiving"),
	PHOTO_SHOOTING("snap"),
	SUNRISE_SUNSET_VIEWING("sunset");

	private final String value;

	Activity(String value) {
		this.value = value;
	}
}
