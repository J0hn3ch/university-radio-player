package eu.universome.UAds;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UAdsRepository
	extends JpaRepository<UniversityAds, Long> {
	
	Optional<UniversityAds> findById(Long id);
	Optional<UniversityAds> findByTitle(String title);
	Collection<UniversityAds> findByDepartment(String department);
}
