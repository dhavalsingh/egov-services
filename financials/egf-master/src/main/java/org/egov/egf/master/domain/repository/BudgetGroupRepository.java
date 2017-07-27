package org.egov.egf.master.domain.repository;

import java.util.HashMap;
import java.util.Map;

import org.egov.common.domain.model.Pagination;
import org.egov.egf.master.domain.model.BudgetGroup;
import org.egov.egf.master.domain.model.BudgetGroupSearch;
import org.egov.egf.master.persistence.entity.BudgetGroupEntity;
import org.egov.egf.master.persistence.queue.MastersQueueRepository;
import org.egov.egf.master.persistence.repository.BudgetGroupJdbcRepository;
import org.egov.egf.master.web.requests.BudgetGroupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BudgetGroupRepository {

	@Autowired
	private BudgetGroupJdbcRepository budgetGroupJdbcRepository;
	@Autowired
	private MastersQueueRepository budgetGroupQueueRepository;

	public BudgetGroup findById(BudgetGroup budgetGroup) {
		BudgetGroupEntity entity = budgetGroupJdbcRepository.findById(new BudgetGroupEntity().toEntity(budgetGroup));
		return entity.toDomain();

	}

	@Transactional
	public BudgetGroup save(BudgetGroup budgetGroup) {
		BudgetGroupEntity entity = budgetGroupJdbcRepository.create(new BudgetGroupEntity().toEntity(budgetGroup));
		return entity.toDomain();
	}

	@Transactional
	public BudgetGroup update(BudgetGroup budgetGroup) {
		BudgetGroupEntity entity = budgetGroupJdbcRepository.update(new BudgetGroupEntity().toEntity(budgetGroup));
		return entity.toDomain();
	}

	public void add(BudgetGroupRequest request) {
		Map<String, Object> message = new HashMap<>();

		if (request.getRequestInfo().getAction().equalsIgnoreCase("create")) {
			message.put("budgetgroup_create", request);
		} else {
			message.put("budgetgroup_update", request);
		}
		budgetGroupQueueRepository.add(message);
	}

	public Pagination<BudgetGroup> search(BudgetGroupSearch domain) {

		return budgetGroupJdbcRepository.search(domain);

	}

}