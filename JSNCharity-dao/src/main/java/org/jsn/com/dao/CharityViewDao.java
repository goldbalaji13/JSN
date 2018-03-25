package org.jsn.com.dao;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsn.com.datasource.SessionWrapper;
import org.jsn.com.entity.JoinedEntity;
import org.jsn.dto.DrugDto;
import org.jsn.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

public class CharityViewDao implements AbstractDao<JoinedEntity> {

	private final SessionWrapper session;

	@Autowired
	public CharityViewDao(SessionWrapper session) {
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	public List<JoinedEntity> getCharityDrug() {
		Date date = Date.valueOf(LocalDate.now().plusWeeks(1));
		List<Object[]> list = this.session.createSQLQuery(
				"SELECT d.*,u.* from drug d inner join  users u on  d.userName = u.userName and d.expiryDate <='"
						+ date.toString() + "';")
				.addEntity("d", DrugDto.class).addEntity("u", UserDto.class).list();
		return list.stream().map(JoinedEntity::EntityFactory).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JoinedEntity> search(Map<String, Object> searchCriteriaMap, String searchText) {
		Date date = Date.valueOf(LocalDate.now().plusWeeks(1));
		List<Object[]> list = this.session.createSQLQuery(
				"SELECT d.*,u.* from drug d inner join  users u on  d.userName = u.userName and d.expiryDate <='"
						+ date.toString() + "' and d.drugName like '" + searchText + "';")
				.addEntity("d", DrugDto.class).addEntity("u", UserDto.class).list();
		return list.stream().map(JoinedEntity::EntityFactory).collect(Collectors.toList());
	}
}
