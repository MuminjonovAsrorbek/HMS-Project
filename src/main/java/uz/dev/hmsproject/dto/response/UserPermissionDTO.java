package uz.dev.hmsproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.hmsproject.enums.PageEnum;
import uz.dev.hmsproject.enums.Permissions;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/2/25 11:42
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserPermissionDTO {

    private List<Permissions> permissions;

    private List<PageEnum> pages;

}
