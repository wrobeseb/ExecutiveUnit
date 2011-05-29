package pwr.tin.tip.sw.pd.eu.db.service.impl;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pwr.tin.tip.sw.pd.eu.db.dao.IUnitDao;
import pwr.tin.tip.sw.pd.eu.db.model.Unit;
import pwr.tin.tip.sw.pd.eu.db.model.enums.UnitType;
import pwr.tin.tip.sw.pd.eu.db.service.IUnitService;
import pwr.tin.tip.sw.pd.eu.db.utils.AddressUtils;
import pwr.tin.tip.sw.pd.eu.db.utils.DateTime;


@Component(value="unitService")
public class UnitServiceImpl implements IUnitService {

	@Autowired(required=true)
	private IUnitDao unitDao;
	
	@Value("${executive.unit.id}")
	private Integer unitId;
	
	@Value("${thread.pool.max.tasks}")
	private Integer maxProcessNo;

	@Override
	@PostConstruct
	public void registerUnit() {
		Unit unit = new Unit();
		unit.setIdUnit(unitId);
		unit.setAddressIp(AddressUtils.getLocalIpAddress());
		unit.setMaxProcessNo(maxProcessNo);
		unit.setOverloadFlg(false);
		unit.setType(UnitType.EU);
		unit.setLastUpdateDt(DateTime.now());
		unitDao.save(unit);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void setOverload() {
		unitDao.setOverload(unitId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void setFree() {
		unitDao.setFree(unitId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void ping() {
		unitDao.ping(unitId);
	}

	@Override
	@PreDestroy
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void removeUnit() {
		unitDao.removeUnit(unitId);
	}
	
	
}
