package org.ps.pecap.service;

import java.util.Optional;
import org.ps.pecap.domain.Client;
import org.ps.pecap.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Client}.
 */
@Service
@Transactional
public class ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Save a client.
     *
     * @param client the entity to save.
     * @return the persisted entity.
     */
    public Client save(Client client) {
        log.debug("Request to save Client : {}", client);
        return clientRepository.save(client);
    }

    /**
     * Update a client.
     *
     * @param client the entity to save.
     * @return the persisted entity.
     */
    public Client update(Client client) {
        log.debug("Request to update Client : {}", client);
        return clientRepository.save(client);
    }

    /**
     * Partially update a client.
     *
     * @param client the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Client> partialUpdate(Client client) {
        log.debug("Request to partially update Client : {}", client);

        return clientRepository
            .findById(client.getId())
            .map(existingClient -> {
                if (client.getNom() != null) {
                    existingClient.setNom(client.getNom());
                }
                if (client.getPrenom() != null) {
                    existingClient.setPrenom(client.getPrenom());
                }
                if (client.getPhotoUrl() != null) {
                    existingClient.setPhotoUrl(client.getPhotoUrl());
                }
                if (client.getDateNaiss() != null) {
                    existingClient.setDateNaiss(client.getDateNaiss());
                }
                if (client.getAnneeNaiss() != null) {
                    existingClient.setAnneeNaiss(client.getAnneeNaiss());
                }
                if (client.getLieuNaiss() != null) {
                    existingClient.setLieuNaiss(client.getLieuNaiss());
                }
                if (client.getGenre() != null) {
                    existingClient.setGenre(client.getGenre());
                }
                if (client.getTypeDemande() != null) {
                    existingClient.setTypeDemande(client.getTypeDemande());
                }
                if (client.getEmail() != null) {
                    existingClient.setEmail(client.getEmail());
                }
                if (client.getDestVoyageP() != null) {
                    existingClient.setDestVoyageP(client.getDestVoyageP());
                }
                if (client.getMotifDeplacement() != null) {
                    existingClient.setMotifDeplacement(client.getMotifDeplacement());
                }
                if (client.getPaysNaissance() != null) {
                    existingClient.setPaysNaissance(client.getPaysNaissance());
                }
                if (client.getRegionNaiss() != null) {
                    existingClient.setRegionNaiss(client.getRegionNaiss());
                }
                if (client.getDeparteNaiss() != null) {
                    existingClient.setDeparteNaiss(client.getDeparteNaiss());
                }
                if (client.getTelephone() != null) {
                    existingClient.setTelephone(client.getTelephone());
                }
                if (client.getPays() != null) {
                    existingClient.setPays(client.getPays());
                }
                if (client.getRegion() != null) {
                    existingClient.setRegion(client.getRegion());
                }
                if (client.getDepartement() != null) {
                    existingClient.setDepartement(client.getDepartement());
                }
                if (client.getLieu() != null) {
                    existingClient.setLieu(client.getLieu());
                }
                if (client.getRue() != null) {
                    existingClient.setRue(client.getRue());
                }
                if (client.getProfession() != null) {
                    existingClient.setProfession(client.getProfession());
                }
                if (client.getPrenomMere() != null) {
                    existingClient.setPrenomMere(client.getPrenomMere());
                }
                if (client.getNomMere() != null) {
                    existingClient.setNomMere(client.getNomMere());
                }
                if (client.getPrenomPere() != null) {
                    existingClient.setPrenomPere(client.getPrenomPere());
                }
                if (client.getNomPere() != null) {
                    existingClient.setNomPere(client.getNomPere());
                }
                if (client.getFormatCni() != null) {
                    existingClient.setFormatCni(client.getFormatCni());
                }
                if (client.getNumeroCni() != null) {
                    existingClient.setNumeroCni(client.getNumeroCni());
                }
                if (client.getDateDelivCni() != null) {
                    existingClient.setDateDelivCni(client.getDateDelivCni());
                }
                if (client.getDateExpCni() != null) {
                    existingClient.setDateExpCni(client.getDateExpCni());
                }

                return existingClient;
            })
            .map(clientRepository::save);
    }

    /**
     * Get all the clients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Client> findAll(Pageable pageable) {
        log.debug("Request to get all Clients");
        return clientRepository.findAll(pageable);
    }

    /**
     * Get one client by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Client> findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findById(id);
    }

    /**
     * Delete the client by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.deleteById(id);
    }
}
