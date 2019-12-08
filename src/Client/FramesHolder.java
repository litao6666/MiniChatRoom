package Client;

import Entity.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class FramesHolder {
    private static List<ChatFrame> users=new ArrayList<>();


    public static void addChatFrame(ChatFrame cf){
        users.add(cf);
        Notify();
    }

    public static boolean remove(ChatFrame cf){
        boolean flag=users.remove(cf);
        Notify();
        return flag;
    }

    public static String getFormatedUserInfos(){
        StringBuilder sb=new StringBuilder("");
        for(ChatFrame cf:users){
            UserInfo userInfo=cf.getUserInfo();
            sb.append("用户 ");
            sb.append(userInfo.getUserName());
            sb.append(": ");
            sb.append("1".equals(userInfo.getStatus())?"在线":"离线");
            sb.append("\n");
        }
        return  sb.toString();
    }

    public static void Notify(){
        for(ChatFrame cf:users){
            cf.setUsersList(getFormatedUserInfos());
        }
    }
}
