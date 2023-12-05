package andy.testing.service.mq;

import andy.testing.entity.UserEntity;

public interface IUserMessageReceivingService {
    public UserEntity receiveUser();
}
