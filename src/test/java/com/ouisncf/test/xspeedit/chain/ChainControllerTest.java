package com.ouisncf.test.xspeedit.chain;

import com.ouisncf.test.xspeedit.article.Article;
import com.ouisncf.test.xspeedit.chain.entity.Chain;
import com.ouisncf.test.xspeedit.chain.exception.ChainNotFoundException;
import com.ouisncf.test.xspeedit.chain.exception.IllegalChainArgumentException;
import com.ouisncf.test.xspeedit.chain.service.ChainService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ChainController.class)
@Import(ChainResourceAssembler.class)
public class ChainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChainService chainService;

    @Autowired
    private ChainResourceAssembler chainResourceAssembler;

    public ChainControllerTest() {

    }

    @Test
    public void shouldReturnAnEmptyListWithSelfLink() throws Exception {
        this.mockMvc
                .perform(
                        get("/chains")
                        .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_embedded").doesNotExist())
        ;
    }

    @Test
    public void shouldReturnANonEmptyListWithSelfLink() throws Exception {
        List<Article> articleList = Lists.newArrayList(new Article(1));
        List<Chain> chainList = Lists.newArrayList(new Chain("1", articleList));
        when(chainService.getAll()).thenReturn(chainList);
        this.mockMvc
                .perform(
                        get("/chains")
                                .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_embedded.chainList").isNotEmpty())
        ;
    }

    @Test
    public void shouldReturnA404ErrorWithAnErrorMessage() throws Exception {
        when(chainService.getOne(anyLong())).thenThrow(new ChainNotFoundException(anyLong()));
        this.mockMvc
                .perform(
                        get("/chains/1")
                                .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8))
                .andExpect(jsonPath("error").exists())
        ;
    }

    @Test
    public void shouldReturnAChainWithLinks() throws Exception {
        List<Article> articleList = Lists.newArrayList(new Article(1));
        Chain chain = new Chain("1", articleList);
        when(chainService.getOne(anyLong())).thenReturn(chain);
        this.mockMvc
                .perform(
                        get("/chains/1")
                                .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.chains").exists())
                .andExpect(jsonPath("initialization").exists())
                .andExpect(jsonPath("articles").exists())
                .andExpect(jsonPath("articles", hasSize(1)))
        ;
    }

    @Test
    public void shouldHandleAnEmptyBodyPostRequestWithA400Error() throws Exception {
        this.mockMvc
                .perform(
                        post("/chains")
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8))
                .andExpect(jsonPath("error").exists())
                .andExpect(jsonPath("usage").exists())
        ;
    }

    @Test
    public void shouldHandleAWrongBodyPostRequestWithA400Error() throws Exception {
        when(chainService.createChain(any())).thenThrow(new IllegalChainArgumentException(""));
        this.mockMvc
                .perform(
                        post("/chains")
                                .content("{\"toto\": false}")
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8))
                .andExpect(jsonPath("error").exists())
                .andExpect(jsonPath("usage").exists())
        ;
    }

    @Test
    public void shouldCreateAndPackAChain() throws Exception {
        Chain chain = new Chain();
        ReflectionTestUtils.setField(chain, "id", 1L);
        when(chainService.createChain(any())).thenReturn(chain);
        when(chainService.packChain(any())).thenReturn(chain);
        this.mockMvc
                .perform(
                        post("/chains")
                                .content("{\"articles\": \"46738291\"}")
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.chains").exists())
        ;
    }

}