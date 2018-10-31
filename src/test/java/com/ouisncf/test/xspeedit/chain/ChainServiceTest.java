package com.ouisncf.test.xspeedit.chain;

import com.ouisncf.test.xspeedit.article.Article;
import com.ouisncf.test.xspeedit.chain.entity.Chain;
import com.ouisncf.test.xspeedit.chain.exception.ChainNotFoundException;
import com.ouisncf.test.xspeedit.chain.exception.NoArticleFoundException;
import com.ouisncf.test.xspeedit.chain.service.ChainService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@Import(ChainService.class)
@DataJpaTest
public class ChainServiceTest {

    @Autowired
    private ChainService chainService;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void shouldCreateAChainFromAStringOfNumbers() {
        Chain chain = chainService.createChain("123456789");
        Assert.assertEquals(9, chain.getArticles().size());
        Assert.assertArrayEquals(
                new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9},
                chain.getArticles()
                        .stream()
                        .map(Article::getSize)
                        .<Integer>toArray(Integer[]::new)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRefuseAnEmptyChain() {
        chainService.createChain("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRefuseAnyNonNumberCharacter() {
        chainService.createChain("456///789");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRefuseAnyZero() {
        chainService.createChain("456089");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRefuseSpaces() {
        chainService.createChain(" 456");
    }

    @Test
    public void shouldFetchAll() {
        Chain chainOne = entityManager.persist(
                new Chain(
                        "17",
                        Arrays.asList(
                                new Article(1),
                                new Article(7)
                        )
                )
        );
        Chain chainTwo = entityManager.persist(
                new Chain(
                        "25",
                        Arrays.asList(
                                new Article(2),
                                new Article(5)
                        )
                )
        );
        List<Chain> allChains = chainService.getAll();
        Assert.assertEquals(2, allChains.size());
        Assert.assertArrayEquals(
                new Chain[]{chainOne, chainTwo},
                allChains.toArray(new Chain[0])
        );
    }

    @Test
    public void shouldFetchAnEmptyList() {
        List<Chain> allChains = chainService.getAll();
        Assert.assertEquals(0, allChains.size());
    }

    @Test
    public void shouldFetchASpecificChain() {
        Chain chainOne = entityManager.persist(
                new Chain(
                        "17",
                        Arrays.asList(
                                new Article(1),
                                new Article(7)
                        )
                )
        );
        Chain chainTwo = entityManager.persist(
                new Chain(
                        "25",
                        Arrays.asList(
                                new Article(2),
                                new Article(5)
                        )
                )
        );
        Chain fetchedChain = chainService.getOne(chainTwo.getId());
        Assert.assertEquals(chainTwo, fetchedChain);
    }

    @Test(expected = ChainNotFoundException.class)
    public void shouldNotFoundAnUnknownChain() {
        Chain fetchedChain = chainService.getOne(1L);
    }

    @Test
    public void shouldPackArticles() {
        Chain chain =
                new Chain(
                        "163841689525773",
                        IntStream.of(1,6,3,8,4,1,6,8,9,5,2,5,7,7,3)
                        .mapToObj(Article::new)
                        .collect(Collectors.toList())
                );
        chain = chainService.packChain(chain);
        Assert.assertEquals("91/82/81/73/73/64/6/55", chain.getPackaging());
    }

    @Test(expected = NoArticleFoundException.class)
    public void shouldRefuseToPackAnEmptyChain() {
        Chain chain = chainService.packChain(new Chain());
        Assert.assertEquals("91/82/81/73/73/64/6/55", chain.getPackaging());
    }
}