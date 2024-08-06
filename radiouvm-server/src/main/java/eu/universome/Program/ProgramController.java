package eu.universome.Program;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.universome.User.UserService.UnauthorizedException;

@RestController
@RequestMapping(path = "api/v1/program")
public class ProgramController {
	
	private final ProgramService programService;
	
	// Autowired serve a legare la creazione della variabile programService al parametro del costruttore.
	// Sostituisce la parola new per creare un'istanza della classe, senza la quale non potremmo chiamare il metodo getProgram()
	@Autowired
	public ProgramController(ProgramService programService) {
		this.programService = programService;
	}
	
	// CRUD Operations
	// Test Done - Ok http://localhost:8080/api/v1/program Body: {"title":"Prog 1", "description":"Something", "speaker":"Gianluca"}
	@PostMapping
	public ResponseEntity<String> registerNewProgram(@RequestBody Program program) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			String result = this.programService.addProgram(program);
			if ( result != null) {
				return new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("{ }", HttpStatus.BAD_REQUEST);
			}
		} catch (UnauthorizedException e) {
			return new ResponseEntity<String>(objectMapper.writeValueAsString("Wrong user or password"), HttpStatus.UNAUTHORIZED);
		}
	}
	
	@GetMapping
	public Collection<Program> getProgram() {
		return this.programService.getProgram();
	}
	
	
	
	@PutMapping(path = "{programId}")
	// Test Done - Ok http://localhost:8080/api/v1/program/1?title=Prog2&description=Something2&speaker=Carbone
	public void updateProgram(
							@PathVariable("programId") Long programId,
							@RequestParam(required = false) String title,
							@RequestParam(required = false) String description,
							@RequestParam(required = false) String speaker) {
		programService.updateProgram(programId, title, description, speaker);
	}
	
	@DeleteMapping(path = "{programId}")
	// Test Done - Ok http://localhost:8080/api/v1/program/1
	public void deleteProgram(@PathVariable("programId") Long programId) {
		programService.deleteProgram(programId);
	}
	
}
