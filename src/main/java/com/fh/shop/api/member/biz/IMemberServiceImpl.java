package com.fh.shop.api.member.biz;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServiceResponse;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.member.mapper.IMemberMapper;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.Md5Util;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service("memberService")
public class IMemberServiceImpl implements IMemberService {
    @Autowired
    private IMemberMapper memberMapper;

    @Override
    public ServiceResponse addMember(Member member,String code) {
        if(StringUtils.isEmpty(member.getPassword())){
            return ServiceResponse.LoginStatus(ResponseEnum.MEMBER_PASSWORD_ISNULL);
        }
        String password = member.getPassword();
        String md5 = Md5Util.md5(Md5Util.md5(password));
        member.setPassword(md5);
        //为空判断
        String phone = member.getPhone();
        String email = member.getEmail();
        String memberName = member.getMemberName();
        if(StringUtils.isEmpty(memberName)){
            return ServiceResponse.error(ResponseEnum.MEMBER_NAME_ISNULL);
        }
        if(StringUtils.isEmpty(phone)){
            return ServiceResponse.error(ResponseEnum.MEMBER_PHONE_ISNULL);
        }
        if(StringUtils.isEmpty(email)){
            return ServiceResponse.error(ResponseEnum.MEMBER_EMAIL_ISNULL);
        }

        //判断输入的验证码
        String node = RedisUtil.get(KeyUtil.buildSMS(phone));
        if(StringUtils.isEmpty(node)){
            return ServiceResponse.error(ResponseEnum.MEMBER_SMS_ISEXPIRE);
        }
        if(!node.equals(code)){
            return ServiceResponse.error(ResponseEnum.REGISTER_NOTE_IS_ERROR);
        }
        //判断会员名唯一
        QueryWrapper<Member> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("memberName",member.getMemberName());
        Member member1 = memberMapper.selectOne(queryWrapper);
        if(member1!=null){
            return ServiceResponse.error(ResponseEnum.MEMBER_NAME_EXist);
        }
        //判断手机号唯一
        QueryWrapper<Member> queryWrapperPhone=new QueryWrapper<>();
        queryWrapperPhone.eq("phone",phone);
        Member memberPhone = memberMapper.selectOne(queryWrapperPhone);
        if(memberPhone!=null){
            return ServiceResponse.error(ResponseEnum.MEMBER_PHONE_EXist);
        }
        //判断邮箱唯一
        QueryWrapper<Member> queryPhone=new QueryWrapper<>();
        queryPhone.eq("email",email);
        Member memberEmail = memberMapper.selectOne(queryPhone);
        if(memberEmail!=null){
            return ServiceResponse.error(ResponseEnum.MEMBER_EMAIL_EXIST);
        }


        memberMapper.insert(member);
        return ServiceResponse.success();
    }

    @Override
    public ServiceResponse queryMemberName(String memberName) {
        if(StringUtils.isEmpty(memberName)){
            return ServiceResponse.error(ResponseEnum.MEMBER_NAME_ISNULL);
        }
        QueryWrapper<Member> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("memberName",memberName);
        Member member = memberMapper.selectOne(queryWrapper);
        if(member!=null){
            return ServiceResponse.error(ResponseEnum.MEMBER_NAME_EXist);
        }
        return ServiceResponse.success();
    }

    @Override
    public ServiceResponse queryPhone(String phone) {
        if(StringUtils.isEmpty(phone)){
            return ServiceResponse.error(ResponseEnum.MEMBER_PHONE_ISNULL);
        }
        QueryWrapper<Member> queryWrapperPhone=new QueryWrapper<>();
        queryWrapperPhone.eq("phone",phone);
        Member memberPhone = memberMapper.selectOne(queryWrapperPhone);
        if(memberPhone!=null){
            return ServiceResponse.error(ResponseEnum.MEMBER_PHONE_EXist);
        }
        return ServiceResponse.success();
    }

    @Override
    public ServiceResponse LoginQueryPhone(String phone) {
        if(StringUtils.isEmpty(phone)){
            return ServiceResponse.error(ResponseEnum.MEMBER_PHONE_ISNULL);
        }
        QueryWrapper<Member> queryWrapperPhone=new QueryWrapper<>();
        queryWrapperPhone.eq("phone",phone);
        Member memberPhone = memberMapper.selectOne(queryWrapperPhone);
        if(memberPhone==null){
            return ServiceResponse.error(ResponseEnum.MEMBER_PHONE_NOT_REGISTER);
        }
        return ServiceResponse.success();
    }

