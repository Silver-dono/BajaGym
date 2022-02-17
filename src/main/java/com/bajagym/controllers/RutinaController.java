package com.bajagym.controllers;

	import java.util.List;
	import java.util.Optional;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RestController;

	import com.bajagym.model.Rutina;
	import com.bajagym.repositories.RutinaDAO;

	@RequestMapping("/rutinas")
	@RestController
	public class RutinaController {
		@Autowired
		private RutinaDAO repositoryRoutine;

		@GetMapping("/")
		public List<Rutina> getRoutines() {
			return repositoryRoutine.findAll();
		}

		@GetMapping("/delete/{id}")
		public ResponseEntity<Rutina> deleteRoutine(@PathVariable Long id) {
			Optional<Rutina> routine = repositoryRoutine.findById(id);
			if (routine.isPresent()) {
				repositoryRoutine.deleteById(id);
				return ResponseEntity.ok(routine.get());
			} else {
				return ResponseEntity.notFound().build();
			}

		}

		@PostMapping("/newRoutine")
		public void newRoutine(@RequestBody Rutina newRoutine) {

			repositoryRoutine.save(newRoutine);
		}
	
}
