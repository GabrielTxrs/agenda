package io.github.gabrieltxrs.agenda.rest.controller;

import io.github.gabrieltxrs.agenda.model.entity.Contato;
import io.github.gabrieltxrs.agenda.model.repository.ContatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contatos")
@RequiredArgsConstructor
public class ContatoController {

    private final ContatoRepository contatoRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contato save(@RequestBody @Validated Contato contato) {
        return contatoRepository.save(contato);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        if (contatoRepository.existsById(id)) {
            contatoRepository.deleteById(id);
            throw new ResponseStatusException(HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public List<Contato> getCliente(@PathVariable Integer id) {
        if (id == -1) {
            return contatoRepository.findAll();
        }
        return Collections.singletonList(
                contatoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado")));
    }

    @PatchMapping("/{id}/favorito")
    public void toggleFavorito(@PathVariable Integer id) {
        Optional<Contato> contato = contatoRepository.findById(id);
        contato.ifPresent(c -> {
                    c.setFavorito(!c.getFavorito());
                    contatoRepository.save(c);
                }
        );
        throw new ResponseStatusException(HttpStatus.OK);
    }
}
