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

package fi.jesunmaailma.teeveet.browser;

import fi.jesunmaailma.teeveet.utils.MediaItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoProvider {

    private static final String TAG = "VideoProvider";

    private static final String TAG_NAME = "name";
    private static final String TAG_SUBTITLE = "description";
    private static final String TAG_THUMB = "thumbnail";
    private static final String TAG_LIVE_URL = "live_url";

    private static List<MediaItem> mediaList;

    protected JSONArray parseUrl(String urlString) {
        InputStream is = null;
        try {
            java.net.URL url = new java.net.URL(urlString);
            URLConnection urlConnection = url.openConnection();
            is = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream(), "iso-8859-1"), 1024);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String json = sb.toString();
            return new JSONArray(json);
        } catch (Exception e) {
            Log.d(TAG, "Failed to parse the json for media list", e);
            return null;
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    public static List<MediaItem> buildMedia(String url) throws JSONException {

        if (null != mediaList) {
            return mediaList;
        }
        mediaList = new ArrayList<>();
        JSONArray jsonArray = new VideoProvider().parseUrl(url);
        if (null != jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject channel = jsonArray.getJSONObject(i);
                String subTitle = channel.getString(TAG_SUBTITLE);
                String liveUrl = channel.getString(TAG_LIVE_URL);
                String imageUrl = channel.getString(TAG_THUMB);
                String title = channel.getString(TAG_NAME);
                mediaList.add(buildMediaInfo(title, subTitle, liveUrl, imageUrl));
            }
        }
        return mediaList;
    }

    private static MediaItem buildMediaInfo(String title, String subTitle, String url, String imgUrl) {
        MediaItem media = new MediaItem();
        media.setUrl(url);
        media.setTitle(title);
        media.setSubTitle(subTitle);
        media.setImageList(imgUrl);

        return media;
    }

}


