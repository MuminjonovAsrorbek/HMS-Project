package uz.dev.hmsproject.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.dto.SpecialityDTO;
import uz.dev.hmsproject.entity.Speciality;
import uz.dev.hmsproject.exception.SpecialityNotFoundException;
import uz.dev.hmsproject.mapper.SpecialityMapper;
import uz.dev.hmsproject.repository.SpecialityRepository;
import uz.dev.hmsproject.service.template.SpecialityService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialityServiceImpl implements SpecialityService {


    private final SpecialityRepository specialityRepository;
    private final SpecialityMapper specialityMapper;

    @Override
    public List<SpecialityDTO> getAll() {
       return specialityRepository.findAll()
               .stream()
                .map(specialityMapper::toDTO
                ).toList();

    }

    @Override
    public SpecialityDTO getById(Long id)  {
        Speciality speciality = specialityRepository.findById(id).orElseThrow(() ->
                new SpecialityNotFoundException("speciality not found by id: " + id, HttpStatus.NOT_FOUND));

        return specialityMapper.toDTO(speciality);

    }

    @Transactional
    @Override
    public void create(SpecialityDTO specialityDTO) {
        Speciality speciality = specialityMapper.toEntity(specialityDTO);
        specialityRepository.save(speciality);

    }

    @Transactional
    @Override
    public void update(Long id, SpecialityDTO specialityDTO) {

        Speciality speciality = specialityRepository.findById(id).orElseThrow(() ->
                new SpecialityNotFoundException("speciality not found by id: " + id, HttpStatus.NOT_FOUND));

        speciality.setName(specialityDTO.getName());

        specialityRepository.save(speciality);

    }

    @Transactional
    @Override
    public void delete(Long id) {

        Speciality speciality = specialityRepository.findById(id).orElseThrow(() ->
                new SpecialityNotFoundException("speciality not found by id: " + id, HttpStatus.NOT_FOUND));

        specialityRepository.delete(speciality);

    }
}
