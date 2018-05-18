package com.wx.auth.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.wx.auth.util.AuthUtil;

@WebServlet("/callBack")
public class CallBackServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 用户同意授权后，微信会回调我们之前发给微信的回调地址，回调地址指向当前servlet，微信会在回调时附带code参数
		String code = req.getParameter("code");
		// 使用获取到的code，再向微信发起请求获取openid和access_token
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + AuthUtil.APPID +
				"&secret=" + AuthUtil.APPSECRET + 
				"&code=" + code +
				"&grant_type=authorization_code";
		JSONObject jsonObject = AuthUtil.doGetJson(url);
		String openid = jsonObject.getString("openid");
		String token = jsonObject.getString("access_token");
		// 获取到openid和access_token之后，我们就可以调动微信接口获取用户信息
		String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token + 
				"&openid=" + openid + 
				"&lang=zh_CN";
		JSONObject userInfo = AuthUtil.doGetJson(infoUrl);
		System.out.println(userInfo);
	}
}
