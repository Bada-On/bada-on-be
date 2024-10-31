package goorm.badaon.domain.dayweather;

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
public class DayWeather {
	@Id
	@Column(name = "day_weather_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id")
	private Region region;

	@Column(name = "weather_date")
	private LocalDate weatherDate;

	@Column(name = "sun_rise_time")
	private LocalTime sunRiseTime;

	@Column(name = "sun_set_time")
	private LocalTime sunSetTime;

	@Column(name = "high_tide_1")
	private LocalTime highTide1;

	@Column(name = "low_tide_1")
	private LocalTime LowTide1;

	@Column(name = "high_tide_2")
	private LocalTime highTide2;

	@Column(name = "low_tide_2")
	private LocalTime LowTide2;
}
