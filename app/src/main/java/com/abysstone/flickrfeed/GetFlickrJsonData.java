package com.abysstone.flickrfeed;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class GetFlickrJsonData implements GetRawData.onDownloadComplete {
    private static final String TAG = "GetFlickrJsonData";

    private List<Photo> mPhotoList = null;
    private String mBaseURL;
    private String mLanguage;
    private Boolean mMatchAll;

    private final OnDataAvailable mCallBack;

    interface OnDataAvailable{
        void onDataAvailable(List<Photo> data, DownloadStatus status);
    }

    public GetFlickrJsonData(String mBaseURL, String mLanguage, Boolean mMatchAll, OnDataAvailable mCallBack) {
        Log.d(TAG, "GetFlickrJsonData: called ");
        this.mBaseURL = mBaseURL;
        this.mLanguage = mLanguage;
        this.mMatchAll = mMatchAll;
        this.mCallBack = mCallBack;
    }

    void executeOnSameThread(String searchCriteria){
        Log.d(TAG, "executeOnSameThread: starts");
        String destinationUri = createUri(searchCriteria, mLanguage, mMatchAll);

        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(destinationUri);
        Log.d(TAG, "executeOnSameThread: ends");
    }

    private String createUri(String searchCriteria, String lang, boolean matchAll){
        Log.d(TAG, "createUri: starts");

        return Uri.parse(mBaseURL).buildUpon()
                .appendQueryParameter("tags", searchCriteria)
                .appendQueryParameter("tagmode",matchAll ? "ALL" : "ANY")
                .appendQueryParameter("lang",lang)
                .appendQueryParameter("format","json")
                .appendQueryParameter("nojsoncallback","1")
                .build().toString();
    }

    @Override
    public void OnDownloadComplete(String s, DownloadStatus mDownloadStatus) {
        Log.d(TAG, "OnDownloadComplete: starts. Status : "+ mDownloadStatus);
        if (mDownloadStatus == DownloadStatus.OK){
            mPhotoList = new ArrayList<>();
            try{
                JSONObject jsonData = new JSONObject(s);
                JSONArray itemArray = jsonData.getJSONArray("items");

                for (int i = 0; i<itemArray.length(); i++){
                    JSONObject jsonPhoto = itemArray.getJSONObject(i);
                    String title = jsonPhoto.getString("title");
                    String author = jsonPhoto.getString("author");
                    String authorId = jsonPhoto.getString("author_id");
                    String tags = jsonPhoto.getString("tags");

                    JSONObject jsonMedia = jsonPhoto.getJSONObject("media");
                    String photoUrl = jsonMedia.getString("m");

                    String link = photoUrl.replaceFirst("_m.","_b.");

                    Photo photoObject = new Photo(title, author, authorId, link, tags, photoUrl);
                    mPhotoList.add(photoObject);

                    Log.d(TAG, "OnDownloadComplete: Complete " + photoObject.toString());
                }
            }catch (JSONException json){
                json.printStackTrace();
                Log.e(TAG, "OnDownloadComplete: Error processing json data "+ json.getMessage());
                mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
        if (mCallBack != null){
            //now inform the caller that processing is done - possibly running null if there was an error
            mCallBack.onDataAvailable(mPhotoList, mDownloadStatus);
        }
        Log.d(TAG, "OnDownloadComplete: Ends!");
    }
}
