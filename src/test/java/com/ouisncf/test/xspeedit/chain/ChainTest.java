package com.ouisncf.test.xspeedit.chain;

import com.ouisncf.test.xspeedit.article.Article;
import com.ouisncf.test.xspeedit.box.Box;
import com.ouisncf.test.xspeedit.chain.entity.Chain;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChainTest {

    @Test
    public void shouldReturnAPackagingAsAString() {
        Box box1 = new Box();
        box1.addArticle(new Article(4));
        box1.addArticle(new Article(5));
        box1.addArticle(new Article(2));

        Box box2 = new Box();
        box2.addArticle(new Article(6));
        box2.addArticle(new Article(1));
        box2.addArticle(new Article(9));

        Chain chain = new Chain();
        ReflectionTestUtils.setField(chain, "boxes", Stream.of(box1, box2).collect(Collectors.toList()));

        Assert.assertEquals("452/619", chain.getPackaging());
    }

    @Test
    public void shouldOmitEmptyBoxes() {
        Box box1 = new Box();
        box1.addArticle(new Article(4));
        box1.addArticle(new Article(5));
        box1.addArticle(new Article(2));

        Box box2 = new Box();
        box2.addArticle(new Article(6));
        box2.addArticle(new Article(1));
        box2.addArticle(new Article(9));

        Chain chain = new Chain();
        ReflectionTestUtils.setField(
                chain,
                "boxes",
                Stream.of(box1, new Box(), box2, new Box()).collect(Collectors.toList())
        );

        Assert.assertEquals("452/619", chain.getPackaging());
    }

    @Test
    public void shouldReturnAnEmptyStringBecauseAllBoxesAreEmpty() {
        Chain chain = new Chain();
        ReflectionTestUtils.setField(
                chain,
                "boxes",
                Stream.of(new Box(), new Box()).collect(Collectors.toList())
        );

        Assert.assertEquals("", chain.getPackaging());
    }

    @Test
    public void shouldReturnAnEmptyStringBecauseThereAreNoBoxes() {
        Chain chain = new Chain();

        Assert.assertEquals("", chain.getPackaging());
    }
}