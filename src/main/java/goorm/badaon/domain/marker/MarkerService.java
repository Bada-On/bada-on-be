package goorm.badaon.domain.marker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import goorm.badaon.domain.marker.dto.MarkerDetailResponse;
import goorm.badaon.domain.marker.dto.MarkerResponse;
import goorm.badaon.domain.marker.dto.MarkerResponse;
import goorm.badaon.domain.marker.dto.MarkerSummaryResponse;
import goorm.badaon.global.enums.Activity;

@Transactional(readOnly = true)
@RequestMapping
@Service
public class MarkerService {

	public List<MarkerResponse> getAllMarkers() {
		List<MarkerResponse> markerResponses = new ArrayList<>();
		MarkerResponse dummy1 = MarkerResponse.builder()
			.id(1L)
			.name("김녕 세기알 해변")
			.latitude(33.558767)
			.longitude(126.755011)
			.activity(Activity.DIVING.getValue())
			.build();
		markerResponses.add(dummy1);

		MarkerResponse dummy2 = MarkerResponse.builder()
			.id(2L)
			.name("서빈백사 해수욕장")
			.latitude(33.502570)
			.longitude(126.942960)
			.activity(Activity.SNORKELING.getValue())
			.build();
		markerResponses.add(dummy2);

		MarkerResponse dummy3 = MarkerResponse.builder()
			.id(3L)
			.name("섭지코지")
			.latitude(33.424758)
			.longitude(126.931019)
			.activity(Activity.PHOTO_SHOOTING.getValue())
			.build();
		markerResponses.add(dummy3);

		return markerResponses;
	}

	public MarkerSummaryResponse getSummary(Long id) {
		List<Double> dummyScores = new Random().doubles(24, 1, 7)
			.map(d -> Math.round(d * 10.0) / 10.0)
			.boxed()
			.toList();

		return MarkerSummaryResponse.builder()
			.id(id)
			.markerName("수마포해안")
			.message("중급자가 서핑하기 좋아요. 다소강한 오프쇼어를 등반한 허리-어깨 높이 파도가 예상되요.")
			.recommend_scores(dummyScores)
			.build();
	}

	public MarkerDetailResponse getDetails(Long id) {
		return MarkerDetailResponse.builder()
			.id(id)
			.message("이부분 아직 뭘놔야될지 모르겠어욥.")
			.build();
	}

	public List<MarkerResponse> findByActivity(String activity) {
		Activity.fromString(activity);

		List<MarkerResponse> markerResponses = new ArrayList<>();
		MarkerResponse dummy1 = MarkerResponse.builder()
			.id(1L)
			.name("김녕 세기알 해변")
			.latitude(33.558767)
			.longitude(126.755011)
			.activity(Activity.DIVING.getValue())
			.build();
		markerResponses.add(dummy1);

		MarkerResponse dummy2 = MarkerResponse.builder()
			.id(2L)
			.name("구두미 포구")
			.latitude(33.236824)
			.longitude(126.596474)
			.activity(Activity.DIVING.getValue())
			.build();
		markerResponses.add(dummy2);

		return markerResponses;
	}
}

