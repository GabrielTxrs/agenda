package io.github.gabrieltxrs.agenda.rest.controller;

import io.github.gabrieltxrs.agenda.model.entity.Contato;
import io.github.gabrieltxrs.agenda.model.repository.ContatoRepository;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contatos")
@RequiredArgsConstructor
@CrossOrigin("*")
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
    public ResponseEntity<Void> toggleFavorito(@PathVariable Integer id) {
        Optional<Contato> contato = contatoRepository.findById(id);
        contato.ifPresent(c -> {
                    c.setFavorito(!c.getFavorito());
                    contatoRepository.save(c);
                }
        );
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/foto")
    public Optional<byte[]> addPhoto(@PathVariable Integer id, @RequestParam("file") Part file) {
        Optional<Contato> contato = contatoRepository.findById(id);
        return contato.map(c -> {
            try {
                InputStream is = file.getInputStream();
                byte[] bytes = new byte[(int) file.getSize()];
                IOUtils.readFully(is, bytes);
                c.setFoto(bytes);
                contatoRepository.save(c);
                is.close();
                return bytes;
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao processar a foto");
            }
        });
    }
}
