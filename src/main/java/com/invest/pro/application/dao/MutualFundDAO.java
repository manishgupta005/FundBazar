package com.invest.pro.application.dao;

import com.invest.pro.application.model.transaction.TMutualFundDetail;

/**
 * Created with IntelliJ IDEA.
 * User: Manish G
 * Date: 10/31/2015
 * Time: 1:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class MutualFundDAO  extends EntityDAO<TMutualFundDetail, Long> {

    private static final String FIND_BY_PNR = "TMutualFundDetail.findByFolioNumber";
}
