package com.cone.websocket3.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
*@author Cone
*@Version 2019年7月21日 下午11:16:42
*/
@Component
public class MyPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence charSequence) {
		// TODO Auto-generated method stub
		return charSequence.toString();
	}

	@Override
	public boolean matches(CharSequence charSequence, String s) {
		// TODO Auto-generated method stub
		return s.equals(charSequence.toString());
	}

}
