package com.andon.securitydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ConnectController {

    @GetMapping(value = "/connect")
    @ResponseBody
    public String connect(){
        System.out.println("Connect success!!");
        return "<h1>Connect success!!</h1>";
    }

    @PostMapping(value = "/index")
    public String index(){
        System.out.println("index_POST");
        return "index";
    }

    @GetMapping(value = "/login")
    public String login(){
        System.out.println("login_POST");
        return "login";
    }

    @PostMapping(value = "/error")
    public String error(){
        System.out.println("error_POST");
        return "error";
    }

    @GetMapping(value = "/bye")
    public String bye(){
        System.out.println("bye_GET");
        return "bye";
    }

    /**
     * session失效处理
     */
    /*@GetMapping(value = "/session/invalid")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public void sessionInvalid(){
        https://blog.csdn.net/Dongguabai/article/details/81053660
    }*/
}
