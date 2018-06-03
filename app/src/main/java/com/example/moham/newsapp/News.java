package com.example.moham.newsapp;

public class News {
    private String title;
    private String webUrl;
    private String sectionName;
    private String author;
    private String publicationDate;
    private boolean hasAuthor;
    private boolean hasPublicationDate;

    public News(String title, String webUrl, String sectionName, String author, String publicationDate) {
        this.title = title;
        this.webUrl = webUrl;
        this.sectionName = sectionName;
        this.author = author;
        this.publicationDate = publicationDate;
        this.hasAuthor = !author.equals("");
        this.hasPublicationDate = !publicationDate.equals("");
    }

    public boolean isHasPublicationDate() {
        return hasPublicationDate;
    }

    public boolean isHasAuthor() {
        return hasAuthor;
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

    public String getAuthor() {
        return author;
    }

    public String getPublicationDate() {
        return publicationDate;
    }
}
