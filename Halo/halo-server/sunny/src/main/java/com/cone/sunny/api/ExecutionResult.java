package com.cone.sunny.api;

import java.util.HashMap;
import java.util.Map;

public class ExecutionResult {
	
	public Map<String, Object> Result(int code) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", code);
		return resultMap;
	}

}
