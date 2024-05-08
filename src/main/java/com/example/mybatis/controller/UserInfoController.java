package com.example.mybatis.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.mybatis.dto.UserAddRequest;
import com.example.mybatis.dto.UserSearchRequest;
import com.example.mybatis.dto.UserUpdateRequest;
import com.example.mybatis.entity.UserInfo;
import com.example.mybatis.service.UserInfoService;

/**
 * ユーザー情報 Controller
 */
@Controller
public class UserInfoController {

    /**
     * ユーザー情報 Service
     */
    @Autowired
    private UserInfoService userInfoService;

    /**
     * ユーザー情報一覧画面を表示
     * @param model Model
     * @return ユーザー情報一覧画面
     */
    @GetMapping(value = "/user/list")//このURLにアクセスしたら
    public String displayList(Model model) {
        List<UserInfo> userList = userInfoService.findAll();//UserInfoデータベースを全件検索して指定された情報を画面に返却
        model.addAttribute("userlist", userList);
        model.addAttribute("userSearchRequest", new UserSearchRequest());
        return "user/search";//template配下のuser/search.htmlを呼び出す
    }

    /**
     * ユーザー新規登録画面を表示
     * @param model Model
     * @return ユーザー情報一覧画面
     */
    @GetMapping(value = "/user/add")
    public String displayAdd(Model model) {
        model.addAttribute("userAddRequest", new UserAddRequest());
        return "user/add";//template配下のuser/add.htmlを呼び出す
    }

    /**
     * ユーザー編集画面を表示
     * @param id ユーザーID
     * @param model Model
     * @return ユーザー編集画面
     */
    @GetMapping("/user/{id}/edit")
    public String displayEdit(@PathVariable Long id, Model model) {
        UserInfo user = userInfoService.findById(id);
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setId(user.getId());
        userUpdateRequest.setId(user.getId());
        userUpdateRequest.setName(user.getName());
        userUpdateRequest.setPhone(user.getPhone());
        userUpdateRequest.setAddress(user.getAddress());
        model.addAttribute("userUpdateRequest", userUpdateRequest);
        return "user/edit";//template配下のuser/edit.htmlを呼び出す
    }

    /**
     * ユーザー情報検索
     * @param userSearchRequest リクエストデータ
     * @param model Model
     * @return ユーザー情報一覧画面
     */
    @RequestMapping(value = "/user/search", method = RequestMethod.POST)///user/searchをhttp postで呼び出し
    public String search(@ModelAttribute UserSearchRequest userSearchRequest, Model model) {
        List<UserInfo> userList = userInfoService.search(userSearchRequest);//userInfoServiceのserchのメソッド呼び出し
        model.addAttribute("userlist", userList);//結果を画面上に表示
        return "user/search";//user/search画面を表示
    }

    /**
     * ユーザー情報削除（論理削除）
     * @param id ユーザーID
     * @param model Model
     * @return ユーザー情報一覧画面
     */
    @GetMapping("/user/{id}/delete")
    public String delete(@PathVariable Long id, Model model) {
        // ユーザー情報の削除
        userInfoService.delete(id);//userInfoServiceのdeleteのメソッド呼び出し
        return "redirect:/user/list";//結果を/user/list画面上に表示
    }

    /**
     * ユーザー新規登録
     * @param userRequest リクエストデータ
     * @param model Model
     * @return ユーザー情報一覧画面
     */
    @RequestMapping(value = "/user/create", method = RequestMethod.POST)//新規登録ボタンが押されたときに呼び出し
    public String create(@Validated @ModelAttribute UserAddRequest userRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // 入力チェックエラーの場合
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("validationError", errorList);
            return "user/add";
        }
        // ユーザー情報の登録
        userInfoService.save(userRequest);//入力が問題なければuserInfoServiceを呼び出して登録
        return "redirect:/user/list";//user/listにリダイレクト
    }

    /**
     * ユーザー更新
     * @param userRequest リクエストデータ
     * @param model Model
     * @return ユーザー情報詳細画面
     */
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)///user/update内のボタンが押されたときに呼び出し
    public String update(@Validated @ModelAttribute UserUpdateRequest userUpdateRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("validationError", errorList);
            return "user/edit";
        }
        // ユーザー情報の更新
        userInfoService.update(userUpdateRequest);//入力が問題なければuserInfoServiceを呼び出して更新
        return "redirect:/user/list";//user/listにリダイレクト
    }
}