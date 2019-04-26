package com.supermap.demo.test.constants;

/**
 * Created by zenghaiqiang on 2018/11/16.
 * 描述：
 */

public class Constant {

    /******** sd卡 目录管理*************/
    public static final String SD_SAVE_DIR = "sdfiles";//需要复制到sd的assets数据
    public static final String SD_PROJ_DIR = "/SHPDOneMap";//项目数据路径

    public static final String SD_SM_LICENSE_DIR = SD_PROJ_DIR + "/SMData/license";//超图许可证书路径
    public static final String SD_SM_TEMP_DIR = SD_PROJ_DIR + "/SMData/temp";//超图临时路径
    public static final String SD_SM_WEBCACHE_DIR = SD_PROJ_DIR + "/SMData/WebCache";//超图web缓存路径
    public static final String SD_SM_FONTS_DIR = SD_PROJ_DIR + "/SMData/fonts";//超图字体文件路径
    public static final String SD_SM_DB_WS = SD_PROJ_DIR + "/SMData/GeometryInfo/SHPD-Business.smwu";//超图数据工作空间
    public static final String SD_SM_DB_BASE_MAP = SD_PROJ_DIR + "/SMData/GeometryInfo/baseMap/baseMap.sci";//基础底图sci路径

    public static final String SM_BUSINESS_MAP_NAME = "businessMap"; //超图数据工作空间中的业务地图名称
    public static final String SM_BUSINESS_DS_ALIAS = "PD-Business"; //超图数据工作空间中的业务数据源别名

    public static final String IS_FIRST = "is_first";//第一次安装运行
    public static final String DB_NAME = "ONEMAP.db";
    public static final String USER_ID = "user_id";
    public static final int PAGE_SIZE = 10;


    public static final String DIALOG_LOCATION = "location";
    public static final String DIALOG_DISTANCE = "distance";
    public static final String DIALOG_LABEL = "label";
    public static final String DIALOG_NAME = "name";
    public static final String DIALOG_BID = "bid";

    public static final String DIALOG_HINT = "hint";
    public static final String DIALOG_CONTENT = "content";
    public static final String DIALOG_LEFT = "left";
    public static final String DIALOG_RIGHT = "right";
    public static final String IS_LOGIN = "is_login";
    public static final String CLOCK_TYPE = "clock_type";
    public static final String CURRENT_TIME = "current_time";
    public static final String IS_TIME_BEAN_RECEIVE = "is_time_bean_receive";

    public static final int PERMISSIONS_RESULT_CODE = 1001;
    public static final int COMMON_CODE = 110;

    public static final String DATE_FORMAT_0 = "yyyy-MM-dd";
    public static final String DATE_FORMAT_1 = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_2 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_3 = "yyyy";
    public static final String DATE_FORMAT_4 = "yyyy年";
    public static final String DATE_FORMAT_5 = "MM月dd日";
    public static final String DATE_FORMAT_6 = "yyyy/MM/dd";
    public static final String DATE_FORMAT_7 = "yyyy/MM";

    public static final String BAIDU_AK = "XNC2spo4QBAIkqlfTEMKeNdzu2n8p615";


    public static final int SELECT_TIME = 0x0086;


    public static final int REQUEST_CODE_SEARCH = 0x0055;//搜索页请求码
    public static final int REQUEST_CODE_PERIPHERY = 0x0056;//周边页请求码

    public static final String SEARCH_NAME = "search_name";
    public static final String SEARCH_TYPE = "search_type";//周边搜索类型   0 ： 控规   2 ：审批     5 : 关键字搜索
    public static final String SEARCH_RADIUS = "search_radius";//周边搜索半径
    public static final String SEARCH_YEAR = "search_year";//年份
    public static final String SEARCH_HISTORYDB = "search_historydb";//历史记录实体类
    public static final String IS_BACK = "is_back";//是否按返回键
    public static final String PAGE_TYPE = "page_type";//来源界面

//    搜索类型

    public static final int SEARCH_TYPE_MAIN = 0;//首页主界面
    public static final int SEARCH_TYPE_SEARCHKEY = 1;//关键字搜索
    public static final int SEARCH_TYPE_APPROVAL = 2;//审批搜索
    public static final int SEARCH_TYPE_PLAN = 3;//土地规划搜索
    public static final int SEARCH_TYPE_BUSINESS = 4;//业务数据
    public static final int SEARCH_TYPE_POI = 5;//poI数据

    public static final int PAGE_TYPE_MAIN = 0;
    public static final int PAGE_TYPE_PERIPHERY = 1;
    public static final int PAGE_TYPE_SEARCH = 2;





}
