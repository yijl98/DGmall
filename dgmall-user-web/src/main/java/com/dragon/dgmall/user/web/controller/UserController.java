package com.dragon.dgmall.user.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dragon.dgmall.api.bean.UmsMember;
import com.dragon.dgmall.api.bean.UmsMemberReceiveAddress;
import com.dragon.dgmall.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {

    @Reference
    UserService userService;


    //添加会员
    @RequestMapping("addUser")
    @ResponseBody
    public String addUser(String umsMember) {

        String result = null;

        int i = userService.addUser(umsMember);

        if (i == 1) {
            result = "插入成功";
        } else {
            result = "插入失败";
        }

        return result;
    }

    //删除会员
    @RequestMapping("deleteUser")
    @ResponseBody
    public String deleteUser(String umsMember) {
        String result = null;
        int i = userService.deleteUser(umsMember);

        if (i == 1) {
            result = "删除成功";
        } else {
            result = "删除失败";
        }

        return result;
    }

    //更新会员
    @RequestMapping("updateUser")
    @ResponseBody
    public String updateUser(String umsMember, String username) {
        String result = null;
        int i = userService.updateUser(umsMember, username);

        if (i == 1) {
            result = "更新成功";
        } else {
            result = "更新失败";
        }

        return result;
    }

    //获得所有会员
    @RequestMapping("getAllUser")
    @ResponseBody
    public List<UmsMember> getAllUser() {

        List<UmsMember> umsMember = userService.getAllUser();

        return umsMember;
    }

    //跳转登录首页
    @RequestMapping("index")
    @ResponseBody
    public String index() {
        return "Project is ok!";
    }

    //通过会员ID获得收货地址
    @RequestMapping("getMemberReceiveAddressByMemberId")
    @ResponseBody
    public List<UmsMemberReceiveAddress> getMemberReceiveAddressByMemberId(String memberId) {

        List<UmsMemberReceiveAddress> List = userService.getMemberReceiveAddressByMemberId(memberId);

        return List;
    }


    //获得所有收货地址
    @RequestMapping("selectAllMemberReceiveAddress")
    @ResponseBody
    public List<UmsMemberReceiveAddress> selectAllMemberReceiveAddress(String id, String memberId) {
        List<UmsMemberReceiveAddress> list = userService.selectAllMemberReceiveAddress();


        return list;
    }

    //添加收货地址
    @RequestMapping("addMemberReceiveAddress")
    @ResponseBody
    public String addMemberReceiveAddress(String id, String memberId) {
        String result = null;
        int i = userService.addMemberReceiveAddress(id, memberId);

        if (i == 1) {
            result = "添加收货地址成功";
        } else {
            result = "添加收货地址失败";
        }

        return result;
    }

    //删除收货地址
    @RequestMapping("deleteMemberReceiveAddress")
    @ResponseBody
    public String deleteMemberReceiveAddress(String id) {
        String result = null;
        int i = userService.deleteMemberReceiveAddress(id);

        if (i == 1) {
            result = "删除收货地址成功";
        } else {
            result = "删除收货地址失败";
        }

        return result;
    }

    //更改收获地址
    @RequestMapping("updateMemberReceiveAddress")
    @ResponseBody
    public String updateMemberReceiveAddress(String id, String memberId) {
        String result = null;
        int i = userService.updateMemberReceiveAddress(id, memberId);

        if (i == 1) {
            result = "更改收货地址成功";
        } else {
            result = "更改收货地址失败";
        }

        return result;
    }


}
