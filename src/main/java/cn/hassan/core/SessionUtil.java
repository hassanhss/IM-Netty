package cn.hassan.core;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

public class SessionUtil {

    private static final Map<String, Channel> userIdChannelMap = new HashMap<>();

    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel){
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static boolean hasLogin(Channel channel) {
        if (LoginUtil.hasLogin(channel)) {
            return true;
        }else {
            return false;
        }
    }

    public static Session getSession(Channel channel) {
        return (Session) channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {
        return userIdChannelMap.get(userId);
    }
}
