package com.bugtracking.web;

import com.bugtracking.domain.entity.User;
import com.bugtracking.domain.repository.UserRepository;
import com.bugtracking.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.security.provider.SHA;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class FrameWorkController {
    @Autowired
    private UserRepository userDao;
    @Autowired
    private ShareService shareService;

    @RequestMapping("/password")
    public String password(Map map, HttpServletRequest request) {

        User user = shareService.getUser();
        String action = request.getParameter("action");

        switch (action){
            case "show":
                break;
            case "edit":
                String mpass = request.getParameter("mpass");
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                if(bCryptPasswordEncoder.matches(mpass,user.getPassword())){
                    String newpass = request.getParameter("newpass");
                    user.setPassword(bCryptPasswordEncoder.encode(newpass));
                    userDao.saveAndFlush(user);
                    map.put("tips","ok");
                }
                else {
                    map.put("tips","failed");
                }
                map.put("url","password?action=show");
                return "tips";
        }
        map.put("username",user.getUsername());
        return "password";
    }

    @RequestMapping("/index")
    public String index(Map map, HttpServletRequest request) {
        return "index";
    }

    @RequestMapping("/login")
    public String login(Map map, HttpServletRequest request) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // 获取登录用户信息
            //判断其左边对象是否为其右边类的实例，返回boolean类型的数据     匿名身份验证
            if (auth instanceof AnonymousAuthenticationToken) {
                return "login";
            } else {
                // 登陆成功,获取登陆者详细信息
                Object pinciba = auth.getPrincipal();
                if (pinciba instanceof UserDetails) {
                    UserDetails userDetails = (UserDetails)pinciba;
                    map.put("user",userDetails);
                    User user = userDao.getUserByName(userDetails.getUsername());
                    map.put("realName",user.getCname());
                    request.getSession().setAttribute("REALNAME",user.getCname());
                }


                return "index";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "login";
        }
    }

}
