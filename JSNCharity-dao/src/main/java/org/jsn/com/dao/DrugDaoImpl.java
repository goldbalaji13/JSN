package org.jsn.com.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jsn.com.datasource.SessionWrapper;
import org.jsn.dto.DrugDto;
import org.springframework.beans.factory.annotation.Autowired;

public class DrugDaoImpl implements DrugDao {
	private final SessionWrapper session;

	@Autowired
	public DrugDaoImpl(SessionWrapper session) {
		this.session = session;
	}

	private int autoIncreaseBatchNo(String drugName, String userName) {
		Criteria criteria = this.session.createCriteria(DrugDto.class);
		criteria.add(Restrictions.eq("userName", userName));
		criteria.add(Restrictions.eq("drugName", drugName));
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

	@Override
	public List<DrugDto> getPharmaDrug(String userName) {
		Criteria criteria = this.session.createCriteria(DrugDto.class);
		criteria.add(Restrictions.eq("userName", userName));
		List<DrugDto> list = criteria.list();
		return list;
	}

	@Override
	public void insert(DrugDto dto) {
		Transaction transaction = this.session.beginTransaction();
		dto.setBatchNo(this.autoIncreaseBatchNo(dto.getDrugName(), dto.getUserName()));
		this.session.persist(dto);
		transaction.commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DrugDto> search(Map<String, Object> searchCriteriaMap) {
		Criteria criteria = this.session.createCriteria(DrugDto.class);
		return criteria.add(Restrictions.allEq(searchCriteriaMap)).list();
	}

	@Override
	public void update(DrugDto dto) {
		Transaction transaction = this.session.beginTransaction();
		this.session.update(dto);
		transaction.commit();
	}

}
