package com.example.thirdparty.controller;


import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.thirdparty.service.OauthService;
import com.example.thirdparty.utils.HttpClientUtils;

/**
 * 
 * @author Cone
 */
@Controller
public class LoginController {
	
	@Autowired
	private OauthService oauthService;
	
	private static String GITHUB_CLIENT_ID = "40d07d4e6332fc684912";
	private static String GITHUB_CLIENT_SECRET = "4370c41d4929bf5798e45edcd88fc3c4a0136d02";
	private static String GITHUB_REDIRECT_URL = "http://127.0.0.1:8080/githubCallback";
	
	@RequestMapping("/githubLogin")
	public void githubLogin(HttpServletResponse response) throws Exception {
		//Github认证服务器地址
		String url = "https://github.com/login/oauth/authorize";
		//生成并保存state，忽略该参数有可能导致CSRF攻击
		String state = oauthService.saveState();
		String param = "response_type=code&" + "client_id=" + GITHUB_CLIENT_ID + "&state=" + state
                + "&redirect_uri=" + GITHUB_REDIRECT_URL;
		//请求Github认证服务器
		response.sendRedirect(url + "?" + param);
	}
	
	@RequestMapping("/githubCallback")
	public void githubCallback(String code, String state, HttpServletResponse response) throws Exception {
		//首先校验state是否合法
		if (!oauthService.checkState(state)) {
			throw new Exception("State验证失败.");
		}
		//向GitHub认证服务器申请令牌
		String url = "https://github.com/login/oauth/access_token";
		
		String param = "grant_type=authorization_code&code=" + code + "&redirect_uri=" +
                GITHUB_REDIRECT_URL + "&client_id=" + GITHUB_CLIENT_ID + "&client_secret=" + GITHUB_CLIENT_SECRET;
		//申请令牌，注意此处为post请求
		String result = HttpClientUtils.sendPostRequest(url, param);
		/*
         * result示例：
         * 失败：error=incorrect_client_credentials&error_description=The+client_id+and%2For+client_secret+passed+are+incorrect.&
         * error_uri=https%3A%2F%2Fdeveloper.github.com%2Fapps%2Fmanaging-oauth-apps%2Ftroubleshooting-oauth-app-access-token-request-errors%2F%23incorrect-client-credentials
         * 成功：access_token=7c76186067e20d6309654c2bcc1545e41bac9c61&scope=&token_type=bearer
         */
		Map<String, String> resultMap = HttpClientUtils.params2Map(result);
		if(resultMap.containsKey("error")) {
            throw  new Exception(resultMap.get("error_description"));
        }
		if(!resultMap.containsKey("access_token")) {
            throw  new Exception("获取token失败");
        }
		String accessToken = resultMap.get("access_token");
		String tokenType = resultMap.get("token_type");
		String userUrl = "https://api.github.com/user";
		String userParam = "access_token=" + accessToken + "&token_type=" + tokenType;
		//申请资源
		String userResult = HttpClientUtils.sendGetRequest(userUrl, userParam);
		// 4、输出用户信息
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(userResult);
	}
}
