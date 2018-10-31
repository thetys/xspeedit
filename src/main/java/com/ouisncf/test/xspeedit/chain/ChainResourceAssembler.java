package com.ouisncf.test.xspeedit.chain;

import com.ouisncf.test.xspeedit.chain.entity.Chain;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Convert a Chain into a Resource
 */
@Component
public class ChainResourceAssembler implements ResourceAssembler<Chain, Resource<Chain>> {
    /**
     * Converts the given Chain into a Resource
     * with self link and a link to /chains method
     *
     * @param chain Chain to convert
     * @return Resource of the chain
     */
    @Override
    public Resource<Chain> toResource(Chain chain) {
        return new Resource<>(
                chain,
                linkTo(methodOn(ChainController.class).one(chain.getId())).withSelfRel(),
                linkTo(methodOn(ChainController.class).all()).withRel("chains")
        );
    }
}
