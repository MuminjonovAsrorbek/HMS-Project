package uz.dev.hmsproject.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.dto.SpecialityCreationDTO;
import uz.dev.hmsproject.dto.SpecialityDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;
import uz.dev.hmsproject.entity.PriceList;
import uz.dev.hmsproject.entity.Speciality;
import uz.dev.hmsproject.entity.template.AbsLongEntity;
import uz.dev.hmsproject.exception.EntityNotFoundException;
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
    public SpecialityDTO getById(Long id) {
        Speciality speciality = specialityRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("speciality not found by id: " + id, HttpStatus.NOT_FOUND));

        return specialityMapper.toDTO(speciality);

    }

    @Transactional
    @Override
    public void create(SpecialityCreationDTO creationDTO) {

        Speciality speciality = new Speciality();
        speciality.setName(creationDTO.getName());

        PriceList priceList = new PriceList();
        priceList.setPrice(creationDTO.getPrice());
        priceList.setSpeciality(speciality);

        speciality.setPriceList(priceList);

        specialityRepository.save(speciality);

    }

    @Transactional
    @Override
    public void update(Long id, SpecialityDTO specialityDTO) {

        Speciality speciality = specialityRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("speciality not found by id: " + id, HttpStatus.NOT_FOUND));

        speciality.setName(specialityDTO.getName());

        specialityRepository.save(speciality);

    }

    @Transactional
    @Override
    public void delete(Long id) {

        Speciality speciality = specialityRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("speciality not found by id: " + id, HttpStatus.NOT_FOUND));

        specialityRepository.delete(speciality);

    }

    @Override
    public PageableDTO getAllByPage(Integer page, Integer size) {

        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Speciality> specialityPage = specialityRepository.findAll(pageable);

        List<Speciality> specialities = specialityPage.getContent();

        List<SpecialityDTO> specialityDTOS = specialityMapper.toDTO(specialities);

        return new PageableDTO(
                specialityPage.getSize(),
                specialityPage.getTotalElements(),
                specialityPage.getTotalPages(),
                !specialityPage.isLast(),
                !specialityPage.isFirst(),
                specialityDTOS
        );

    }
}
