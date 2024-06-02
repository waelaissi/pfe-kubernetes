package tn.esprit.rh.achat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.rh.achat.entities.Operateur;
import tn.esprit.rh.achat.repositories.OperateurRepository;
import tn.esprit.rh.achat.services.OperateurServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OperateurTest {

    @InjectMocks
    OperateurServiceImpl operateurService;

    @Mock
    OperateurRepository operateurRepository;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testRetrieveAllOperateurs() {
        when(operateurRepository.findAll()).thenReturn(Arrays.asList(new Operateur(), new Operateur()));

        List<Operateur> operateurs = operateurService.retrieveAllOperateurs();

        assertEquals(2, operateurs.size());
    }

    @Test
    public void testAddOperateur() {
        Operateur operateur = new Operateur();
        when(operateurRepository.save(operateur)).thenReturn(operateur);

        Operateur addedOperateur = operateurService.addOperateur(operateur);

        assertNotNull(addedOperateur);
    }

    @Test
    public void testDeleteOperateur() {
        Long id = 1L;

        operateurService.deleteOperateur(id);

        verify(operateurRepository).deleteById(id);
    }

    @Test
    public void testUpdateOperateur() {
        Operateur operateur = new Operateur();
        when(operateurRepository.save(operateur)).thenReturn(operateur);

        Operateur updatedOperateur = operateurService.updateOperateur(operateur);

        assertNotNull(updatedOperateur);
    }

    @Test
    public void testRetrieveOperateur() {
        Long id = 1L;
        Operateur operateur = new Operateur();
        when(operateurRepository.findById(id)).thenReturn(Optional.of(operateur));
        Operateur retrievedOperateur = operateurService.retrieveOperateur(id);
        assertNotNull(retrievedOperateur);
    }
}
