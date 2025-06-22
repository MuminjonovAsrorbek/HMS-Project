package uz.dev.hmsproject.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.dto.RoomDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;
import uz.dev.hmsproject.entity.Room;
import uz.dev.hmsproject.entity.template.AbsLongEntity;
import uz.dev.hmsproject.exception.EntityNotFoundException;
import uz.dev.hmsproject.exception.EntityUniqueException;
import uz.dev.hmsproject.mapper.RoomMapper;
import uz.dev.hmsproject.repository.RoomRepository;
import uz.dev.hmsproject.service.template.RoomService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {


    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    public PageableDTO getAll(Integer page, Integer size) {

        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Room> roomPages = roomRepository.findAll(pageable);

        List<Room> rooms = roomPages.getContent();

        List<RoomDTO> roomDTOS = roomMapper.toDTO(rooms);

        return new PageableDTO(
                roomPages.getSize(),
                roomPages.getTotalElements(),
                roomPages.getTotalPages(),
                !roomPages.isLast(),
                !roomPages.isFirst(),
                roomDTOS
        );
    }

    @Override
    public RoomDTO getById(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("room not found by id: " + id, HttpStatus.NOT_FOUND));

        return roomMapper.toDTO(room);
    }

    @Transactional
    @Override
    public void create(RoomDTO roomDTO) {

        roomRepository.findByNumber(roomDTO.getNumber()).ifPresent(room -> {
            throw new EntityUniqueException("room already exists with number: " + roomDTO.getNumber(), HttpStatus.CONFLICT);
        });

        Room room = new Room(
                roomDTO.getNumber()
        );
        roomRepository.save(room);

    }

    @Transactional
    @Override
    public void update(Long id, RoomDTO roomDTO) {

        Room room = roomRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("room not found by id: " + id, HttpStatus.NOT_FOUND));

        roomRepository.findByNumber(roomDTO.getNumber())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new EntityUniqueException("room already exists with number: " + roomDTO.getNumber(), HttpStatus.CONFLICT);
                });

        room.setNumber(roomDTO.getNumber());

        roomRepository.save(room);
    }

    @Transactional
    @Override
    public void delete(Long id) {

        Room room = roomRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("room not found by id: " + id, HttpStatus.NOT_FOUND));

        roomRepository.delete(room);

    }
}
