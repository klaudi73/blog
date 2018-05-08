package com.klaudi73.blog.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "article", length = 10000)
    private String article;

    @Column(name = "title")
    private String title;

    @Column(name = "author_id")
    private Long authorId;

    public ArticleEntity() {
    }

    public ArticleEntity(String article, String title, Long authorId) {
        this.article = article;
        this.title = title;
        this.authorId = authorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleEntity)) return false;
        ArticleEntity that = (ArticleEntity) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getArticle(), that.getArticle()) &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getAuthorId(), that.getAuthorId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getArticle(), getTitle(), getAuthorId());
    }

    @Override
    public String toString() {
        return "ArticleEntity{" +
                "id=" + id +
                ", article='" + article + '\'' +
                ", title='" + title + '\'' +
                ", authorId=" + authorId +
                '}';
    }
}
