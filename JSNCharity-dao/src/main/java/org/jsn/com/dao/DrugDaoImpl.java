package org.jsn.com.dao;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jsn.com.datasource.SessionWrapper;
import org.jsn.dto.DrugDto;
import org.springframework.beans.factory.annotation.Autowired;

public class DrugDaoImpl implements DrugDao {
	private static final String EXPIRY_DATE = "expiryDate";
	private static final String DRUG_NAME = "drugName";
	private static final String USER_NAME = "userName";
	private final SessionWrapper session;

	@Autowired
	public DrugDaoImpl(SessionWrapper session) {
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	private int autoIncreaseBatchNo(String drugName, String userName) {
		Criteria criteria = this.session.createCriteria(DrugDto.class);
		criteria.add(Restrictions.eq(USER_NAME, userName));
		criteria.add(Restrictions.eq(DRUG_NAME, drugName));
		criteria.addOrder(Order.desc("batchNo"));
		criteria.setMaxResults(1);
		List<DrugDto> list = criteria.list();
		return list.isEmpty() ? 0 : list.get(0).getBatchNo() + 1;
	}

	@Override
	public void delete(List<DrugDto> deleteDrugList) {
		Transaction transaction = this.session.beginTransaction();
		deleteDrugList.stream().forEach(this.session::delete);
		transaction.commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DrugDto> getPharmaDrug(String userName) {
		Criteria criteria = this.session.createCriteria(DrugDto.class);
		criteria.add(Restrictions.eq(USER_NAME, userName));
		return criteria.list();
	}

	@PostConstruct
	private void init() {
		Criteria criteria = this.session.createCriteria(DrugDto.class);
		Date lo = Date.valueOf(LocalDate.now());
		Date hi = Date.valueOf(LocalDate.now().plusWeeks(1));
		criteria.add(Restrictions.lt(EXPIRY_DATE, lo));
		this.delete(criteria.list());
	}

	@Override
	public void insert(DrugDto dto) {
		Transaction transaction = this.session.beginTransaction();
		dto.setBatchNo(this.autoIncreaseBatchNo(dto.getDrugName(), dto.getUserName()));
		this.session.persist(dto);
		transaction.commit();
		this.session.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DrugDto> search(Map<String, Object> searchCriteriaMap, String SearchText) {
		Criteria criteria = this.session.createCriteria(DrugDto.class);
		criteria.add(Restrictions.like(DRUG_NAME, SearchText));
		return criteria.add(Restrictions.allEq(searchCriteriaMap)).list();
	}

	@Override
	public void update(DrugDto dto) {
		Transaction transaction = this.session.beginTransaction();
		this.session.update(dto);
		transaction.commit();
	}

}
