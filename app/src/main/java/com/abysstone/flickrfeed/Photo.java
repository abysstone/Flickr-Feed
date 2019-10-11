package com.abysstone.flickrfeed;
//package com.abysstone.flickrfeed;

public class Photo {
    private String mTitle;
    private String mAuthor;
    private String mAutherId;
    private String mLink;
    private String mTag;
    private String mImage;

    public Photo(String mTitle, String mAuthor, String mAutherId, String mLink, String mTag, String mImage) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mAutherId = mAutherId;
        this.mLink = mLink;
        this.mTag = mTag;
        this.mImage = mImage;
    }

    String getmTitle() {
        return mTitle;
    }

    String getmAuthor() {
        return mAuthor;
    }

    String getmAutherId() {
        return mAutherId;
    }

    String getmLink() {
        return mLink;
    }

    String getmTag() {
        return mTag;
    }

    String getmImage() {
        return mImage;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "mTitle='" + mTitle + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mAutherId='" + mAutherId + '\'' +
                ", mLink='" + mLink + '\'' +
                ", mTag='" + mTag + '\'' +
                ", mImage='" + mImage + '\'' +
                '}';
    }
}

