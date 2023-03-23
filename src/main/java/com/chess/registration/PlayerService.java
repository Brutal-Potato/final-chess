package com.chess.registration;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Page<Players> getPlayersForTable(String queryString, Pageable pageable) {

        PlayerTableFilter PlayersTableFilter = new PlayerTableFilter(queryString);

        return playerRepository.findAll(PlayersTableFilter, pageable);
    }
}
