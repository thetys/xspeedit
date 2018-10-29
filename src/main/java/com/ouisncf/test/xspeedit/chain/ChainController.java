package com.ouisncf.test.xspeedit.chain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("chains")
public class ChainController {

    private ChainService chainService;

    public ChainController(@Autowired ChainService chainService) {
        this.chainService = chainService;
    }

    @GetMapping
    public List<Chain> getAllChains() {
        return chainService.getAll();
    }

    @PostMapping
    public Chain createChain(@RequestBody ChainRequestModel chainRequestModel) {
        return chainService.createChain(chainRequestModel.getArticles());
    }
}
