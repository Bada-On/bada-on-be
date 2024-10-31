package goorm.badaon.domain.marker;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import goorm.badaon.global.enums.Activity;

public interface MarkerRepository extends JpaRepository<Marker, Long> {
	List<Marker> findAllByActivity(Activity activity);
}
