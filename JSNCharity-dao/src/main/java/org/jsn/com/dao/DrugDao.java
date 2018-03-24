package org.jsn.com.dao;

import java.util.List;

import org.jsn.dto.DrugDto;

public interface DrugDao extends AbstractDao<DrugDto> {

	public void delete(List<DrugDto> deleteDrugList);

	public List<DrugDto> getPharmaDrug(String userName);

	public void insert(DrugDto dto);

	public void update(DrugDto dto);
}
