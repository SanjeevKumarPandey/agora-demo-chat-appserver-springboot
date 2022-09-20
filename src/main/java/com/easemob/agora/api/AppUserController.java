package com.easemob.agora.api;

import com.easemob.agora.model.AppUser;
import com.easemob.agora.model.ResCode;
import com.easemob.agora.model.ResponseParam;
import com.easemob.agora.model.TokenInfo;
import com.easemob.agora.service.AppUserService;
import com.easemob.agora.service.AgoraChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@RestController
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Autowired
    private AgoraChatService agoraChatService;

    public AppUserController(AgoraChatService agoraChatService) {
        this.agoraChatService = agoraChatService;
    }

    public AppUserController(){}

    @PostMapping("/app/user/register")
    public ResponseEntity register(@RequestBody @Valid AppUser appUser) {
        appUserService.registerUser(appUser);

        ResponseParam responseParam = new ResponseParam();
        responseParam.setCode(ResCode.RES_OK.getCode());
        return ResponseEntity.ok(responseParam);
    }

    @PostMapping("/app/user/login")
    public ResponseEntity login(@RequestBody @Valid AppUser appUser) {
        TokenInfo token = appUserService.loginUser(appUser);

        ResponseParam responseParam = new ResponseParam();
        responseParam.setAccessToken(token.getToken());
        responseParam.setExpireTimestamp(token.getExpireTimestamp());
        responseParam.setChatUserName(token.getChatUserName());
        responseParam.setAgoraUid(token.getAgoraUid());
        return ResponseEntity.ok(responseParam);
    }

    // Get the Agora Chat user token
    @GetMapping("/chat/user/{chatUserName}/token")
    public ResponseEntity getChatToken(@PathVariable String chatUserName) {
        ResponseParam responseParam = new ResponseParam();
        responseParam.setToken(agoraChatService.getChatToken(chatUserName));
        responseParam.setCode(ResCode.RES_OK.getCode());
        return ResponseEntity.ok(responseParam);
    }
//
//    //https://docs.agora.io/en/agora-chat/agora_chat_restful_chatroom_superadmin?platform=RESTful#http-request
//    @PostMapping("/app/chatrooms/super_admin")
//    public ResponseEntity super_admin(@RequestBody @Valid AppUser superadmin) {
//    /**
//     * TODO: Add Logic for super_admin
//     */
//    }
//
//    @DeleteMapping("/chatrooms/super_admin/{superAdmin}")
//    public ResponseEntity deleteAdmin(@RequestBody @Valid AppUser appUser @PathVariable String chatUserName) {
//        /**
//         * TODO
//         */
//    }
}
