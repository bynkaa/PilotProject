package com.qsoft.pilotproject.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * User: binhtv
 * Date: 10/31/13
 * Time: 11:07 AM
 */
public class OnlineDioContract
{
    public static final String CONTENT_AUTHORITY = "com.qsoft.onlinedio";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FEEDS = "feeds";
    public static final String PATH_COMMENTS = "comments";
    public static final String PATH_PROFILE = "profiles";

    public static class Feed implements BaseColumns
    {
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.pilotproject.feeds";
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.pilotproject.feed";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FEEDS).build();
        public static final String TABLE_NAME = "feeds";
        public static final String COLUMN_ID = "feed_sound_id";
        public static final String COLUMN_USER_ID = "feed_user_id";
        public static final String COLUMN_TITLE = "feed_title";
        public static final String COLUMN_THUMBNAIL = "feed_thumbnail";
        public static final String COLUMN_DESCRIPTION = "feed_description";
        public static final String COLUMN_SOUND_PATH = "feed_sound_path";
        public static final String COLUMN_DURATION = "feed_duration";
        public static final String COLUMN_PLAYED = "feed_played";
        public static final String COLUMN_CREATED_AT = "feed_created_at";
        public static final String COLUMN_UPDATED_AT = "feed_updated_at";
        public static final String COLUMN_LIKES = "feed_likes";
        public static final String COLUMN_VIEWED = "feed_viewed";
        public static final String COLUMN_COMMENTS = "feed_comments";
        public static final String COLUMN_USER_NAME = "feed_user_name";
        public static final String COLUMN_DISPLAY_NAME = "feed_display_name";
        public static final String COLUMN_AVATAR = "feed_avatar";

    }

    public static class Profile implements BaseColumns
    {
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.pilotproject.profiles";
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.pilotproject.profile";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PROFILE).build();
        public static final String TABLE_NAME = "profiles";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_FACEBOOK_ID = "facebook_id";
        public static final String COLUMN_USERNAME = "user_name";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_AVATAR = "avatar";
        public static final String COLUMN_COVER_IMAGE = "cover_image";
        public static final String COLUMN_DISPLAY_NAME = "display_name";
        public static final String COLUMN_FULL_NAME = "full_name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_BIRTHDAY = "birthday";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_COUNTRY_ID = "country_id";
        public static final String COLUMN_STORAGE_PLAN_ID = "storage_plan_id";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_CREATED_AT = "created_at";
        public static final String COLUMN_UPDATED_AT = "updated_at";
        public static final String COLUMN_SOUNDS = "sound";
        public static final String COLUMN_FAVORITES = "favorite";
        public static final String COLUMN_LIKES = "like";
        public static final String COLUMN_FOLLOWING = "following";
        public static final String COLUMN_AUDIENCE = "audience";

    }

    public static class Comment implements BaseColumns
    {
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.sqlite.comments";
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.sqlite.comment";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_COMMENTS).build();
        public static final String TABLE_NAME = "comments";
        public static final String COLUMN_ID = "comment_id";
        public static final String COLUMN_SOUND_ID = "comment_sound_id";
        public static final String COLUMN_USER_ID = "comment_user_id";
        public static final String COLUMN_CONTENT = "comment_content";
        public static final String COLUMN_CREATED_AT = "comment_created_at";
        public static final String COLUMN_UPDATED_AT = "comment_updated_at";
        public static final String COLUMN_USER_NAME = "comment_username";
        public static final String COLUMN_DISPLAY_NAME = "comment_display_name";
        public static final String COLUMN_AVATAR = "comment_avatar";
    }
}
