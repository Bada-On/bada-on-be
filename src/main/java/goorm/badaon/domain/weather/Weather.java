package goorm.badaon.domain.weather;

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
public class Weather {

	@Id
	@Column(name = "weather_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id")
	private Region region;

	@Column(name = "weather_date")
	private LocalDate weatherDate;

	@Column(name = "weather_time")
	private LocalTime weatherTime;

	@Column(name = "sun_rise_time")
	private LocalTime sunRiseTime;

	@Column(name = "sun_set_time")
	private LocalTime sunSetTime;

	@Column(name = "high_tide_1")
	private int highTide1;

	@Column(name = "low_tide_1")
	private int LowTide1;

	@Column(name = "high_tide_2")
	private int highTide2;

	@Column(name = "low_tide_2")
	private int LowTide2;

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
