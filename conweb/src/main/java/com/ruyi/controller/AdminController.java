package com.ruyi.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruyi.Entity.*;
import com.ruyi.dao.*;
import com.ruyi.util.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AdminController {
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    WelcomeMapper welcomeMapper;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    AnswerMapper answerMapper;
    @Autowired
    ConsulMapper consulMapper;

    //管理员登录
    @RequestMapping("/admin")
    public String toadminlogin(){
        return"adminlogin";
    }
    @RequestMapping("/adminlog")
    public String adminlogin(Admin admin, Model model, HttpSession session){
        Admin adm = adminMapper.getAdminInfoByname(admin.getAdmin_name());
        if(adm==null) {
            model.addAttribute("usernametip", "该用户不存在！！");
            return "adminlogin";
        }
        //System.out.println("该用户不存在！！！");
        else if(adm.getAdmin_password().equals(admin.getAdmin_password())) {
            model.addAttribute("Admin",adm);
            session.setAttribute("adminsession",adm.getAdmin_limit());
            return "redirect:/adminIndex";
        }
        else {
            model.addAttribute("userpasswordtip", "密码错误！！！");
            return "adminlogin";
        }
    }

    //用户管理
    @RequestMapping("/adminIndex")
    public String Adminindex(Model model){
        List<User> userinfo = userMapper.getUserinfo();
        model.addAttribute("userlist",userinfo);
        return"adminIndex";
    }

    //查找用户
    @RequestMapping("/queryuser")
    public String queryuser(@RequestParam("username") String username,Model model){
        User user = userMapper.getUserInfoByname(username);
        List<User> userlist =new ArrayList<>();
        userlist.add(user);
        model.addAttribute("userlist",user);
        return "adminIndex";
    }

    //删除用户
    @RequestMapping("/deleteuser")
    public String deleteuser(@RequestParam("User_Id") String id){
        userMapper.deleteUserInfo(id);
        return"redirect:/adminIndex";
    }

    //新增
    @RequestMapping("/toaddAdmin")
    public String toaddadmin(@RequestParam("type") String type,Model model){
        model.addAttribute("type",type);
        return "addAdmin";
    }
    @RequestMapping("/addAdmin")
    public String addAdmin(@RequestParam("type") String type,Answer answer,Question question,Welcome welcome,Article article){
        if(type.equals("answer")) {
            answer.setAnswer_Id(IDUtils.getId());
            answerMapper.insetAnsInfo(answer);
            return "redirect:/answerIndex";
        }
        else if(type.equals("question")){
            question.setQuestion_Id(IDUtils.getId());
            questionMapper.insetQuesInfo(question);
            return"redirect:/questionIndex";
        }
        else if(type.equals("article")){
            article.setArticle_id(IDUtils.getId());
            article.setArticle_viewnumber(0);
            article.setArticle_compliment(0);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = formatter.format(new Date());
            article.setArticle_time(time);
            articleMapper.insetarticleInfo(article);
            return"redirect:/articleIndex";
        }
        else if(type.equals("welcome")){
            welcome.setWelcome_Id(IDUtils.getId());
            welcomeMapper.insetWelInfo(welcome);
            return "redirect:/welcomeIndex";
        }
        return "";
    }

    //修改
    @RequestMapping("/toupdateuser")
    public String toUpdate(@RequestParam("ID") String id , Model model,@RequestParam("type") String type){
        if(type.equals("user")) {
            User user = userMapper.getUserInfoById(id);
            model.addAttribute("User", user);
        }
        else if(type.equals("answer")){
            Answer answer = answerMapper.getAnsinfoById(id);
            model.addAttribute("Answer",answer);
        }
        else if(type.equals("question")){
            Question question =questionMapper.getQuesInfoById(id);
            model.addAttribute("question",question);
        }
        else if(type.equals("welcome")){
            Welcome welcome =welcomeMapper.getWelById(id);
            model.addAttribute("welcome",welcome);
        }
        model.addAttribute("type", type);
        return "updateuser";
    }
    @RequestMapping("/updateuser")
    public String updateBook(User user,@RequestParam("type") String type,Answer answer,Question question,Welcome welcome){
        if(type.equals("user")) {
            userMapper.updateUserInfo(user);
            System.out.println(user);
            System.out.println(answer);
            return"redirect:/adminIndex";
        }
        else if(type.equals("answer")){
            answerMapper.updateAnsInfo(answer);
            System.out.println(answer);
            return"redirect:/answerIndex";
        }
        else if(type.equals("question")){
            questionMapper.updateQuesInfo(question);
            System.out.println(question);
            return "redirect:/questionIndex";
        }
        else if(type.equals("welcome")){
            welcomeMapper.updateWelInfo(welcome);
            System.out.println(welcome);
            return "redirect:/welcomeIndex";
        }
        return  "";
    }

    //管理员管理
    @RequestMapping("/manageIndex")
    public String managerindex(Model model, Admin admin,HttpSession session){
        List<Admin> admininfo = adminMapper.getAdmininfo(Integer.parseInt((String) session.getAttribute("adminsession")));
        model.addAttribute("adminlist",admininfo);
        //#httpServletRequest.getSession().getAttribute('usersession')
        System.out.println();
        return"manageIndex";
    }

    //删除管理员
    @RequestMapping("/deleteadmin")
    public String deleteadmin(@RequestParam("Admin_Id") String id){
        adminMapper.deleteAdminInfo(id);
        return"redirect:/manageIndex";
    }

    //问题模板展示
    @RequestMapping("/questionIndex")
    public String quesIndex(Model model){
        List<Question> quesinfo = questionMapper.getQuesinfo();
        model.addAttribute("queslist",quesinfo);
        return "questionIndex";
    }
    //删除问题
    @RequestMapping("/deleteques")
    public String deleteques(@RequestParam("Question_Id") String id){
        questionMapper.deleteQuesInfo(id);
        return "redirect:/questionIndex";
    }

    //问题查询
    @RequestMapping("queryquestion")
    public String queryquestion(@RequestParam("typename") String typename,Model model){
        Question ques = questionMapper.getQuesInfoBytype(typename);
        List<Question> questionList =new ArrayList<>();
        questionList.add(ques);
        model.addAttribute("queslist",questionList);
        return "questionIndex";
    }

    //欢迎页面展示
    @RequestMapping("/welcomeIndex")
    public String welindex(Model model){
        List<Welcome> welinfo = welcomeMapper.getWelinfo();
        model.addAttribute("wellist",welinfo);
        return "welcomeIndex";
    }
    //欢迎页面删除
    @RequestMapping("/deletewel")
    public String deletewelcome(@RequestParam("Welcome_Id") String id){
        welcomeMapper.deleteWelInfo(id);
        return "redirect:/welcomeIndex";
    }

    //欢迎页面查询
    @RequestMapping("querywelcome")
    public String querywelcome(@RequestParam("titlename") String title,Model model){
        Welcome wel = welcomeMapper.getWelBytitle(title);
        List<Welcome> welcomeList =new ArrayList<>();
        welcomeList.add(wel);
        model.addAttribute("wellist",welcomeList);
        return "welcomeIndex";
    }

    //文章页面展示
    @RequestMapping("/articleIndex")
    public String articleindex(Model model){
        List<Article> articleinfo = articleMapper.getarticleinfo();
        model.addAttribute("articlelist",articleinfo);
        return "articleIndex";
    }
    //文章删除
    @RequestMapping("deletearticle")
    public String deletearticle(@RequestParam("Article_Id") String id){
        articleMapper.deletearticleInfo(id);
        return "redirect:/articleIndex";
    }

    //文章查询
    @RequestMapping("/queryarticle")
    public String queryarticle(@RequestParam("typename") String typename,Model model){
        Article article = articleMapper.getarticleBytype(typename);
        List<Article> articleList =new ArrayList<>();
        articleList.add(article);
        model.addAttribute("articlelist",articleList);
        return "articleIndex";
    }

    //答案模板页面展示
    @RequestMapping("/answerIndex")
    public String answerIndex(Model model){
        List<Answer> ansinfo = answerMapper.getAnsinfo();
        model.addAttribute("answerlist",ansinfo);
        return "answerIndex";
    }

    //答案模板删除
    @RequestMapping("/deleteans")
    public String deleteans(@RequestParam("Answer_Id") String id){
        answerMapper.deleteAnsInfo(id);
        return "redirect:/answerIndex";
    }
    //答案查询
    @RequestMapping("queryanswer")
    public String queryanswer(@RequestParam("typename") String typename,Model model){
        Answer ans = answerMapper.getAnsinfoBytype(typename);
        List<Answer> answerList =new ArrayList<>();
        answerList.add(ans);
        model.addAttribute("answerlist",answerList);
        return "answerIndex";
    }

    //咨询记录展示
    @RequestMapping("/historyIndex")
    public String historyIndex(Model model){
        List<Consul> consulinfo = consulMapper.getConsulinfo();
        model.addAttribute("conlist",consulinfo);
        return"historyIndex";
    }

    //咨询记录删除
    @RequestMapping("/deletehistory")
    public String deletehistory(@RequestParam("Record_Id") String id){
        consulMapper.deleteConsulInfo(id);
        return "redirect:/deleteIndex";
    }


//    //数据导入
//    @RequestMapping("/importdata")
//    public String importdara(){
//
//        return "importdata";
//    }

    @RequestMapping("/admin/writeArticle")
    public String write(Article article){
        article.setArticle_id(IDUtils.getId());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = formatter.format(new Date());
        article.setArticle_time(time);
        article.setArticle_compliment(0);
        article.setArticle_viewnumber(0);
        articleMapper.insetarticleInfo(article);
        System.out.println(article);
        return"redirect:/articleIndex";
    }
    @RequestMapping("/admin/towriteArticle")
    public String towrite(){

        return"admin/writeArticle";
    }

    // MarkDown博客图片上传问题
    @RequestMapping("/file/upload")
    @ResponseBody
    public JSONObject fileUpload(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file, HttpServletRequest request) throws IOException {
        //上传路径保存设置

        //获得SpringBoot当前项目的路径：System.getProperty("user.dir")
        String path = System.getProperty("user.dir")+"/upload/";

        //按照月份进行分类：
        Calendar instance = Calendar.getInstance();
        String month = (instance.get(Calendar.MONTH) + 1)+"月";
        path = path+month;

        File realPath = new File(path);
        if (!realPath.exists()){
            realPath.mkdirs();
        }

        //上传文件地址
        System.out.println("上传文件保存地址："+realPath);

        //解决文件名字问题：我们使用uuid;
        String filename = "ks-"+UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
        String originalFilename = file.getOriginalFilename();
        // KuangUtils.print(originalFilename);
        assert originalFilename != null;
        int i = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(i + 1);

        String outFilename = filename + "."+suffix;
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        file.transferTo(new File(realPath +"/"+ filename));

        //给editormd进行回调
        JSONObject res = new JSONObject();
        res.put("url","/upload/"+month+"/"+ filename);
        res.put("success", 1);
        res.put("message", "upload success!");

        return res;
    }

}
