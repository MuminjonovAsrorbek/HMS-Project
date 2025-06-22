package uz.dev.hmsproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: asrorbek
 * DateTime: 6/22/25 10:58
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageableDTO {

    private Integer size;

    private Long totalElements;

    private Integer totalPages;

    private boolean hasNext;

    private boolean hasPrevious;

    private Object object;

}
