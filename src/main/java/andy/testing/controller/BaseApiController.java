package andy.testing.controller;

import andy.testing.dto.user.UserDto;
import andy.testing.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class BaseApiController {
    protected final ModelMapper modelMapper;

    public BaseApiController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <T,V> T entity2Dto(V  entity, Class<T> type){
        return modelMapper.map(entity,type);
    }

    public <T,V> List<T> listToDto(List<V> data, Class<T> type){
        return data.stream().map(v->entity2Dto(v,type)).toList();
    }
}
