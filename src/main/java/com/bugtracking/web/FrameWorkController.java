package com.bugtracking.web;

import com.bugtracking.domain.entity.Authority;
import com.bugtracking.domain.entity.User;
import com.bugtracking.domain.repository.UserRepository;
import com.bugtracking.dto.UserDto;
import com.bugtracking.service.common.ShareService;
import com.bugtracking.vo.UserVO;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.ir.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import sun.security.provider.SHA;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Controller
public class FrameWorkController {
    @Autowired
    private UserRepository userDao;
    @Autowired
    private ShareService shareService;
    @Autowired
    private LocaleResolver localeResolver;

    @RequestMapping("/password")
    public String password(Map map, HttpServletRequest request) {
        User user = shareService.getUser();
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "show":
                    break;
                case "edit":
                    String mpass = request.getParameter("mpass");
                    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                    if (bCryptPasswordEncoder.matches(mpass, user.getPassword())) {
                        String newpass = request.getParameter("newpass");
                        user.setPassword(bCryptPasswordEncoder.encode(newpass));
                        userDao.saveAndFlush(user);
                        map.put("tips", "ok");
                    } else {
                        map.put("tips", "failed");
                    }
                    map.put("url", "password?action=show");
                    return "tips";
            }
        }
        map.put("username", user.getUsername());
        return "password";
    }

    @RequestMapping("/index")
    public String index(Map map, HttpServletRequest request) {
        return "index";
    }

    @RequestMapping("/switchingLanguage")
    public String switchingLanguage(Map map, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Object s = session.getAttribute("LOCALE_SESSION_ATTRIBUTE_NAME");
        localeResolver.setLocale(request, response, Locale.SIMPLIFIED_CHINESE);
        return "redirect:/login";
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
                    UserDetails userDetails = (UserDetails) pinciba;
                    map.put("user", userDetails);
                    User user = userDao.getUserByName(userDetails.getUsername());
                    map.put("realName", user.getCname());
                    if (request.getSession().getAttribute("ALLROLES") == null) {
                        // 获取所有权限
                        Set<Authority> authoritySet = user.getAuthoritySet();
                        StringBuilder authorityStr = new StringBuilder();
                        Iterator<Authority> i = authoritySet.iterator();
                        while (i.hasNext()) {
                            authorityStr.append(i.next().getEname());
                            authorityStr.append(",");
                        }
                        request.getSession().setAttribute("ALLROLES", authorityStr);
                    }
                }
                return "index";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "login";
        }
    }

    @RequestMapping("/addUser")
    public String addUser(Map map) {
        map.put("authoritys", shareService.getAuthoritys());
        return "addUser";
    }

    @RequestMapping("/ajaxAddUser")
    @ResponseBody
    public String ajaxAddUser(@RequestBody String json) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            UserDto userDto = objectMapper.readValue(json, UserDto.class);

            User u = userDao.getUserByName(userDto.getUsername());
            if (u == null) {
                User user = new User();
                user.setUsername(userDto.getUsername());
                user.setCname(userDto.getCname());
                user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
                List list = userDto.getAuthoritys();
                Set<Authority> set = new HashSet<>();
                for (int i = 0; i < list.size(); i++) {
                    Authority a = new Authority();
                    a.setId(Integer.parseInt(list.get(i).toString()));
                    set.add(a);
                }
                user.setAuthoritySet(set);
                userDao.saveAndFlush(user);
            } else {
                return "repeat";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }
}
