package com.ouisncf.test.xspeedit.chain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("chains")
public class ChainController {

    private ChainService chainService;

    public ChainController(@Autowired ChainService chainService) {
        this.chainService = chainService;
    }

    @PostMapping
    public Chain createChain(@RequestBody ChainRequestModel chainRequestModel) {
        return chainService.createChain(chainRequestModel.getArticles());
    }
}
