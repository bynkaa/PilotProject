package com.qsoft.pilotproject.model;


import android.net.Uri;
import android.content.ContentResolver;
import android.provider.BaseColumns;

public final class CommentCCContract
    implements BaseColumns {
  public static final String AUTHORITY = "com.qsoft.pilotproject";

  public static final String CONTENT_URI_PATH = "comments";

  public static final String MIMETYPE_TYPE = "comments";
  public static final String MIMETYPE_NAME = "com.qsoft.pilotproject.provider";

  public static final int CONTENT_URI_PATTERN_MANY = 1;
  public static final int CONTENT_URI_PATTERN_ONE = 2;

  public static final Uri CONTENT_URI = new Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT).authority(AUTHORITY).appendPath(CONTENT_URI_PATH).build();

  private CommentCCContract() {
  }


  public static final String SOUNDID = "soundId";
  public static final String USERID = "userId";
  public static final String COMMENT = "comment";
  public static final String CREATEDAT = "createdAt";
  public static final String UPDATEDAT = "updatedAt";
  public static final String USERNAME = "userName";
  public static final String DISPLAYNAME = "displayName";
  public static final String AVATAR = "avatar";
}
