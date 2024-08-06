package eu.universome.Program;

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
public class ProgramService {
	
	private final ProgramRepository programRepository;
	
	// Constructor
	@Autowired
	public ProgramService(ProgramRepository programRepository) {
		this.programRepository = programRepository;
	}
	
	
	
	// CRUD Implementation
	public String addProgram(Program program) {
		Optional<Program> programOptional = this.programRepository.findByTitle(program.getTitle());
		
		if (programOptional.isPresent()) {
			throw new IllegalStateException("Title present");
		} else {
			programRepository.save(program);
			return "Program saved";
		}
	}
	
	//@RequestMapping(path = "/program")
	public Collection<Program> getProgram(){
		return this.programRepository.findAll();
	}
	
	// Update Program
	@Transactional
	public void updateProgram(Long programId,
							String title,
							String description,
							String speaker) {
		
		Program program = programRepository
						.findById(programId)
						.orElseThrow(() -> new IllegalStateException("program with id " + programId + "does not exist"));
		
		if (   title != null 
			&& title.length() > 0 
			&& !Objects.equals(program.getTitle(), title) ) { // check if new name is not equal to the previous
				
				program.setTitle(title);
		}
		
		if (   description != null 
				&& description.length() > 0 
				&& !Objects.equals(program.getDescription(), description) ) { 
					
					program.setDescription(description);
		}
		
		if (   speaker != null 
				&& speaker.length() > 0 
				&& !Objects.equals(program.getSpeaker(), speaker) ) { 
					
					program.setSpeaker(speaker);
		}
	}
	
	// Delete
	public void deleteProgram(Long programId) {
		boolean exists = programRepository.existsById(programId);
		
		if (!exists) throw new IllegalStateException("program with id " + programId + "does not exists");
		
		programRepository.deleteById(programId);		
	}
}

