package com.xtelsolution.xmec.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Admin on 1/23/2017.
 */

public class Constant {
    public static final String INTENT_ACTION_DETAIL_CONTROL_DISEASE = "intent_action_detail_control_disease";


    public static final String ACTION_HIDE_BOTTOM_BAR = "hide_bottom_bar";
    public static final String ACTION_SHOW_BOTTOM_BAR = "show_bottom_bar";

    /// body name and position
    public static float[][] POSITION_BODY_MAP={{1.1223971f, 1.5163009f,8.726857f,9.303358f },{1.7059582f,2.0415053f,8.880105f,9.369035f},{0.99839044f,1.4725337f,7.0046554f,8.361984f},{ 1.6986638f , 2.0415053f ,7.0046554f,8.361984f},{0.83845824f,1.4633446f,4.7480264f,6.1739473f},{1.7154919f,2.3403783f,4.7480264f,6.1739473f},{0.21357174f,0.5753479f,4.5396223f,5.2964573f},{2.6692653f,3.1954856f,4.550591f,5.1758027f},{0.48764458f,0.71786577f,3.5195403f,4.2105637f},{2.4390442f,2.756969f,3.6511638f,4.342187f},{0.6740143f,0.9809761f,2.389772f,3.1356382f},{2.1649716f,2.471933f,2.5104268f, 3.1575756f},{1.112531f,2.0553422f, 2.3678346f, 2.9491718f},{ 1.0029018f,2.1430454f,3.6950383f,4.057003f},{1.2464038f,1.8372594f,0.15963212f,1.2980369f}};
    public static float[][] POSITION_BODY_MAP_BEHIND={{1.2912773f, 1.7369334f,8.760153f, 9.276758f },{1.8076721f,2.1825893f,8.654002f,9.262604f},{1.3195729f,1.6308246f,6.92727f,8.434622f},{ 1.8713374f , 2.196737f ,6.92727f,8.434622f},{1.019524f,1.6574059f, 4.9660854f,6.1574183f},{1.7956135f,2.5185466f,4.9660854f,6.1574183f},{0.8706848f,2.5717034f,4.2427764f,4.795895f},{0.15838337f,0.6261633f,4.455514f,5.18946f},{2.9119072f,3.4966323f,4.657615f,5.2958293f},{1.1152061f,2.3803387f,3.7428422f,4.0725856f},{0.67932016f,0.96636736f,3.5088303f,4.083223f},{2.6354916f,2.9225383f, 3.7960267f,4.3385086f},{0.8387907f,1.1683632f,2.391956f,3.104628f},{ 2.3378131f, 2.6673856f,2.4238667f,3.1259017f},{1.2912773f,2.196737f,0.1264972f,1.2941636f}};
    public static String[] NAME_BODY_PARTS_BEHIND={"Bàn Chân","Bàn Chân","Bắp chân","Bắp Chân","Đùi sau","Đùi sau","Mông","Bàn tay","Bàn tay","Hông","Cảng tay sau","Cảng tay sau","Bắp tay sau","Bắp tay sau","Đầu"};
    public static String[] NAME_BODY_PARTS={"Bàn Chân","Bàn Chân","Cẳng Chân","Cẳng Chân","Đùi","Đùi","Bàn tay","Bàn tay","Cánh tay","Cánh tay","Bắp tay","Bắp tay","Ngực","Bụng","Đầu"};

    public static final String USER_SESSION = "user_sesstion";
    public static final String USER_FULL_NAME  = "fullname";
    public static final String USER_GENDER  = "gender";
    public static final String USER_BIRTHDAY= "birthday";
    public static final String USER_PHONE_NUMBER= "phonenumber";
    public static final String USER_ADDRESS= "address";
    public static final String USER_AVATAR= "avatar";
    public static final String USER_WEIGHT= "weight";
    public static final String USER_HEIGHT= "height";
    public static final String PREFERENCE_NAME = "preference_xmec";


    public static final String MEDICAL_NAME="name";
    public static final String MEDICAL_BEGIN_TIME="begin_time";
    public static final String MEDICAL_END_TIME="end_time";
    public static final String MEDICAL_TYPE="type";
    public static final String MEDICAL_NOTE="note";
    public static final String MEDICAL_RESOURCES="resources";


    public static final String SERVER_AUTHEN = "http://124.158.5.112:9180/nipum/";
    public static final String GET_USER = "/user";
    public static final String SERVER_UPLOAD = "http://124.158.5.112:9180/s/files/upload";
    public static final String MEDICAL_REPORT_BOOK = "user/medical-report-book";

    public static final String SERVER_XMEC = "http://192.168.29.104:8080/v0.1";


    public static String getDate(long times){
        DateFormat sdf= new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date date = new Date(times);
        return sdf.format(date);
    }
//    public static String parseListString(List<String> list){
//        String rs ="[";
//        for (int i=0;i<list.size();i++){
//            rs+=list.get(i);
//            if (i<list.size()-1)
//                rs+=",";
//        }
//        rs+="]";
//        return rs;
//    }
}
