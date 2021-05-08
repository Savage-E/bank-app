package com.haulmont.bank.security;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Vlad Kotov
 * Date: 08.май.2021
 * Time:  19:35
 * Project: bank-app
 * Description:
 */

class CustomRequestCache extends HttpSessionRequestCache {

  @Override
  public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
    if (!SecurityUtils.isFrameworkInternalRequest(request)) {
      super.saveRequest(request, response);
    }
  }

}