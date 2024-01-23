package site.hyundai.wewrite.domain.map.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.hyundai.wewrite.domain.map.dto.MapBoardDTO;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MapListGetResponseDTO {
    private List<MapBoardDTO> mapList;
}
