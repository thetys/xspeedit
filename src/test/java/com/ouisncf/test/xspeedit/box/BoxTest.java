package com.ouisncf.test.xspeedit.box;

import com.ouisncf.test.xspeedit.article.Article;
import org.junit.Assert;
import org.junit.Test;

public class BoxTest {

    @Test
    public void shouldReturnArticleSizesAsAString() {
        Box box = new Box();
        box.addArticle(new Article(3));
        box.addArticle(new Article(7));
        box.addArticle(new Article(5));

        String articlesAsString = box.getArticlesAsString();
        Assert.assertEquals("375", articlesAsString);
    }

    @Test
    public void shouldReturnAnEmptyString() {
        Box box = new Box();

        String articlesAsString = box.getArticlesAsString();
        Assert.assertEquals("", articlesAsString);
    }
}