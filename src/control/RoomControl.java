package control;

import game.Room;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import task.RandString;

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
            //?????rand
            String rand = "";
            System.out.println("uesedNum:" + RandString.uesedNum);
            while (RandString.uesedNum < 2040) {
                rand = RandString.randList.get(RandString.uesedNum);
                RandString.uesedNum ++;
                break;
            }
            // ??????roomID
            Room.roomIdMapTemp.put(rand, room_id);
            //http://www.??.com/?RoomIdTemp=977927&pw=123  -->
            return "RoomIdTemp:" + rand;
        }
        return null;
    }

    /*
        room2:?????http??
        ??????
        http://www.??.com/?RoomIdTemp=977927&pw=123
     */
    @RequestMapping("/room2")
    @ResponseBody
    public String room2( String RoomIdTemp, String pw) throws Exception {
        // roomIdMapTemp????
        if (Room.roomIdMapTemp.containsKey(RoomIdTemp)){
            // ????????
            String roomId = Room.roomIdMapTemp.get(RoomIdTemp);
            //1.????????
            if (Room.roomAdminSet.contains(roomId)) {
                // ?????
            } else {
                // ??????
                // a.???????
                String admin = Room.roomAdminPWMap.get(roomId);
                if (admin == null) return "";
                if (admin.equals(pw)) {
                    return "{Admin,Player1}";
                }
            }
            //2.???????
            if (Room.roomPlayerSet.contains(roomId)) {
                // ????
            } else {
                // ?????
                // a.??????
                String player = Room.roomAdminPWMap.get(roomId);
                if (player == null) return "";
                if (player.equals(pw)) {
                    return "{Player,Player2}";
                } else {
                    // ????
                    Room.roomIdMapTemp.remove(RoomIdTemp);
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

    // ????
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
