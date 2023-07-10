package dev.idriz.planey.repository;

import dev.idriz.planey.model.Ticket;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * The ticket repository
 */
public interface TicketRepository extends CrudRepository<Ticket, UUID> {

}
