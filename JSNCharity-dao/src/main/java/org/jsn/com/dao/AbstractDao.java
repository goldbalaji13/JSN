package org.jsn.com.dao;

import java.util.List;
import java.util.Map;

public interface AbstractDao<T> {

	public List<T> search(Map<String, Object> searchCriteriaMap, String searchText);
}
