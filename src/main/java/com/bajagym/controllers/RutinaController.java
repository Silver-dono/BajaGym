package com.bajagym.controllers;

	import java.util.List;
	import java.util.Optional;

	import com.bajagym.repositories.UsuarioDAO;
	import org.apache.commons.collections4.CollectionUtils;
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
		private RutinaDAO rutinaDAO;
		@Autowired
		private UsuarioDAO usuarioDAO;

		@GetMapping("/")
		public List<Rutina> getRoutines() {
			return rutinaDAO.findAll();
		}

		@GetMapping("/delete/{id}")
		public ResponseEntity<Rutina> deleteRoutine(@PathVariable Long id) {
			Optional<Rutina> routine = rutinaDAO.findById(id);
			if (routine.isPresent()) {
				rutinaDAO.deleteById(id);
				return ResponseEntity.ok(routine.get());
			} else {
				return ResponseEntity.notFound().build();
			}

		}

		@GetMapping("/{nombre}")
		public Rutina getRutinaUsuario(@PathVariable String nombre){
			return usuarioDAO.getRutinaUsuario(nombre);
		}

		@GetMapping("/ejemplo")
		public List<Rutina> getRutinasEjemplo(){
			List<Rutina> rutinas = rutinaDAO.getAllByEjemplo(true);
			if(CollectionUtils.isNotEmpty(rutinas)){
				return rutinas;
			} else {
				return null;
			}
		}

		@PostMapping("/newRoutine")
		public void newRoutine(@RequestBody Rutina newRoutine) {

			rutinaDAO.save(newRoutine);
		}
	
}
