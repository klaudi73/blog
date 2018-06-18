package com.klaudi73.blog.services;

import com.klaudi73.blog.models.ArticleEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    public static void changeArticlesToShort(Iterable<ArticleEntity> articlesCol, List<ArticleEntity> articlesCol2) {
        for (ArticleEntity article: articlesCol) {
            ArticleEntity article2 = article;
            String strArticle = article.getArticle();
            String strPoczatek = "";
            int j;
            if (strArticle.length() > 50) {
                j = 50;
            } else {
                j = strArticle.length();
            }
            for (int i = 0; i < j ; i++) {
                strPoczatek += strArticle.charAt(i);
            }
            strPoczatek += "...";
            article2.setArticle(strPoczatek);
            articlesCol2.add(article2);
        }
    }
}
