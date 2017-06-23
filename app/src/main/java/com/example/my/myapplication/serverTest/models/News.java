package com.example.my.myapplication.serverTest.models;


public class News {
    private int id;
    private String urlContent;

    private ViTri viTri;

    private String nameNews;
    private String imageView;
    private int like, comment, share;

    public News(int id, String nameNews, String urlContent, String urlImage, int like, int comment, int share,ViTri viTri) {
        this.id = id;
        this.imageView = urlImage;
        this.nameNews = nameNews;
        this.urlContent = urlContent;
        this.like = like;
        this.comment = comment;
        this.share = share;
        this.viTri = viTri;
    }

    public int getId() {
        return id;
    }

    public String getUrlContent() {
        return urlContent;
    }

    public String getNameNews() {
        return nameNews;
    }

    public void setNameNews(String nameNews) {
        this.nameNews = nameNews;
    }

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public int getShare() {
        return share;
    }

    public void setViTri(ViTri viTri) {
        this.viTri = viTri;
    }

    public ViTri getViTri() {
        return viTri;
    }
}
