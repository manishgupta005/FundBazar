package com.invest.pro.application.repository;

import com.invest.pro.application.model.transaction.TMutualFundDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 * User: Manish G
 * Date: 10/15/2015
 * Time: 8:51 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MutualFundRepository extends JpaRepository<TMutualFundDetail, Long> {

    TMutualFundDetail addMutualFund(TMutualFundDetail mutualFundDetail);

}
