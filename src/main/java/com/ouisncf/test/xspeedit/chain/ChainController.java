package com.ouisncf.test.xspeedit.chain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(
        value = "chains",
        produces = MediaTypes.HAL_JSON_UTF8_VALUE
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
    public ResponseEntity<Resource<Chain>> createChain(@RequestBody ChainRequestModel chainRequestModel) throws URISyntaxException {
        Resource<Chain> resource = assembler.toResource(
                chainService.createChain(chainRequestModel.getArticles())
        );
        //TODO: personnaliser le message d'erreur 400
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource)
                ;
    }
}
