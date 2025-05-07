package br.edu.senaisp.colegio.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.edu.senaisp.colegio.model.EPerfil;
import br.edu.senaisp.colegio.model.EStatus;
import br.edu.senaisp.colegio.model.Usuario;
import br.edu.senaisp.colegio.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {
	
	@InjectMocks
	UsuarioService usuarioService;
	
	@Mock
	UsuarioRepository repo;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	@DisplayName("Caso de Sucesso - retorno: Usuario.")
	public void buscarPorId_Sucesso() {
		Usuario usuarioEsperado = new Usuario();
		usuarioEsperado.setId(1L);
		usuarioEsperado.setLogin("Xaxa");
		usuarioEsperado.setSenha("sdfghjkl");
		usuarioEsperado.setPerfil(EPerfil.ALUNO);
		usuarioEsperado.setStatus(EStatus.ATIVO);
		
		Mockito.when(repo.findById(1L)).thenReturn(Optional.of(usuarioEsperado));
		Usuario usuarioResposta = usuarioService.buscarPorId(1L);
		
		Assertions.assertEquals(usuarioEsperado , usuarioResposta);
		
	}
	
	@Test
	@DisplayName("Caso Exception - retorno: RuntimeException.")
	public void buscarPorId_Excecao() {
		
		Mockito.when(repo.findById(1L)).thenReturn(null);
		
		Assertions.assertThrows(RuntimeException.class,() -> usuarioService.buscarPorId(1L));
	}	
}
