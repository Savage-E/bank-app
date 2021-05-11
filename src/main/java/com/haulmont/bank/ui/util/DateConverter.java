package com.haulmont.bank.ui.util;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.time.LocalDate;

/**
 * Created by Vlad Kotov
 * Date: 10.май.2021
 * Time:  18:28
 * Project: bank-app
 * Description:
 */


public class DateConverter implements Converter<String, LocalDate> {
  @Override
  public Result<LocalDate> convertToModel(String fieldValue, ValueContext context) {
    try {
      return Result.ok(LocalDate.parse(fieldValue));
    } catch (Exception e) {

      return Result.error("Please enter a  date in right format like YYYY-MM-DD");
    }
  }

  @Override
  public String convertToPresentation(LocalDate date, ValueContext context) {

    return date.toString();
  }
}

