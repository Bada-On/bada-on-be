package goorm.badaon.domain.marker;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import goorm.badaon.domain.marker.dto.MarkerDetailResponse;
import goorm.badaon.domain.marker.dto.MarkerResponse;
import goorm.badaon.domain.marker.dto.MarkerSummaryResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/markers")
@RestController
public class MarkerController {

	private final MarkerService markerService;

	@GetMapping
	public ResponseEntity<List<MarkerResponse>> getMarkerList() {
		List<MarkerResponse> response = markerService.getAllMarkers();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/summary/{id}")
	public ResponseEntity<MarkerSummaryResponse> getMarkerSummary(@PathVariable("id") Long id) {
		MarkerSummaryResponse response = markerService.getSummary(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/details/{id}")
	public ResponseEntity<List<MarkerDetailResponse>> getMarkerDetails(@PathVariable("id") Long id) {
		List<MarkerDetailResponse> response = markerService.getDetails(id);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/find")
	public ResponseEntity<List<MarkerResponse>> findMarkers(@RequestParam("activity") String activity) {
		List<MarkerResponse> response = markerService.findByActivity(activity);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
