package Dao;

import Entity.UserInfo;

import java.util.List;

public interface UserInfoMapper {

    List<UserInfo> selectAll();

    UserInfo selectByUserName(String userName);

    void insertUserInfo(UserInfo userInfo);

    void updateUserStatus(UserInfo userInfo);
}
