package goorm.badaon.domain.marker;

import static goorm.badaon.global.enums.Activity.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import goorm.badaon.domain.dayweather.DayWeather;
import goorm.badaon.domain.dayweather.DayWeatherRepository;
import goorm.badaon.domain.marker.dto.MarkerDetailResponse;
import goorm.badaon.domain.marker.dto.MarkerResponse;
import goorm.badaon.domain.marker.dto.MarkerSummaryResponse;
import goorm.badaon.domain.shortweather.ShortWeather;
import goorm.badaon.domain.shortweather.ShortWeatherRepository;
import goorm.badaon.global.enums.Activity;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MarkerService {

	private final MarkerRepository markerRepository;
	private final DayWeatherRepository dayWeatherRepository;
	private final ShortWeatherRepository shortWeatherRepository;

	public List<MarkerResponse> getAllMarkers() {
		List<MarkerResponse> markerResponses = new ArrayList<>();
		MarkerResponse dummy1 = MarkerResponse.builder()
			.id(1L)
			.name("김녕 세기알 해변")
			.latitude(33.558767)
			.longitude(126.755011)
			.activity(DIVING.getValue())
			.build();
		markerResponses.add(dummy1);

		MarkerResponse dummy2 = MarkerResponse.builder()
			.id(2L)
			.name("서빈백사 해수욕장")
			.latitude(33.502570)
			.longitude(126.942960)
			.activity(SNORKELING.getValue())
			.build();
		markerResponses.add(dummy2);

		MarkerResponse dummy3 = MarkerResponse.builder()
			.id(3L)
			.name("섭지코지")
			.latitude(33.424758)
			.longitude(126.931019)
			.activity(PHOTO_SHOOTING.getValue())
			.build();
		markerResponses.add(dummy3);

		return markerResponses;
	}

	public MarkerSummaryResponse getSummary(Long id) {

		Marker marker = markerRepository.findById(id).orElseThrow(IllegalArgumentException::new);
		Activity activity = marker.getActivity();

		List<DayWeather> dayWeathers = dayWeatherRepository.findAll();
		List<ShortWeather> shortWeathers = shortWeatherRepository.findAll();

		MarkerSummaryResponse markerSummaryResponse = MarkerSummaryResponse.builder()
			.id(id)
			.markerName(marker.getName())
			.activity(activity)
			.build();

		for (ShortWeather shortWeather : shortWeathers) {
			int hazardScore = 0;
			for (DayWeather dayWeather : dayWeathers) {
				if (shortWeather.getWeatherDate().isEqual(dayWeather.getWeatherDate())) {
					switch (activity) {
						case SNORKELING:
							hazardScore += calculateSnorkelingHazardScore(dayWeather, shortWeather);
							break;
						case DIVING:
							hazardScore += calculateDivingHazardScore(dayWeather, shortWeather);
							break;
						case PHOTO_SHOOTING:
							hazardScore += calculatePhotoShootHazardScore(dayWeather, shortWeather);
							break;
					}
				}
			}
			markerSummaryResponse.addRecommendScores("메시지 템플릿 정해야됩니다...", hazardScore,
				shortWeather.getWeatherTime().getHour(), shortWeather.getWeatherDate());
		}

		return markerSummaryResponse;
	}

	public List<MarkerDetailResponse> getDetails(Long id) {
		List<MarkerDetailResponse> detailResponseList = new ArrayList<>();

		for (int i = 0; i < 24; i++) {
			MarkerDetailResponse detailResponse = MarkerDetailResponse.builder()
				.id(id)
				.score(11)
				.hour(i)
				.build();
			detailResponse.addFeedback(SNORKELING.getValue(), "맑은 시야덕분에 강력하게 추천!");
			detailResponse.addFeedback(SWIMMING.getValue(), "잔잔한 파도로 편안하게 즐기기 좋아요.");
			detailResponseList.add(detailResponse);
		}

		return detailResponseList;
	}

	public List<MarkerResponse> findByActivity(String activity) {
		fromString(activity);

		List<MarkerResponse> markerResponses = new ArrayList<>();
		MarkerResponse dummy1 = MarkerResponse.builder()
			.id(1L)
			.name("김녕 세기알 해변")
			.latitude(33.558767)
			.longitude(126.755011)
			.activity(DIVING.getValue())
			.build();
		markerResponses.add(dummy1);

		MarkerResponse dummy2 = MarkerResponse.builder()
			.id(2L)
			.name("구두미 포구")
			.latitude(33.236824)
			.longitude(126.596474)
			.activity(DIVING.getValue())
			.build();
		markerResponses.add(dummy2);

		return markerResponses;
	}

	// 다이빙 위험도 평가 로직
	public int calculateDivingHazardScore(DayWeather dayWeather, ShortWeather shortWeather) {
		LocalDateTime current = LocalDateTime.now();
		int totalScore = 0;

		// 물때: (최대 30점)
		//  간조 ±3시간: 30점 (매우 위험)
		//  그 외 시간: 0점 (안전)
		LocalTime lowTide1Start = dayWeather.getLowTide1().minusHours(3);
		LocalTime lowTide1End = dayWeather.getLowTide1().plusHours(3);
		LocalTime lowTide2Start = dayWeather.getLowTide2().minusHours(3);
		LocalTime lowTide2End = dayWeather.getLowTide2().plusHours(3);

		LocalTime currentTime = current.toLocalTime();
		boolean isWithinLowTide1 = !currentTime.isAfter(lowTide1Start) && !currentTime.isBefore(lowTide1End);
		boolean isWithinLowTide2 = !currentTime.isAfter(lowTide2Start) && !currentTime.isBefore(lowTide2End);

		// 점수 계산
		if (isWithinLowTide1 || isWithinLowTide2) {
			totalScore += 30;  // 매우 위험한 조건
		}

		//  날씨: (최대 20점)
		//  강수확률 50% 이상: 10점 (위험 증가)
		//  강풍 (풍속 8m/s 이상): 20점 (심각한 위험) -> 강풍데이터 없음
		//  맑고 바람이 약함: 0점 (안전) -> 바람데이터 없음...
		if (shortWeather.getCloud() > 50) {
			totalScore += 10;
		}

		//  조류 속도: (최대 25점)
		//  0.5m/s 이상: 25점 (매우 위험)
		//  0.3~0.5m/s: 15점 (주의 필요)
		//  0.3m/s 이하: 0점 (안전)
		if (shortWeather.getTidal() > 10000) {
			totalScore += 25;
		} else if (shortWeather.getTidal() > 9000) {
			totalScore += 15;
		}

		//  파고: (최대 15점)
		//  2m 이상: 15점 (매우 위험)
		//  1~2m: 10점 (주의 필요)
		//  1m 이하: 0점 (안전)
		if (shortWeather.getWaveHeight() > 2) {
			totalScore += 15;
		} else if (shortWeather.getWaveHeight() > 1) {
			totalScore += 15;
		}

		//  주의보 발령: (최대 10점) -> 없음
		//  현재 주의보 발령 시: 10점 (즉각 중지 필요)
		//  주의보 없음: 0점 (안전)

		return totalScore;
	}

	// 스노클링 위험도 평가 로직 SNORKELING
	public int calculateSnorkelingHazardScore(DayWeather dayWeather, ShortWeather shortWeather) {

		LocalDateTime current = LocalDateTime.now();
		int totalScore = 0;

		// 물때: (최대 20점)
		//  만조 ±2시간: 20점 (주의 필요)
		//  그 외 시간: 0점 (안전)
		LocalTime highTide1Start = dayWeather.getHighTide1().minusHours(2);
		LocalTime highTide1End = dayWeather.getHighTide1().plusHours(2);
		LocalTime highTide2Start = dayWeather.getHighTide2().minusHours(2);
		LocalTime highTide2End = dayWeather.getHighTide2().plusHours(2);

		LocalTime currentTime = current.toLocalTime();
		boolean isWithinLowTide1 = !currentTime.isAfter(highTide1Start) && !currentTime.isBefore(highTide1End);
		boolean isWithinLowTide2 = !currentTime.isAfter(highTide2Start) && !currentTime.isBefore(highTide2End);

		// 점수 계산
		if (isWithinLowTide1 || isWithinLowTide2) {
			totalScore += 20;  // 매우 위험한 조건
		}

		//  날씨: (최대 20점)
		//  강수확률 50% 이상: 10점 (시야 감소 및 위험 증가)
		//  강풍 (풍속 8m/s 이상): 20점 (심각한 위험)
		//  맑고 바람이 약함: 0점 (안전)

		//  조류 속도: (최대 25점)
		//  0.5m/s 이상: 25점 (매우 위험)
		//  0.3~0.5m/s: 15점 (주의 필요)
		//  0.3m/s 이하: 0점 (안전)
		if (shortWeather.getTidal() > 10000) {
			totalScore += 25;
		} else if (shortWeather.getTidal() > 9000) {
			totalScore += 15;
		}

		//  파고: (최대 20점)
		//  1.5m 이상: 20점 (매우 위험)
		//  0.5~1.5m: 10점 (주의 필요)
		//  0.5m 이하: 0점 (안전)
		if (shortWeather.getWaveHeight() > 2) {
			totalScore += 15;
		} else if (shortWeather.getWaveHeight() > 1) {
			totalScore += 15;
		}

		//  물속 시야: (최대 15점) -> 데이터 없음
		//  시야 2m 이하: 15점 (매우 위험, 시야 불량)
		//  시야 2~5m: 10점 (주의 필요)
		//  시야 5m 이상: 0점 (안전)

		return totalScore;
	}

	// 스냅사진 위험도 평가 로직
	public int calculatePhotoShootHazardScore(DayWeather dayWeather, ShortWeather shortWeather) {
		int totalScore = 0;

		// 날씨: (최대 50점)
		//  강수 (비가 내릴 경우): 20점 (촬영 불가, 장비 손상 위험)
		//  천둥·번개 발생: 50점 (매우 위험, 즉각 중단 필요)
		//  맑음: 0점 (안전)
		if (shortWeather.getCloud() > 90) {
			totalScore += 50;
		} else if (shortWeather.getCloud() > 80) {
			totalScore += 20;
		}

		//  바람 속도: (최대 30점)
		//  풍속 10m/s 이상: 30점 (매우 위험, 장비 전복 및 촬영 불가)
		//  풍속 5~10m/s: 15점 (주의 필요, 강한 바람)
		//  풍속 5m/s 이하: 0점 (안전)

		//  파고: (최대 20점)
		//  2m 이상: 20점 (매우 위험, 물 튀김 및 안전 문제)
		//  1~2m: 10점 (주의 필요)
		//  1m 이하: 0점 (안전)
		if (shortWeather.getWaveHeight() > 2) {
			totalScore += 20;
		} else if (shortWeather.getWaveHeight() > 1) {
			totalScore += 10;
		}

		return totalScore;
	}
}

