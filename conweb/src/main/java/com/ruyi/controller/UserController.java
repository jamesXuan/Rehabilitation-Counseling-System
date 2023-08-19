package com.ruyi.controller;

import com.ruyi.Entity.User;
import com.ruyi.dao.UserMapper;
import com.ruyi.util.IDUtils;
import com.sun.deploy.security.SelectableSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
public class UserController {

    @Autowired
    public UserMapper userMapper;

    //跳转到登录页面
    @RequestMapping("/tologin")
    public String tologinPage(){
        return "login";
    }

    //用户登录
    @RequestMapping("/login")
    public String login(User user , Model model, HttpSession session){
        User u = userMapper.getUserInfoByname(user.getUser_name());
        System.out.println("查到的用户名为："+u);
        System.out.println("前端返回的用户名为"+user.getUser_name());
        if(u==null) {
            model.addAttribute("usernametip", "该用户不存在！！");
            return "login";
        }
            //System.out.println("该用户不存在！！！");
        else if(u.getUser_password().equals(user.getUser_password())) {
            model.addAttribute("User",u);
            session.setAttribute("usersession",user.getUser_name());
            return "redirect:/index";
        }
        else {
            model.addAttribute("userpasswordtip", "密码错误！！！");
            return "login";
        }
    }

    //跳转用户注册页面
    @RequestMapping("/toregister")
    public String toregister(){
        return "Register";
    }

    //用户注册
    @RequestMapping("/register")
    public String register(User user,Model model){
        user.setUser_Id(IDUtils.getId());
        String emailpattern = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
        String phonepattern = "0?(13|14|15|17|18)[0-9]{9}";
        String passwordpattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$";
        //邮箱验证
        Pattern emailPattern = Pattern.compile(emailpattern);
        Matcher emailmatch = emailPattern.matcher(user.getUser_email());
        if(!emailmatch.matches()) {
            model.addAttribute("emailmsg", "邮箱格式不正确");
            return "Register";
        }
        //电话号码验证
        Pattern phonePattern = Pattern.compile(phonepattern);
        Matcher phonematch = phonePattern.matcher(user.getUser_phonenumber());
        if(!phonematch.matches()) {
            model.addAttribute("phonemsg", "手机号格式不正确");
            return "Register";
        }
        //密码验证
        Pattern passwordPattern = Pattern.compile(passwordpattern);
        Matcher passwordmatch = passwordPattern.matcher(user.getUser_password());
        if(!passwordmatch.matches()) {
            model.addAttribute("psdmsg", "必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-10之间");
            return "Register";
        }
        if(userMapper.getUserInfoByname(user.getUser_name())==null) {
            userMapper.insetUserInfo(user);
            return "redirect:/tologin";
        }
        else{
            model.addAttribute("usermsg", "该用户名已存在");
            return "Register";
        }
    }

    //用户信息
    @RequestMapping("/userinfo")
    public String userinfo(Model model,@RequestParam("User_name") String userName){
        User user = userMapper.getUserInfoByname(userName);
        System.out.println(user);
        model.addAttribute("User",user);
        return "/UserInfo";
    }
    //修改用户信息
    @RequestMapping("/touserupdate")
    public String userupdate(User user){
        System.out.println(user);
        userMapper.updateUserInfo(user);
        return "redirect:/tologin";
    }
}
