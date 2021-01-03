package control;

import game.Room;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import task.RandString;

import javax.crypto.spec.PSource;
import java.util.Random;

@Controller
public class RoomControl {

    /*
        room1:?????http??
        ???????
        http://www.??.com/?room_id=9595
     */
    @RequestMapping("/room1")
    @ResponseBody
//    @RequestParam(value = "room_id")
    public String room1( String room_id) throws Exception {
        System.out.println("web??????" + room_id);
//        if (room_id.equals("99")) return null;
//        if (room_id.equals("88")) return "";
        if (Room.roomSet.contains(room_id)){
            //获取随机数rand
            String rand = "";
            System.out.println("uesedNum:" + RandString.uesedNum);
            while (RandString.uesedNum < 2040) {
                rand = RandString.randList.get(RandString.uesedNum);
                RandString.uesedNum ++;
                break;
            }
            // 对应roomID
            Room.roomIdMapTemp.put(rand, room_id);
            //http://www.??.com/?RoomIdTemp=3ERWhE5W88&pw=123  -->
            return "RoomIdTemp:" + rand;
        }
        return null;
    }

    /*
        room2:房间密码及身份权限认定
        密码：管理员密码    --> 房主
        密码：玩家密码      --> 玩家
        http://www.??.com/?RoomIdTemp=977927&pw=123
        根据RoomIdTemp找到房间号，再验证密码
     */
    @RequestMapping("/room2")
    @ResponseBody
    public String room2( String RoomIdTemp, String pw) throws Exception {
        // roomIdMapTemp????
        if (Room.roomIdMapTemp.containsKey(RoomIdTemp)){
            // ????????
            String roomId = Room.roomIdMapTemp.get(RoomIdTemp);
            System.out.println("roomAdminSet:" + Room.roomAdminSet.size());
            //2.身份判断（玩家）
            if (Room.roomPlayerSet.size() > 10) {
                // 房间人满不反回值
            } else {
                // ?????
                // a.??????
                String player = Room.roomPlayerPWMap.get(roomId);
                if (player == null) return "";
                if (player.equals(pw)) {
                    return "{Player,Player2}";

                }
                // RoomIdTemp失效
                Room.roomIdMapTemp.remove(RoomIdTemp);
            }
            //1.身份判断（管理员）
            if (Room.roomAdminSet.size() > 0) {
                // 管理员已经存在
            } else {
                // 管理员不存在
                // a.???????
                String admin = Room.roomAdminPWMap.get(roomId);
                if (admin == null) return "";
                if (admin.equals(pw)) {
                    return "{Admin,Player1}";
                }
            }

        }
        return null;
    }

    // ????
    @RequestMapping("/room/rm/player")
    @ResponseBody
    public String removePlayer(String roomID, String adminPW) {
        // ???????
        //1.??????
        if (Room.roomSet.contains(roomID)) {
            if (Room.roomAdminPWMap.get(roomID).contains(adminPW)) {
                // ????
                // ???????
                Room.roomPlayerSet.remove(roomID);
            }
        }
        return "true";
    }

    // 创建房间
    @RequestMapping("/add/room/r")
    @ResponseBody
    public String addRoomId(String room_id, String AdminPW, String PlayerPW) {
        // ????
        Room.roomSet.add(room_id);
        // ?????
        Room.roomAdminPWMap.put(room_id, AdminPW);
        // ????
        Room.roomAdminPWMap.put(room_id, PlayerPW);
        // ????
        return "????" + room_id + "\n" +
                "?????" + PlayerPW + "\n" +
                "??????" + AdminPW;
    }

    // ??????
    @RequestMapping("/change/room/pw")
    @ResponseBody
    public String changeRoomPW(String room_id, String oldPlayerPW, String newPlayerPW) {
        //??????
        if (Room.roomSet.contains(room_id)) {
            // ???????
            if (Room.roomPlayerPWMap.get(room_id).equals(oldPlayerPW)) {
                // ????
                Room.roomPlayerPWMap.remove(room_id);
                Room.roomPlayerPWMap.put(room_id, newPlayerPW);
            } else return "false";
        } else return "false";
        return "true";
    }

}
