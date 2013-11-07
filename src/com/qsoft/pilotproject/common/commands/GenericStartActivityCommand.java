package com.qsoft.pilotproject.common.commands;

import android.app.Activity;
import android.content.Intent;
import com.qsoft.pilotproject.common.Constants;
import com.qsoft.pilotproject.common.ICommandExecutable;
import com.qsoft.pilotproject.common.SuperAnnotationActivity;
import com.qsoft.pilotproject.common.utils.LogUtils;
import com.qsoft.pilotproject.common.utils.UniqueRequestCodeGenerator;

import java.io.Serializable;

/**
 * User: Le
 * Date: 10/21/13
 */
public class GenericStartActivityCommand implements ICommandExecutable
{
// ------------------------------ FIELDS ------------------------------

    private Class openingActivityClass;
    private Activity activityExchangeEventManageable;
    private int requestCode;
    private Serializable model;

    public GenericStartActivityCommand(Activity activity, Class<?> openingActivityClass, int requestCode)
    {
        this.activityExchangeEventManageable = activity;
        this.openingActivityClass = openingActivityClass;
        this.requestCode = requestCode;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public void setActivityExchangeEventManageable(SuperAnnotationActivity activityExchangeEventManageable)
    {
        this.activityExchangeEventManageable = activityExchangeEventManageable;
    }

    public void setModel(Serializable model)
    {
        this.model = model;
    }

    public void setOpeningActivityClass(Class openingActivityClass)
    {
        this.openingActivityClass = openingActivityClass;
    }

    public void setRequestCode(int requestCode)
    {
        this.requestCode = requestCode;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ICommandExecutable ---------------------

    @Override
    public void execute() throws Exception
    {
        LogUtils.debugLog(this, "GenericStartActivityCommand execute()");
        Intent intent = new Intent(activityExchangeEventManageable, openingActivityClass);
        intent.putExtra(Constants.SOURCE_MODEL, model);
        requestCode = UniqueRequestCodeGenerator.getNext();
        intent.putExtra(Constants.REQUEST_CODE, requestCode);
        overrideExtra(intent);
        activityExchangeEventManageable.startActivityForResult(intent, requestCode);
    }

    public void overrideExtra(Intent intent)
    {

    }
}
