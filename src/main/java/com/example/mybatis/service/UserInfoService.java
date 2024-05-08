package com.example.mybatis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mybatis.dao.UserInfoMapper;
import com.example.mybatis.dto.UserAddRequest;
import com.example.mybatis.dto.UserSearchRequest;
import com.example.mybatis.dto.UserUpdateRequest;
import com.example.mybatis.entity.UserInfo;

/**
 * ユーザー情報 Service
 */
@Service
public class UserInfoService {

    /**
     * ユーザー情報 Mapper
     */
    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * ユーザー情報全件検索
     * @return 検索結果
     */
    public List<UserInfo> findAll() {//全件検索
        return userInfoMapper.findAll();//リポジトリクラスを呼び出して検索
    }

    /**
     * ユーザー情報主キー検索
     * @return 検索結果
     */
    public UserInfo findById(Long id) {//ID検索
        return userInfoMapper.findById(id);//リポジトリクラスを呼び出して検索
    }

    /**
     * ユーザー情報検索
     * @param userSearchRequest リクエストデータ
     * @return 検索結果
     */
    public List<UserInfo> search(UserSearchRequest userSearchRequest) {//
        return userInfoMapper.search(userSearchRequest);//リポジトリクラスを呼び出して検索
    }

    /**
     * ユーザ情報登録
     * @param userAddRequest リクエストデータ
     */
    public void save(UserAddRequest userAddRequest) {
        userInfoMapper.save(userAddRequest);//リポジトリクラスを呼び出して保存
    }

    /**
     * ユーザ情報更新
     * @param userEditRequest リクエストデータ
     */
    public void update(UserUpdateRequest userUpdateRequest) {
        userInfoMapper.update(userUpdateRequest);//リポジトリクラスを呼び出して更新
    }

    /**
     * ユーザー情報論理削除
     * @param id
     */
    public void delete(Long id) {
        userInfoMapper.delete(id);//リポジトリクラスを呼び出して削除
    }
}