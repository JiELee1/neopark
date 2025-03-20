package com.prgrms.be.intermark.domain.ticket.controller;

import com.prgrms.be.intermark.common.dto.page.PageResponseDTO;
import com.prgrms.be.intermark.domain.ticket.dto.TicketCreateRequestDTO;
import com.prgrms.be.intermark.domain.ticket.dto.TicketResponseByMusicalDTO;
import com.prgrms.be.intermark.domain.ticket.dto.TicketResponseByUserDTO;
import com.prgrms.be.intermark.domain.ticket.dto.TicketResponseDTO;
import com.prgrms.be.intermark.domain.ticket.model.Ticket;
import com.prgrms.be.intermark.domain.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/create")
    public ResponseEntity<Void> createTicket(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid TicketCreateRequestDTO ticketCreateRequestDTO) {

        Long ticketId = ticketService.createTicket(ticketCreateRequestDTO,token);

        return ResponseEntity.created(
                URI.create("/api/v1/tickets/" + ticketId)
        ).build();
    }


    @PostMapping
    public ResponseEntity<String> createTicket1(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid TicketCreateRequestDTO ticketCreateRequestDTO
    ){
        Long ticketId = ticketService.createTicket(ticketCreateRequestDTO, token);
        String message = String.format("%s 님이 %d번째 좌석을 예매하였습니다.", ticketCreateRequestDTO.getUserId(), ticketCreateRequestDTO.getScheduleSeatId());
        String ticketInfo = String.format("\n고객님의 티켓번호는 %d입니다.", ticketId);

        return ResponseEntity.ok(message+ticketInfo);
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<Ticket, TicketResponseDTO>> getAllTickets(Pageable pageable) {
        PageResponseDTO<Ticket, TicketResponseDTO> tickets = ticketService.getAllTickets(pageable);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<PageResponseDTO<Ticket, TicketResponseByUserDTO>> getTicketsByUser(
            @PathVariable("userId") Long userId, Pageable pageable) {
        PageResponseDTO<Ticket, TicketResponseByUserDTO> tickets = ticketService.getTicketsByUser(userId, pageable);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/musicals/{musicalId}")
    public ResponseEntity<PageResponseDTO<Ticket, TicketResponseByMusicalDTO>> getTicketsByMusical(
            @PathVariable("musicalId") Long musicalId, Pageable pageable) {
        PageResponseDTO<Ticket, TicketResponseByMusicalDTO> tickets = ticketService.getTicketsByMusical(
                musicalId, pageable);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketResponseDTO> getTicketById(@PathVariable("ticketId") Long ticketId) {
        TicketResponseDTO ticket = ticketService.getTicketById(ticketId);
        return ResponseEntity.ok(ticket);
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long ticketId) {
        ticketService.deleteTicket(ticketId);

        return ResponseEntity.noContent().build();
    }
}
