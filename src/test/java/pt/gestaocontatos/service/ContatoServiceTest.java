
package pt.gestaocontatos.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.gestaocontatos.model.Contato;
import pt.gestaocontatos.model.TipoContacto;
import pt.gestaocontatos.repository.ContatoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContatoServiceTest {

    @Mock
    private ContatoRepository contatoRepository;

    @InjectMocks
    private ContatoService contatoService;

    private Contato criarContato() {
        Contato contato = new Contato();
        contato.setNome("Joao Teste");
        contato.setTelefone("910000001");
        contato.setEmail("joao.teste@example.com");
        contato.setTipoContacto(TipoContacto.CLIENTE);
        return contato;
    }

    @Test
    void deveCadastrarContatoComSucesso() {
        Contato contato = criarContato();

        when(contatoRepository.existsByTelefone(contato.getTelefone()))
                .thenReturn(false);
        when(contatoRepository.save(contato)).thenReturn(contato);

        Contato resultado = contatoService.cadastrar(contato);

        assertNotNull(resultado);
        assertEquals("Joao Teste", resultado.getNome());

        verify(contatoRepository).save(contato);
    }

    @Test
    void naoDeveCadastrarContatoComTelefoneDuplicado() {
        Contato contato = criarContato();

        when(contatoRepository.existsByTelefone(contato.getTelefone()))
                .thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                () -> contatoService.cadastrar(contato)
        );

        verify(contatoRepository, never()).save(any(Contato.class));
    }

    @Test
    void deveListarTodosOsContatos() {
        List<Contato> contatos = List.of(criarContato());

        when(contatoRepository.findAll()).thenReturn(contatos);

        List<Contato> resultado = contatoService.listarTodos();

        assertEquals(1, resultado.size());
        assertEquals("Joao Teste", resultado.get(0).getNome());
    }

    @Test
    void deveBuscarContatoPorId() {
        Contato contato = criarContato();

        when(contatoRepository.findById(1L))
                .thenReturn(Optional.of(contato));

        Optional<Contato> resultado = contatoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Joao Teste", resultado.get().getNome());
    }

    @Test
    void deveBuscarContatoPorNome() {
        List<Contato> contatos = List.of(criarContato());

        when(contatoRepository.findByNomeContainingIgnoreCase("Joao"))
                .thenReturn(contatos);

        List<Contato> resultado = contatoService.buscarPorNome("Joao");

        assertEquals(1, resultado.size());
        assertEquals("Joao Teste", resultado.get(0).getNome());
    }

    @Test
    void deveAtualizarContatoComSucesso() {
        Contato contatoExistente = criarContato();

        Contato dadosAtualizados = criarContato();
        dadosAtualizados.setNome("Joao Atualizado");
        dadosAtualizados.setTelefone("910000002");

        when(contatoRepository.findById(1L))
                .thenReturn(Optional.of(contatoExistente));
        when(contatoRepository.existsByTelefone("910000002"))
                .thenReturn(false);
        when(contatoRepository.save(contatoExistente))
                .thenReturn(contatoExistente);

        Contato resultado = contatoService.atualizar(1L, dadosAtualizados);

        assertEquals("Joao Atualizado", resultado.getNome());
        assertEquals("910000002", resultado.getTelefone());

        verify(contatoRepository).save(contatoExistente);
    }

    @Test
    void naoDeveAtualizarContatoComTelefoneDuplicado() {
        Contato contatoExistente = criarContato();

        Contato dadosAtualizados = criarContato();
        dadosAtualizados.setTelefone("910000002");

        when(contatoRepository.findById(1L))
                .thenReturn(Optional.of(contatoExistente));
        when(contatoRepository.existsByTelefone("910000002"))
                .thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                () -> contatoService.atualizar(1L, dadosAtualizados)
        );

        verify(contatoRepository, never()).save(any(Contato.class));
    }

    @Test
    void naoDeveAtualizarContatoInexistente() {
        when(contatoRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> contatoService.atualizar(99L, criarContato())
        );

        verify(contatoRepository, never()).save(any(Contato.class));
    }

    @Test
    void deveExcluirContatoExistente() {
        when(contatoRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> contatoService.excluir(1L));

        verify(contatoRepository).deleteById(1L);
    }

    @Test
    void naoDeveExcluirContatoInexistente() {
        when(contatoRepository.existsById(99L)).thenReturn(false);

        assertThrows(
                IllegalArgumentException.class,
                () -> contatoService.excluir(99L)
        );

        verify(contatoRepository, never()).deleteById(99L);
    }
}
