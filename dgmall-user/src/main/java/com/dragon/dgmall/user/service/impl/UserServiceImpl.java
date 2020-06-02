package com.dragon.dgmall.user.service.impl;

import com.dragon.dgmall.api.bean.UmsMember;
import com.dragon.dgmall.api.bean.UmsMemberReceiveAddress;
import com.dragon.dgmall.user.mapper.UmsMemberReceiveAddressMapper;
import com.dragon.dgmall.user.mapper.UserMapper;
import com.dragon.dgmall.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;


    @Override
    public List<UmsMember> getAllUser() {

        List<UmsMember> umsMember = userMapper.selectAll();

        return umsMember;
    }

    @Override
    public List<UmsMemberReceiveAddress> getMemberReceiveAddressByMemberId(String memberId) {

        UmsMemberReceiveAddress umsMemberReceiveAddress = new UmsMemberReceiveAddress();
        umsMemberReceiveAddress.setMemberId(memberId);

        List<UmsMemberReceiveAddress> select = umsMemberReceiveAddressMapper.select(umsMemberReceiveAddress);

        return select;
    }

    @Override
    public int addUser(String umsMember) {
        UmsMember umsmember = new UmsMember();
        umsmember.setId(umsMember);

        int result = userMapper.insert(umsmember);

        return result;
    }

    @Override
    public int deleteUser(String umsMember) {
        UmsMember umsmember = new UmsMember();
        umsmember.setId(umsMember);

        int result = userMapper.delete(umsmember);

        return result;
    }

    @Override
    public int updateUser(String umsMember, String username) {
        UmsMember umsmember = new UmsMember();
        umsmember.setId(umsMember);
        umsmember.setUsername(username);

        int result = userMapper.updateByPrimaryKey(umsmember);

        return result;
    }

    @Override
    public int addMemberReceiveAddress(String id, String memberId) {

        UmsMemberReceiveAddress UmsMemberReceiveAddress = new UmsMemberReceiveAddress();
        UmsMemberReceiveAddress.setId(id);
        UmsMemberReceiveAddress.setMemberId(memberId);

        int result = umsMemberReceiveAddressMapper.insert(UmsMemberReceiveAddress);

        return result;
    }

    @Override
    public int deleteMemberReceiveAddress(String id) {
        UmsMemberReceiveAddress UmsMemberReceiveAddress = new UmsMemberReceiveAddress();
        UmsMemberReceiveAddress.setId(id);

        int result = umsMemberReceiveAddressMapper.delete(UmsMemberReceiveAddress);

        return result;
    }

    @Override
    public int updateMemberReceiveAddress(String id, String memberId) {
        UmsMemberReceiveAddress UmsMemberReceiveAddress = new UmsMemberReceiveAddress();
        UmsMemberReceiveAddress.setId(id);
        UmsMemberReceiveAddress.setMemberId(memberId);

        int result = umsMemberReceiveAddressMapper.updateByPrimaryKey(UmsMemberReceiveAddress);

        return result;
    }

    @Override
    public List<UmsMemberReceiveAddress> selectAllMemberReceiveAddress() {

        List<UmsMemberReceiveAddress> list = umsMemberReceiveAddressMapper.selectAll();

        return list;
    }

}
