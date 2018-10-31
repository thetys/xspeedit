package com.ouisncf.test.xspeedit.chain;

import com.ouisncf.test.xspeedit.chain.entity.Chain;
import com.ouisncf.test.xspeedit.chain.exception.IllegalChainArgumentException;
import com.ouisncf.test.xspeedit.chain.model.ChainRequestModel;
import com.ouisncf.test.xspeedit.chain.service.ChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
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

    /**
     * Return all existing chains with hypermedia links
     *
     * @return Resources of a list of chain
     */
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

    /**
     * Return a specific chain with hypermedia links
     *
     * @param id Long Id of the required chain
     * @return Resource of a chain
     */
    @GetMapping("{id}")
    public Resource<Chain> one(@PathVariable Long id) {
        return assembler.toResource(chainService.getOne(id));

    }

    /**
     * Create a new chain from a string containing a list of articles
     *
     * @param chainRequestModel Model of the expected body of the request
     * @return ResponseEntity containing the newly created chain as a Resource
     * @throws URISyntaxException Thrown by the creation to the chain link in the header of the response
     */
    @PostMapping
    public ResponseEntity<Resource<Chain>> createChain(@RequestBody ChainRequestModel chainRequestModel) throws URISyntaxException {
        Chain chain = chainService.createChain(chainRequestModel.getArticles());
        chain = chainService.packChain(chain);
        Resource<Chain> resource = assembler.toResource(chain);
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource)
                ;
    }
}
