package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

	@Autowired
	TopicoRepository topicoRepository;
	
	@Autowired
	CursoRepository cursoRepository;
	
	
	// --> O parâmetro "String nomeCurso" vêm na url, via método GET
	
	@GetMapping
	public List<TopicoDto> listar(String nomeCurso) {
		if (nomeCurso != null) {
			List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
			return TopicoDto.converter(topicos);
		} else {
			List<Topico> topicos = topicoRepository.findAll();			
			return TopicoDto.converter(topicos);			
		}
	}
	
	
	// --> @RequestBody : O parâmetro vêm no corpo da requisição, via método POST.
	// --> O cliente mandou o JSON e o Spring chama o Jackson para pegar e converter no TopicoForm.
	// --> @Valid: para avisar para o Spring rodar as validações do bean validation.
	
	@PostMapping
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		
		URI uri = uriBuilder
				.path("/topicos/{id}")
				.buildAndExpand(topico.getId())
				.toUri();
		
		return ResponseEntity
				.created(uri) 
				.body(new TopicoDto(topico)); // -> devolver o código 201
	}
}
