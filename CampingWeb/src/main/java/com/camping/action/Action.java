package com.camping.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.action.ActionForward;

public interface Action {
	public ActionForward execute(HttpServletRequest request, 
			HttpServletResponse response) throws IOException;
}
