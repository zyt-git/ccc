package com.fh.shop.api.member.biz;

import com.fh.shop.api.common.ServiceResponse;
import com.fh.shop.api.member.po.Member;

public interface IMemberService {
    ServiceResponse addMember(Member member,String code);

    ServiceResponse queryMemberName(String memberName);

    ServiceResponse queryPhone(String phone);

    ServiceResponse queryEmail(String email);

    ServiceResponse login(Member member);

    Member findMember(String memberName);

    ServiceResponse loginPhone(Member member,String code);

    ServiceResponse LoginQueryPhone(String phone);
}
