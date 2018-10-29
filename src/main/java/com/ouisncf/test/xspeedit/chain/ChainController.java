package com.ouisncf.test.xspeedit.chain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(
        value = "chains",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class ChainController {

    private final ChainService chainService;
    private final ChainResourceAssembler assembler;

    public ChainController(
            @Autowired ChainService chainService,
            @Autowired ChainResourceAssembler assembler
    ) {
        this.chainService = chainService;
        this.assembler = assembler;
    }

    @GetMapping
    public Resources<Resource<Chain>> all() {
        List<Resource<Chain>> chains = chainService.getAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(
                chains,
                linkTo(methodOn(ChainController.class).all()).withSelfRel()
        );
    }

    @GetMapping("{id}")
    public Resource<Chain> one(@PathVariable Long id) {
        return assembler.toResource(chainService.getOne(id));

    }

    @PostMapping
    public Chain createChain(@RequestBody ChainRequestModel chainRequestModel) {
        return chainService.createChain(chainRequestModel.getArticles());
    }
}
