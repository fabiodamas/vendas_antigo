package io.github.fabiodamas.vendas.service;

import io.github.fabiodamas.vendas.model.Pessoa;
import io.github.fabiodamas.vendas.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Pessoa atualizar(Long codigo, Pessoa pessoa) {

		Pessoa pessoaSalva = this.pessoaRepository.findById(codigo)
				.orElseThrow(() -> new EmptyResultDataAccessException(1));

		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");

		return this.pessoaRepository.save(pessoaSalva);
	}
	
}