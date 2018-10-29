package com.ouisncf.test.xspeedit.chain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(
        value = "chains",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
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
