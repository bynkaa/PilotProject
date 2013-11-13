package com.qsoft.pilotproject.common.annotation;

import android.view.View;
import com.qsoft.pilotproject.common.ICommand;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: Le
 * Date: 10/21/13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Command
{
    Class<? extends ICommand> value();

    Class<?> event() default View.OnClickListener.class;

    String[] parameters() default {};
}
