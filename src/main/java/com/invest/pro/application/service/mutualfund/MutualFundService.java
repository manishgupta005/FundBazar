package com.invest.pro.application.service.mutualfund;

import com.invest.pro.application.model.transaction.TMutualFundDetail;

import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: Manish G
 * Date: 10/15/2015
 * Time: 8:26 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MutualFundService {

    Response addMutualFund(TMutualFundDetail mutualFundDetail);
}
