package com.dragon.dgmall.api.service;

import com.dragon.dgmall.api.bean.UmsMember;
import com.dragon.dgmall.api.bean.UmsMemberReceiveAddress;

import java.util.List;

public interface UserService {
    List<UmsMember> getAllUser();

    List<UmsMemberReceiveAddress> getMemberReceiveAddressByMemberId(String memberId);

    int addUser(String umsMember);

    int deleteUser(String umsMember);

    int updateUser(String umsMember, String username);

    int addMemberReceiveAddress(String id, String memberId);

    int deleteMemberReceiveAddress(String id);

    int updateMemberReceiveAddress(String id, String memberId);

    List<UmsMemberReceiveAddress> selectAllMemberReceiveAddress();
}
