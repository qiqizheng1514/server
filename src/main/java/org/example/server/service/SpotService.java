package org.example.server.service;

import org.example.server.dto.SpotDTO;
import org.example.server.entity.Spot;
import org.example.server.repository.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpotService {

    @Autowired
    private SpotRepository spotRepository;

    public List<SpotDTO> getAllSpots() {
        return spotRepository.findAll().stream()
                .map(SpotDTO::new)
                .collect(Collectors.toList());
    }

    public SpotDTO getSpotById(Long id) {
        Spot spot = spotRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("景点不存在"));
        return new SpotDTO(spot);
    }

    @Transactional
    public SpotDTO createSpot(SpotDTO spotDTO) {
        Spot spot = new Spot();
        updateSpotFromDTO(spot, spotDTO);
        return new SpotDTO(spotRepository.save(spot));
    }

    @Transactional
    public SpotDTO updateSpot(Long id, SpotDTO spotDTO) {
        Spot spot = spotRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("景点不存在"));
        updateSpotFromDTO(spot, spotDTO);
        return new SpotDTO(spotRepository.save(spot));
    }

    @Transactional
    public void deleteSpot(Long id) {
        if (!spotRepository.existsById(id)) {
            throw new IllegalArgumentException("景点不存在");
        }
        spotRepository.deleteById(id);
    }

    private void updateSpotFromDTO(Spot spot, SpotDTO dto) {
        spot.setName(dto.getName());
        spot.setDescription(dto.getDescription());
        spot.setAddress(dto.getAddress());
        spot.setOpeningHours(dto.getOpeningHours());
        spot.setTicketPrice(dto.getTicketPrice());
        spot.setRating(dto.getRating());
        spot.setImageUrl(dto.getImageUrl());
        spot.setLatitude(dto.getLatitude());
        spot.setLongitude(dto.getLongitude());
    }
} 