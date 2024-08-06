package eu.universome.UAds;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.universome.Music.Music;

// REST Response


// JSON import

@Service
public class UAdsService {
	
	private final UAdsRepository uAdsRepository;
	
	// Constructor
	@Autowired
	public UAdsService(UAdsRepository uAdsRepository) {
		this.uAdsRepository = uAdsRepository;
	}
	
	
	// CRUD Implementation
	// POST
	public String addUAds(UniversityAds uAds) {
		Optional<UniversityAds> uAdsOptional = this.uAdsRepository.findByTitle(uAds.getTitle());
		
		if (uAdsOptional.isPresent()) {
			throw new IllegalStateException("Title present");
		} else {
			uAdsRepository.save(uAds);
			return "UAds saved";
		}
	}
	
	// GET 
	public Collection<UniversityAds> getUAds(){
		return this.uAdsRepository.findAll();
	}
	
	// Delete
	public void deleteUAds(Long uAdsId) {
		boolean exists = uAdsRepository.existsById(uAdsId);
		
		if (!exists) throw new IllegalStateException("UAds with id " + uAdsId + "does not exists");
		
		uAdsRepository.deleteById(uAdsId);		
	}
	
	// Update User
	@Transactional
	public void updateUAds(Long uAdsId,
							String title,
							String description,
							String department,
							String speaker) {
		
		UniversityAds uAds = uAdsRepository
						.findById(uAdsId)
						.orElseThrow(() -> new IllegalStateException("UAds with id " + uAdsId + "does not exist"));
		
		if (   title != null 
			&& title.length() > 0 
			&& !Objects.equals(uAds.getTitle(), title) ) { // check if new name is not equal to the previous
				
				uAds.setTitle(title);
		}
		
		if (   description != null 
				&& description.length() > 0 
				&& !Objects.equals(uAds.getDescription(), description) ) { 
					
				uAds.setDescription(description);
		}
		
		if (   department != null 
				&& department.length() > 0 
				&& !Objects.equals(uAds.getDepartment(), department) ) { 
					
				uAds.setDepartment(department);
		}
	}
}

