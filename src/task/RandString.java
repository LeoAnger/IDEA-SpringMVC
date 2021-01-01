package task;

import game.Room;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class RandString {

    public static List<String> randList = new ArrayList<>(2048);
    public static int uesedNum = 1000;
    public static boolean isGetRand = true;


    @Scheduled(cron = "0/5 * * * * ?")//每隔100秒隔行一次
    public void test2()
    {
        if (uesedNum >= 1000) {
            isGetRand = false;
            //重新随机
            System.out.println("RandString..");
            randList = new ArrayList<>(2048);
            for (int i = 0; i < 2048; i++) {
                randList.add(GenRandomStringByLen(10));
            }
            // 初始化使用
            uesedNum = 0;
            isGetRand = true;
        }
    }

    /**
     *
     * @方法名称 GenRandomStringByLen
     * @功能描述 <pre>生成传入的长度的密码</pre>
     * @作者    xingsfdz
     * @创建时间 2019年9月26日 下午10:39:03
     * @param len
     * @return
     */
    public static String GenRandomStringByLen(Integer len){
        String password = "";

        int[] pwdindex =new int[len];

        char[] numbers = { '0','1','2','3','4','5','6','7','8','9'};

        char[] upperLetters = {'A','B','C','D','E','F','G','H','I','J',
                'K','L','M','N','O','P','Q','R','S','T',
                'U','V','W','X','Y','Z'};

        char[] lowerLetters = {'a','b','c','d',
                'e','f','g','h','i','j','k','l','m','n',
                'o','p','q','r','s','t','u','v','w','x',
                'y','z'};


        char[] allCharacters = { '0','1','2','3','4','5','6','7','8','9',
                'A','B','C','D','E','F','G','H','I','J',
                'K','L','M','N','O','P','Q','R','S','T',
                'U','V','W','X','Y','Z','a','b','c','d',
                'e','f','g','h','i','j','k','l','m','n',
                'o','p','q','r','s','t','u','v','w','x',
                'y','z'};

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < pwdindex.length; i++) {
            sb.append(allCharacters[new Random().nextInt(62)]);
        }
        password = sb.toString();//先按传进来的长度生成一个随机密码
        //下面处理密码中必须包含大小写字母与数字
        int a = 0;
        int b = 0;
        int c = 0;
        a = new Random().nextInt(len);
        while(a == b){
            b = new Random().nextInt(len);
        }
        while(a == c || b == c){
            c = new Random().nextInt(len);
        }
        password = password.replace(password.charAt(a), lowerLetters[new Random().nextInt(26)]);//小写
        password = password.replace(password.charAt(b), numbers[new Random().nextInt(10)]);//数字
        password = password.replace(password.charAt(c), upperLetters[new Random().nextInt(26)]);//大写
        return password;
    }
}
