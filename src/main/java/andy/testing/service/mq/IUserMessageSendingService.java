package andy.testing.service.mq;

import andy.testing.entity.UserEntity;

public interface IUserMessageSendingService {
    public void sendUser(UserEntity userEntity);
}
