package com.example.moham.newsapp;

public class News {
    private String title;
    private String webUrl;
    private String sectionName;
    private String[] authors;
    private String publicationDate;

    public News(String title, String webUrl, String sectionName, String[] authors, String publicationDate) {
        this.title = title;
        this.webUrl = webUrl;
        this.sectionName = sectionName;
        this.authors = authors;
        this.publicationDate = publicationDate;
    }

    public String getTitle() {
        return title;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String[] getAuthors() {
        return authors;
    }

    public String getPublicationDate() {
        return publicationDate;
    }
}
