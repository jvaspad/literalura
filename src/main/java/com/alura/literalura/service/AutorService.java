package com.alura.literalura.service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> listarAutores() {
        return autorRepository.findAllAutoresConLibros();
    }

    public List<Autor> listarAutoresVivosEnAno(int ano) {
        return autorRepository.findAutoresVivosEnAnoDeterminado(ano);
    }

    public Autor crearAutor(Autor autor) {
        return autorRepository.save(autor);
    }

    public Optional<Autor> obtenerAutorPorNombre (String nombre) {
        return autorRepository.findByNombre(nombre);
    }
}
