package task;

import game.Room;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RoomTask {

    boolean bool = false;

    // 自动初始化一个房间 9595、123、456
    @Scheduled(cron = "0/5 * * * * ?") //每隔5秒隔行一次
    public void creatRoom() {
        String room_id = "9595";
        String AdminPW = "123";
        String PlayerPW = "456";
        if (!bool) {
            // 创建房间
            Room.roomSet.add(room_id);
            // 管理员密码
            Room.roomAdminPWMap.put(room_id, AdminPW);
            // 玩家密码
            Room.roomPlayerPWMap.put(room_id, PlayerPW);

            System.out.println("房间号：" + room_id + "\n" +
                    "玩家密码：" + PlayerPW + "\n" +
                    "管理员密码：" + AdminPW);

            bool = true;
        }
    }

}
