package tn.esprit.rh.achat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.rh.achat.entities.Facture;
import tn.esprit.rh.achat.entities.Fournisseur;
import tn.esprit.rh.achat.repositories.*;
import tn.esprit.rh.achat.services.FactureServiceImpl;
import tn.esprit.rh.achat.services.OperateurServiceImpl;
import tn.esprit.rh.achat.services.ReglementServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class FactureTest {
    @InjectMocks
    FactureServiceImpl factureService;

    @Mock
    FactureRepository factureRepository;

    @Mock
    OperateurRepository operateurRepository;

    @Mock
    DetailFactureRepository detailFactureRepository;

    @Mock
    FournisseurRepository fournisseurRepository;

    @Mock
    ProduitRepository produitRepository;

    @Mock
    ReglementServiceImpl reglementService;
    @Mock
    OperateurServiceImpl operateurService;

    @BeforeEach
    public void setUp() {
        // Initialize any necessary setup here.


    }

    @Test
    public void testretrieveAllFactures() {
        List<Facture> factures = new ArrayList<>();
        factures.add(new Facture());
        factures.add(new Facture());

        when(factureRepository.findAll()).thenReturn(factures);

        List<Facture> result = factureService.retrieveAllFactures();
        assertEquals(factures, result);
    }

    @Test
    public void testaddFacture() {
        Facture facture = new Facture();

        when(factureRepository.save(facture)).thenReturn(facture);

        Facture result = factureService.addFacture(facture);
        assertEquals(facture, result);
    }

    @Test
    public void testcancelFacture() {
        Long factureId = 1L;
        Facture facture = new Facture();
        when(factureRepository.findById(factureId)).thenReturn(Optional.of(facture));

        factureService.cancelFacture(factureId);

        assertTrue(facture.getArchivee());
    }

    @Test
    public void testretrieveFacture() {
        Long factureId = 1L;
        Facture facture = new Facture();
        when(factureRepository.findById(factureId)).thenReturn(Optional.of(facture));

        Facture result = factureService.retrieveFacture(factureId);

        assertEquals(facture, result);
    }
    @Test
    public void testgetFacturesByFournisseur() {
        // Mock the FournisseurRepository behavior
        when(fournisseurRepository.findById(anyLong())).thenAnswer(invocation -> {
            Long id = invocation.getArgument(0);

            if (id == 1L) {
                Fournisseur fournisseur = new Fournisseur();
                // Set properties of the Fournisseur as needed for your test
                Set<Facture> factures = new HashSet<>();
                // Add Facture objects to the factures Set as needed for your test
                factures.add(new Facture()); // Example Facture
                factures.add(new Facture()); // Example Facture
                fournisseur.setFactures(factures);
                return Optional.of(fournisseur);
            } else {
                return Optional.empty();
            }
        });

        // Call your test method
        Fournisseur fournisseur = fournisseurRepository.findById(1L).orElse(null);

        // Process the Set of Factures
        int factureCount = 0;
        for (Facture facture : fournisseur.getFactures()) {
            // Process each Facture as needed
            factureCount++;
        }

        // Assertions
        assertEquals(2, factureCount); // Assuming there are 2 Factures in the set
        // Add other assertions for this test case as needed
    }


    @Test
    public void testpourcentageRecouvrement() {
        Date startDate = new Date();
        Date endDate = new Date();
        float totalFactures = 100.0f;
        float totalRecouvrement = 80.0f;

        when(factureRepository.getTotalFacturesEntreDeuxDates(startDate, endDate)).thenReturn(totalFactures);
        when(reglementService.getChiffreAffaireEntreDeuxDate(startDate, endDate)).thenReturn(totalRecouvrement);

        float pourcentage = factureService.pourcentageRecouvrement(startDate, endDate);

        assertEquals(80.0f, pourcentage, 0.001);
    }
}
