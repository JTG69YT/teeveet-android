/*
 * Copyright (C) 2016 Google LLC. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fi.jesunmaailma.teeveet.utils;

import android.os.Bundle;

import java.util.ArrayList;

/**
 * Class representing local media metadata.
 */
public class MediaItem {

    private String mTitle;
    private String mSubTitle;
    private String mUrl;
    private String mImageList;

    public static final String KEY_TITLE = "name";
    public static final String KEY_SUBTITLE = "description";
    public static final String KEY_THUMBNAIL = "thumbnail";
    public static final String KEY_URL = "live_url";

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getSubTitle() {
        return mSubTitle;
    }

    public void setSubTitle(String mSubTitle) {
        this.mSubTitle = mSubTitle;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getImageList() {
        return mImageList;
    }

    public void setImageList(String mImageList) {
        this.mImageList = mImageList;
    }

    public Bundle toBundle() {
        Bundle wrapper = new Bundle();
        wrapper.putString(KEY_TITLE, mTitle);
        wrapper.putString(KEY_SUBTITLE, mSubTitle);
        wrapper.putString(KEY_URL, mUrl);
        wrapper.putString(KEY_THUMBNAIL, mImageList);
        return wrapper;
    }

    public static final MediaItem fromBundle(Bundle wrapper) {
        if (null == wrapper) {
            return null;
        }
        MediaItem media = new MediaItem();
        media.setUrl(wrapper.getString(KEY_URL));
        media.setTitle(wrapper.getString(KEY_TITLE));
        media.setSubTitle(wrapper.getString(KEY_SUBTITLE));
        media.setImageList(wrapper.getString(KEY_THUMBNAIL));
        return media;
    }

}
