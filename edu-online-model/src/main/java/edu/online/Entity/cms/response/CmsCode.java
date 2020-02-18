package edu.online.Entity.cms.response;

import edu.online.model.response.ResultCode;

/**
 * @Classname CmsCode
 * @Description TODO
 * @Date 2020/2/11 18:22
 * @Created by zhoutao
 */
public enum CmsCode implements ResultCode {
    CMS_ADDPAGE_EXISTSNAME(false,24001,"页面信息已存在！"),
    CMS_FINDPAGE_NotEXISTSNAME(false,24000,"页面信息不存在！"),
    CMS_GENERATEHTML_DATAURLISNULL(false,24002,"从页面信息中找不到获取数据的url！"),
    CMS_GENERATEHTML_DATAISNULL(false,24003,"根据页面的数据url获取不到数据！"),
    CMS_GENERATEHTML_TEMPLATEISNULL(false,24004,"模板文件为空！"),
    CMS_GENERATEHTML_HTMLISNULL(false,24005,"生成的静态html为空！"),
    CMS_GENERATEHTML_SAVEHTMLERROR(false,24006,"保存静态html出错！"),
    CMS_COURSE_PERVIEWISNULL(false,24007,"预览页面为空！"),
    CMS_GENERATEHTML_TEMPLATEI_EXIST(false,24008,"模板信息已存在！"),
    CMS_GENERATEHTML_TEMPLATEI_NotEXIST(false,24009,"模板信息不存在！"),
    CMS_UPLOAD_TEMPLATEFILE_FIAL(false,24010,"上传模板文件失败！"),
    CMS_DELETE_TEMPLATEFILE_FIAL(false,24013,"删除模板文件失败！"),
    CMS_TEMPLATEFILE_NotEXISTS(false,24011,"模板文件不存在！"),
    CMS_UPLOAD_URL_ERROR(false,24012,"上传失败,路径不正确！"),
    CMS_Site_EXISTS(false,24014,"该站点已存在！"),
    CMS_Site_NotEXISTS(false,24015,"该站点不存在！");
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    CmsCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
