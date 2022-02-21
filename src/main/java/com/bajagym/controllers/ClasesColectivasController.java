package com.bajagym.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bajagym.model.ClasesColectivas;
import com.bajagym.repositories.ClasesColectivasDAO;

@RequestMapping("/ClasesColectivas")
@Controller
public class ClasesColectivasController {

    @Autowired
    private ClasesColectivasDAO clasesColectivasDAO;

    @GetMapping("/")
    public List<ClasesColectivas> getGroupLessons() {
        return clasesColectivasDAO.findAll();
    }

    @GetMapping("/prueba")
    public String probate() {
        return "clases_colectivas";
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<ClasesColectivas> deleteGroupLesson(@PathVariable Long id) {
        Optional<ClasesColectivas> groupLesson = clasesColectivasDAO.findById(id);
        if (groupLesson.isPresent()) {
            clasesColectivasDAO.deleteById(id);
            return ResponseEntity.ok(groupLesson.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/{fecha}")
    public List<ClasesColectivas> getGroupLessonsByDate(@PathVariable Date fecha) {
        List<ClasesColectivas> clases = clasesColectivasDAO.findAllByFecha(fecha);
        if (CollectionUtils.isNotEmpty(clases)) {
            return clases;
        } else {
            return null;
        }
    }

    @PostMapping("/newGroupLesson")
    public void newGroupLesson(@RequestBody ClasesColectivas newGroupLesson) {
        clasesColectivasDAO.save(newGroupLesson);
    }
}
