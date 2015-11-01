package com.invest.pro.application.resources;


import com.invest.pro.application.model.transaction.TMutualFundDetail;
import com.invest.pro.application.service.mutualfund.MutualFundService;
import com.yammer.metrics.annotation.Timed;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: Manish G
 * Date: 10/15/2015
 * Time: 8:04 PM
 * To change this template use File | Settings | File Templates.
 */
@Path(MutualFundResource.PATH)
public class MutualFundResource implements Resource {

    public static final String PATH = ROOT_PATH + "admin/mutualFund";

    @Inject
    private MutualFundService mutualFundService;

    @POST
    @Path("/add")
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMutualFund(@Valid TMutualFundDetail mutualFundDetail) {
        return mutualFundService.addMutualFund(mutualFundDetail);
    }

}
