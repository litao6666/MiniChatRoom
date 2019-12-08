package Service;

import Dao.UserInfoMapper;
import Entity.UserInfo;
import Utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserInfoService {

    private SqlSession session;

    private UserInfoMapper getMapper(){
        session= MyBatisUtil.getSqlSession();
        return session.getMapper(UserInfoMapper.class);
    }

    public List<UserInfo> getAll(){
        List<UserInfo> list=new ArrayList<>();
        list=getMapper().selectAll();
        closeSession();
        return list;
    }

    public UserInfo login(String userName,String password){
        UserInfo result=null;
        UserInfo userInfo=getMapper().selectByUserName(userName);
        if(password.equals(userInfo.getPassword())){
            result=userInfo;
        }
        closeSession();
        return result;
    }

    public UserInfo register(String userName,String password){
        UserInfo newUser=new UserInfo();
        newUser.setId(UUID.randomUUID().toString());
        newUser.setUserName(userName);
        newUser.setPassword(password);
        newUser.setStatus("0");
        getMapper().insertUserInfo(newUser);
        session.commit();
        closeSession();
        return newUser;
    }

    public void updateUserStatus(UserInfo userInfo){
        getMapper().updateUserStatus(userInfo);
        session.commit();
        closeSession();
    }

    private void closeSession(){
        if(session!=null){
            session.close();
        }
    }

    /*public static void main(String[] a){
        System.out.println(getAll());
    }*/
}
