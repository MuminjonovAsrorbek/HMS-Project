package uz.dev.hmsproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link uz.dev.hmsproject.entity.Room}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO implements Serializable {

    @Schema(description = "Unique id of the room", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull
    @Schema(description = "Room number", example = "Apple 101", requiredMode = Schema.RequiredMode.REQUIRED)
    private String number;
}