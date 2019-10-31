package com.fh.shop.api.member.controller;

import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServiceResponse;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.member.biz.IMemberService;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.NoteUtil;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("members")

public class MemberController {
    @Resource(name = "memberService")
    private IMemberService memberService;
    @Autowired
    private HttpServletRequest request;
    //会员登录
    @PostMapping(value = "login")
    public ServiceResponse login(Member member){
        return memberService.login(member);
    }

    @PostMapping(value = "loginPhone")
    public ServiceResponse loginPhone(Member member,String code){
        return memberService.loginPhone(member,code);
    }

    //会员注册
    @PostMapping
    public ServiceResponse addMember(Member member,String code){
        return memberService.addMember(member,code);
    }

    //会员是否存在
    @GetMapping(value = "queyMemberName")
    public ServiceResponse queryMemberName( String memberName){
        return memberService.queryMemberName(memberName);
    }

    //会员手机号是否被使用
    @GetMapping(value = "queryPhone")
    public ServiceResponse queryPhone( String phone){
        return memberService.queryPhone(phone);
    }

    @GetMapping(value = "LoginQueryPhone")
    public ServiceResponse LoginQueryPhone( String phone){
        return memberService.LoginQueryPhone(phone);
    }

    //会员邮箱是否被使用
    @GetMapping(value = "queryEmail")
    public ServiceResponse queryEmail( String email){
        return memberService.queryEmail(email);
    }

    //登录成功之后 在request中获取会员信息
    @GetMapping(value = "findMemberName")
    @Check
    public ServiceResponse findName(){
        MemberVo member = (MemberVo) request.getAttribute(SystemConstant.MEMBER);
        String realName = member.getRealName();
        return ServiceResponse.success(realName);
    }

    //退出登录
    @GetMapping("loginOut")
    @Check
    public ServiceResponse loginOut(){
        MemberVo member = (MemberVo) request.getAttribute(SystemConstant.MEMBER);
        String memberName = member.getMemberName();
        String uuid = member.getUuid();
        Long id = member.getId();
        RedisUtil.del(KeyUtil.buildsign(id,memberName,uuid));
        return ServiceResponse.success();
    }


}
