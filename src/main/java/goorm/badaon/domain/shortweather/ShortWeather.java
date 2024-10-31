package goorm.badaon.domain.shortweather;

import java.time.LocalDate;
import java.time.LocalTime;

import goorm.badaon.domain.regions.Region;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ShortWeather {
	
	@Id
	@Column(name = "short_weather_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id")
	private Region region;

	@Column(name = "weather_date")
	private LocalDate weatherDate;

	@Column(name = "weather_time")
	private LocalTime weatherTime;

	@Column(name = "cloud")
	private int cloud;

	@Column(name = "temp")
	private double temperature;

	@Column(name = "tidal")
	private int tidal;

	@Column(name = "wave_height")
	private int waveHeight;

	@Column(name = "visible_distance")
	private int visibleDistance;
}
