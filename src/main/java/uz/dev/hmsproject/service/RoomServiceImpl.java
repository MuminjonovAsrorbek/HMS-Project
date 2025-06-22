package uz.dev.hmsproject.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.dto.RoomDTO;
import uz.dev.hmsproject.entity.Room;
import uz.dev.hmsproject.exception.RoomNotFoundException;
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
    public List<RoomDTO> getAll() {
        return roomRepository.findAll()
                .stream()
                .map(roomMapper::toDTO)
                .toList();
    }

    @Override
    public RoomDTO getById(Long id)  {
        Room room = roomRepository.findById(id).orElseThrow(() ->
                new RoomNotFoundException("room not found by id: " + id, HttpStatus.NOT_FOUND));

        return roomMapper.toDTO(room);
    }

    @Transactional
    @Override
    public void create(RoomDTO roomDTO) {
        Room room = roomMapper.toEntity(roomDTO);
        roomRepository.save(room);

    }

    @Transactional
    @Override
    public void update(Long id, RoomDTO roomDTO) {

        Room room = roomRepository.findById(id).orElseThrow(() ->
                new RoomNotFoundException("room not found by id: " + id, HttpStatus.NOT_FOUND));

        room.setNumber(roomDTO.getNumber());

        roomRepository.save(room);
    }

    @Transactional
    @Override
    public void delete(Long id) {

        Room room = roomRepository.findById(id).orElseThrow(() ->
                new RoomNotFoundException("room not found by id: " + id, HttpStatus.NOT_FOUND));

        roomRepository.delete(room);

    }
}
