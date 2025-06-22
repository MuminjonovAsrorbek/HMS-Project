package uz.dev.hmsproject.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.dto.SpecialityDTO;
import uz.dev.hmsproject.entity.Speciality;
import uz.dev.hmsproject.exception.SpecialityAlreadyExistsException;
import uz.dev.hmsproject.exception.SpecialityNotFoundException;
import uz.dev.hmsproject.mapper.SpecialityMapper;
import uz.dev.hmsproject.repository.SpecialityRepository;
import uz.dev.hmsproject.service.template.SpecialityService;

import java.util.List;
import java.util.Optional;

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

        specialityRepository.findByName(specialityDTO.getName()).ifPresent(speciality -> {
            throw new SpecialityAlreadyExistsException("speciality name already exists by name: " + specialityDTO.getName(), HttpStatus.CONFLICT);
        });


        Speciality speciality = specialityMapper.toEntity(specialityDTO);
        specialityRepository.save(speciality);

    }

    @Transactional
    @Override
    public void update(Long id, SpecialityDTO specialityDTO) {

        Speciality speciality = specialityRepository.findById(id).orElseThrow(() ->
                new SpecialityNotFoundException("speciality not found by id: " + id, HttpStatus.NOT_FOUND));

        Optional<Speciality> optionalSpeciality = specialityRepository.findByName(specialityDTO.getName());
        if (optionalSpeciality.isPresent() && !optionalSpeciality.get().getId().equals(speciality.getId())) {
            throw new SpecialityAlreadyExistsException("speciality name already exists by name: " + specialityDTO.getName(), HttpStatus.CONFLICT);
        }

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
