package br.com.alura.forum.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.alura.forum.modelo.Curso;

@RunWith(SpringRunner.class)

// -> @DataJpaTest: a. Controle transacional automático; b. Utilizar o EntityManager de testes do Spring.
@DataJpaTest

// -> Não substituir pelo banco h2 (banco em memória "default" do spring boot) --> Utilize o meu banco (exemplo) MySql. 
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

// -> Utilizar o "application-test.properties"
@ActiveProfiles("test")
public class CursoRepositoryTest {
	
	@Autowired
	private CursoRepository repository;
	
	@Autowired
	private TestEntityManager em;

	@Test
	public void dveriaCarregarUmCursoAoBuscarPeloSeuNome() {
		String nomeCurso = "HTML 5";
		
		Curso html5 = new Curso();
		html5.setNome(nomeCurso);
		html5.setCategoria("Programacao");
		em.persist(html5);
		
		Curso curso = repository.findByNome(nomeCurso);
		Assert.assertNotNull(curso);
		Assert.assertEquals(nomeCurso, curso.getNome());
	}


	@Test
	public void naoDveriaCarregarUmCujoNomeNaoEstaCadastrado() {
		String nomeCurso = "CursoDeNomeInexistnteNoBancoDeDados";
		Curso curso = repository.findByNome(nomeCurso);
		Assert.assertNull(curso);
	}
	
}
