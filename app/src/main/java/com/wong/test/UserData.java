package com.wong.test;

import com.wong.utils.DESEncryption;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ysh on 2016/7/8.
 */
public class UserData{
    private static int userId;//用户ID
    private static int orgId;//组织ID
    private static int roleId;//角色ID
    private static String orgName;//组织名称

    private static String moduleName; //模块名称
    private static String loginUser;//用户名
    private static String cellBrand;//手机品牌
    private static String androidVersion;//android版本
    private static String cellModel;//手机型号
    private static String release; //app发布版本号
    private static String timestamp;//时间戳


    private static String cid; //个推使用的client ID

    public static String getCid() {
        return cid;
    }

    public static void setCid(String cid) {
        UserData.cid = cid;
    }


    public static String getModuleName() {
        return moduleName;
    }

    public static void setModuleName(String moduleName) {
        UserData.moduleName = moduleName;
    }

    public static String getOrgName() {
        return orgName;
    }

    public static void setOrgName(String orgName) {
        UserData.orgName = orgName;
    }

    public static String getRelease() {
        return release;
    }

    public static void setRelease(String release) {
        UserData.release = release;
    }


    public static String getCellModel() {
        return cellModel;
    }

    public static void setCellModel(String cellModel) {
        UserData.cellModel = cellModel;
    }

    public static String getAndroidVersion() {
        return androidVersion;
    }

    public static void setAndroidVersion(String androidVersion) {
        UserData.androidVersion = androidVersion;
    }

    public static String getCellBrand() {
        return cellBrand;
    }

    public static void setCellBrand(String cellBrand) {
        UserData.cellBrand = cellBrand;
    }

    public static String getLoginUser() {
        return loginUser;
    }

    public static void setLoginUser(String loginUser) {
        UserData.loginUser = loginUser;
    }

    public static int getRoleId() {
        return roleId;
    }

    public static void setRoleId(int roleId) {
        UserData.roleId = roleId;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        UserData.userId = userId;
    }

    public static int getOrgId() {
        return orgId;
    }

    public static void setOrgId(int orgId) {
        UserData.orgId = orgId;
    }

    public static String getTimestamp() {

        if(UserData.timestamp == null){
            Date d = new Date();
            SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            UserData.timestamp = ss.format(d);
        }
        return UserData.timestamp;
    }

    public static void setTimestamp(String timestamp) {
        UserData.timestamp = timestamp;
    }

    public static String getJson() {
        String json = "{\"userId\":\"" + userId + "\",\"orgId\":\"" + orgId + "\",\"roleId\":" + roleId + "\",\"loginUser\":\"" + loginUser + "\",\"cellBrand\":\"" + cellBrand + "\",\"androidVersion\":\"" + androidVersion + "\",\"cellModel\":\"" + cellModel + "\",\"release\":\"" + release + "\",\"timestamp\":\"" + timestamp + "\"";
        return json;
    }

    public static String getJsonToWebService() {
        String json = "{\"UserId\":\"" + userId + "\",\"OrgId\":\"" + orgId + "\",\"EquipmentBrand\":\"" + cellBrand + "\",\"SystemVersion\":\"" + androidVersion + "\",\"EquipmentModel\":\"" + cellModel + "\",\"SoftwareVersion\":\"" + release + "\",\"CId\":\"helloworld\"}";
        Date d = new Date();
        SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DESEncryption desEncryption = new DESEncryption();
        String dateEncript = desEncryption.DESEncrypt(ss.format(d));
        json = "_TE_" + json + "_TE_" + dateEncript;
        return json;
    }

    //加密时间
    public static String getCryptoCurrentTime(){
        DESEncryption desEncryption = new DESEncryption();
        String dateEncript = desEncryption.DESEncrypt(UserData.getTimestamp());
        return dateEncript;
    }

}
