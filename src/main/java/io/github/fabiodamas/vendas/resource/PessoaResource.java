package io.github.fabiodamas.vendas.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import io.github.fabiodamas.vendas.event.RecursoCriadoEvent;
import io.github.fabiodamas.vendas.model.Categoria;
import io.github.fabiodamas.vendas.model.Pessoa;
import io.github.fabiodamas.vendas.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}

    @GetMapping
	public List<Pessoa> listar() {
		return pessoaRepository.findAll();
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo) {
		Pessoa pessoa = pessoaRepository.findById(codigo).orElse(null);
		return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		Pessoa pessoa = new Pessoa();
		pessoa.setCodigo(codigo);
		this.pessoaRepository.delete(pessoa);
	}

}