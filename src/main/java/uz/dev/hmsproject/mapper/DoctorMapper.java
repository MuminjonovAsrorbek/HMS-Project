package uz.dev.hmsproject.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.dev.hmsproject.dto.DoctorDTO;
import uz.dev.hmsproject.dto.response.DoctorResponseDTO;
import uz.dev.hmsproject.entity.Doctor;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DoctorMapper {

    private final PriceListMapper priceListMapper;


    public DoctorDTO toDTO(Doctor doctor) {

        return new DoctorDTO(
                doctor.getId(),
                doctor.getUser().getId(),
                doctor.getSpeciality().getId(),
                doctor.getRoom().getId()
        );
    }

    public List<DoctorResponseDTO> toDTO(List<Doctor> doctors) {

        return doctors.stream().map(
                doctor -> new DoctorResponseDTO(
                        doctor.getId(),
                        doctor.getUser().getFullName(),
                        doctor.getUser().getUsername(),
                        doctor.getSpeciality().getName(),
                        priceListMapper.toDTO(doctor.getSpeciality().getPriceList()),
                        doctor.getRoom().getNumber()
                )
        ).toList();

    }

}
