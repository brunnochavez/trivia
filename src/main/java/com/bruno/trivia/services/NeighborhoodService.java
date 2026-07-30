package com.bruno.trivia.services;
import com.bruno.trivia.dtos.NeighborhoodRequestDTO;
import com.bruno.trivia.dtos.NeighborhoodResponseDTO;
import com.bruno.trivia.entities.Neighborhood;
import com.bruno.trivia.repositories.NeighborhoodRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NeighborhoodService {

    private final NeighborhoodRepository neighborhoodRepository;

    @Transactional(readOnly = true)
    public NeighborhoodResponseDTO findById(Long id){
        Neighborhood neighborhood = neighborhoodRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Bairro não encontrado!")
        );
        return toResponse(neighborhood);
    }

    @Transactional(readOnly = true)
    public Page<NeighborhoodResponseDTO> findAll(Pageable pageable){
        Page<NeighborhoodResponseDTO> pageNeighborhood = neighborhoodRepository.findAll(pageable)
                .map(n -> toResponse(n));
        return pageNeighborhood;
    }

    @Transactional
    public NeighborhoodResponseDTO insert(NeighborhoodRequestDTO dto){
        if(neighborhoodRepository.existsByNameIgnoreCase(dto.name())){
            throw new EntityExistsException("Já existe um bairro com este nome");
        }
        Neighborhood neighborhood = toNeighborhood(dto);
        neighborhood = neighborhoodRepository.save(neighborhood);
        return toResponse(neighborhood);
    }

    @Transactional
    public NeighborhoodResponseDTO update(Long id, NeighborhoodRequestDTO dto){
        Neighborhood neighborhood = neighborhoodRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Bairro não encontrado!")
        );

        if(neighborhoodRepository.existsByNameIgnoreCaseAndIdNot(dto.name(), id)){
            throw new EntityExistsException("Já existe um bairro com este nome");
        }
        dtoToEntity(dto, neighborhood);
        neighborhood = neighborhoodRepository.save(neighborhood);
        return toResponse(neighborhood);
    }

    @Transactional
    public void deleteById(Long id){
        Neighborhood neighborhood = neighborhoodRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Bairro não encontrado!")
        );
        neighborhoodRepository.delete(neighborhood);
    }

    private NeighborhoodResponseDTO toResponse(Neighborhood neighborhood){
        return new NeighborhoodResponseDTO(
                neighborhood.getId(),
                neighborhood.getName(),
                neighborhood.getDeliveryFee()
        );
    }

    private Neighborhood toNeighborhood(NeighborhoodRequestDTO dto){
        return new Neighborhood(
                dto.name(),
                dto.deliveryFee()
        );
    }

    private void dtoToEntity(NeighborhoodRequestDTO dto, Neighborhood entity){
        entity.setName(dto.name());
        entity.setDeliveryFee(dto.deliveryFee());
    }
}
