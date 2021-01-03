package game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Room {
    // 房间集合
    public static HashSet<String> roomSet = new HashSet<>();
    // 房间密码Map
    public static Map<String, String> roomAdminPWMap = new HashMap<>();
    public static Map<String, String> roomPlayerPWMap = new HashMap<>();

    // 房间临时代理密码唯一ID
    /*
        http://www.米游.com/?room_num=977927&pw=123
        room_num --> room_id --> pw匹配
        3ERWhE5W88 --> 9595
     */
    public static Map<String, String> roomIdMapTemp = new HashMap<>();

    // Set<String> : 参数String是房间ID
    // 房间管理员信息 --> 作用：查询管理员是否在线
    public static Set<String> roomAdminSet = new HashSet<>();
    // 房间玩家信息 --> 作用：查询玩家是否在线
    public static Set<String> roomPlayerSet = new HashSet<>();



}