    @Override
    public ServiceResponse queryEmail(String email) {
        if(StringUtils.isEmpty(email)){
            return ServiceResponse.error(ResponseEnum.MEMBER_EMAIL_ISNULL);
        }
        QueryWrapper<Member> queryPhone=new QueryWrapper<>();
        queryPhone.eq("email",email);
        Member memberEmail = memberMapper.selectOne(queryPhone);
        if(memberEmail!=null){
            return ServiceResponse.error(ResponseEnum.MEMBER_EMAIL_EXIST);
        }
        return ServiceResponse.success();
    }

    @Override
    public ServiceResponse login(Member member) {
        String memberName = member.getMemberName();
        String password = member.getPassword();
        //非空判断
        if(StringUtils.isEmpty(memberName)){
            return ServiceResponse.error(ResponseEnum.MEMBER_NAME_ISNULL);
        }
        if(StringUtils.isEmpty(password)){
            return ServiceResponse.error(ResponseEnum.MEMBER_PASSWORD_ISNULL); 
        }
        //验证会员是否存在
        QueryWrapper<Member> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("memberName",memberName);
        Member memberInfo = memberMapper.selectOne(queryWrapper);
        if(memberInfo==null){
            return ServiceResponse.error(ResponseEnum.MEMBER_NAME_IS_EXIST);
        }
        String md5= Md5Util.md5(Md5Util.md5(password));
        //密码是否正确
        if(!md5.equals(memberInfo.getPassword())){
            return ServiceResponse.error(ResponseEnum.MEMBER_PASSWORD_IS_ERROR);
        }
        Long id = memberInfo.getId();
        MemberVo memberVo=new MemberVo();
        memberVo.setId(id);
        memberVo.setMemberName(memberName);
        String uuid = UUID.randomUUID().toString();
        memberVo.setUuid(uuid);
        memberVo.setRealName(memberInfo.getRealName());
        String memberVoJson = JSONObject.toJSONString(memberVo);
        String base64 = Base64.getEncoder().encodeToString(memberVoJson.getBytes());
        System.out.println(base64);
        String sign = Md5Util.sign(base64, SystemConstant.SECRET);
        String sign64 = Base64.getEncoder().encodeToString(sign.getBytes());
        RedisUtil.setex(KeyUtil.buildsign(id,memberName,uuid),base64,SystemConstant.SMS_EXPRIE);
        System.out.println(base64+"."+sign64);
        return ServiceResponse.success(base64+"."+sign64);
    }


    @Override
    public ServiceResponse loginPhone(Member member,String code) {
        String phone = member.getPhone();
        if(StringUtils.isEmpty(phone)){
            return ServiceResponse.error(ResponseEnum.MEMBER_PHONE_ISNULL);
        }
        if(StringUtils.isEmpty(code)){
            return ServiceResponse.error(ResponseEnum.REGISTER_NOTE_IS_NULL);
        }
        //验证验证码是否正确
        String node = RedisUtil.get(KeyUtil.buildSMS(phone));
        if(StringUtils.isEmpty(node)){
            return ServiceResponse.error(ResponseEnum.MEMBER_SMS_ISEXPIRE);
        }
        if(!node.equals(code)){
            return ServiceResponse.error(ResponseEnum.REGISTER_NOTE_IS_ERROR);
        }

        //验证会员是否存在
        QueryWrapper<Member> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("phone",phone);
        Member memberInfo = memberMapper.selectOne(queryWrapper);
        if(memberInfo==null){
            return ServiceResponse.error(ResponseEnum.MEMBER_NAME_IS_EXIST);
        }

        String uuid = UUID.randomUUID().toString();
        Long id = memberInfo.getId();
        String realName = memberInfo.getRealName();
        String memberName = memberInfo.getMemberName();
        MemberVo memberVo=new MemberVo();
        memberVo.setId(id);
        memberVo.setRealName(realName);
        memberVo.setMemberName(memberName);
        memberVo.setUuid(uuid);

        String memberVoJson = JSONObject.toJSONString(memberVo);
        String base64 = Base64.getEncoder().encodeToString(memberVoJson.getBytes());
        System.out.println(base64);

        String sign = Md5Util.sign(base64, SystemConstant.SECRET);
        String sign64 = Base64.getEncoder().encodeToString(sign.getBytes());
        RedisUtil.setex(KeyUtil.buildsign(id,memberName,uuid),base64,SystemConstant.SMS_EXPRIE);
        System.out.println(base64+"."+sign64);
        return ServiceResponse.success(base64+"."+sign64);

    }




    @Override
    public Member findMember(String memberName) {
        QueryWrapper<Member> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("memberName",memberName);
        Member member = memberMapper.selectOne(queryWrapper);
        return member;
    }


}
