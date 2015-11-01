package com.invest.pro.application.service.mutualfund.impl;


import com.google.inject.Inject;
import com.invest.pro.application.dao.MutualFundDAO;
import com.invest.pro.application.exception.ApplicationException;
import com.invest.pro.application.model.transaction.TMutualFundDetail;
import com.invest.pro.application.service.mutualfund.MutualFundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PersistenceException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: Manish G
 * Date: 10/15/2015
 * Time: 8:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class MutualFundServiceImpl implements MutualFundService {

    private static final Logger _logger = LoggerFactory.getLogger(MutualFundServiceImpl.class);

    //@Inject
    //private Provider<MutualFundRepository> mutualFundRepository;

    @Inject
    private MutualFundDAO mutualFundDAO;

    @Override
    public Response addMutualFund(TMutualFundDetail mutualFundDetail) {
        try {
            if(mutualFundDetail == null){
                throw new ApplicationException("mutualFundDetail is null");
            }
            if (mutualFundDetail != null) {
                mutualFundDAO.save(mutualFundDetail);
               return Response.ok("Mutual Fund detail saved successfully").build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(mutualFundDetail).build();
        } catch (PersistenceException con) {
            _logger.error("Error PersistenceException occurred while adding MutualFund :" + con.getMessage());
            throw new WebApplicationException(Response.status(Response.Status.CONFLICT)
                    .entity("").build());
        } catch (Exception e) {
            _logger.error("Error occurred while adding MutualFund - " + e.getMessage());
            throw new WebApplicationException(Response.status(Response.Status.CONFLICT)
                    .entity("").build());
        }
    }


}
