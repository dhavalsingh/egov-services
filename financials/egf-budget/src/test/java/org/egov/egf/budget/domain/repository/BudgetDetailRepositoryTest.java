package org.egov.egf.budget.domain.repository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.egov.common.contract.request.RequestInfo;
import org.egov.common.domain.model.Pagination;
import org.egov.egf.budget.domain.model.Budget;
import org.egov.egf.budget.domain.model.BudgetDetail;
import org.egov.egf.budget.domain.model.BudgetDetailSearch;
import org.egov.egf.budget.persistence.entity.BudgetDetailEntity;
import org.egov.egf.budget.persistence.queue.repository.BudgetDetailQueueRepository;
import org.egov.egf.budget.persistence.repository.BudgetDetailJdbcRepository;
import org.egov.egf.budget.web.contract.BudgetDetailRequest;
import org.egov.egf.master.web.contract.BudgetGroupContract;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BudgetDetailRepositoryTest {

	@Mock
	private BudgetDetailJdbcRepository budgetDetailJdbcRepository;

	private BudgetDetailRepository budgetDetailRepositoryWithKafka;

	private BudgetDetailRepository budgetDetailRepositoryWithOutKafka;

	@Mock
	private BudgetDetailQueueRepository budgetDetailQueueRepository;

	@Captor
	private ArgumentCaptor<BudgetDetailRequest> captor;

	private RequestInfo requestInfo = new RequestInfo();

	@Before
	public void setup() {
		budgetDetailRepositoryWithKafka = new BudgetDetailRepository(budgetDetailJdbcRepository,
				budgetDetailQueueRepository, "yes");

		budgetDetailRepositoryWithOutKafka = new BudgetDetailRepository(budgetDetailJdbcRepository,
				budgetDetailQueueRepository, "no");
	}

	@Test
	public void test_find_by_id() {
		BudgetDetailEntity entity = getBudgetDetailEntity();
		BudgetDetail expectedResult = entity.toDomain();

		when(budgetDetailJdbcRepository.findById(any(BudgetDetailEntity.class))).thenReturn(entity);

		BudgetDetail actualResult = budgetDetailRepositoryWithKafka.findById(getBudgetDetailDomin());

		assertEquals(expectedResult.getAnticipatoryAmount(), actualResult.getAnticipatoryAmount());
		assertEquals(expectedResult.getApprovedAmount(), actualResult.getApprovedAmount());
		assertEquals(expectedResult.getBudgetAvailable(), actualResult.getBudgetAvailable());
		assertEquals(expectedResult.getOriginalAmount(), actualResult.getOriginalAmount());
		assertEquals(expectedResult.getPlanningPercent(), actualResult.getPlanningPercent());
	}

	@Test
	public void test_find_by_id_return_null() {
		BudgetDetailEntity entity = getBudgetDetailEntity();

		when(budgetDetailJdbcRepository.findById(null)).thenReturn(entity);

		BudgetDetail actualResult = budgetDetailRepositoryWithKafka.findById(getBudgetDetailDomin());

		assertEquals(null, actualResult);
	}

	@Test
	public void test_save_with_kafka() {

		List<BudgetDetail> expectedResult = getBudgetDetails();

		budgetDetailRepositoryWithKafka.save(expectedResult, requestInfo);

		verify(budgetDetailQueueRepository).addToQue(captor.capture());

		final BudgetDetailRequest actualRequest = captor.getValue();

		assertEquals(expectedResult.get(0).getAnticipatoryAmount(),
				actualRequest.getBudgetDetails().get(0).getAnticipatoryAmount());
		assertEquals(expectedResult.get(0).getApprovedAmount(),
				actualRequest.getBudgetDetails().get(0).getApprovedAmount());
		assertEquals(expectedResult.get(0).getBudgetAvailable(),
				actualRequest.getBudgetDetails().get(0).getBudgetAvailable());
		assertEquals(expectedResult.get(0).getOriginalAmount(),
				actualRequest.getBudgetDetails().get(0).getOriginalAmount());
		assertEquals(expectedResult.get(0).getPlanningPercent(),
				actualRequest.getBudgetDetails().get(0).getPlanningPercent());

	}

	@Test
	public void test_save_with_out_kafka() {

		List<BudgetDetail> expectedResult = getBudgetDetails();

		BudgetDetailEntity entity = new BudgetDetailEntity().toEntity(expectedResult.get(0));

		when(budgetDetailJdbcRepository.create(any(BudgetDetailEntity.class))).thenReturn(entity);

		budgetDetailRepositoryWithOutKafka.save(expectedResult, requestInfo);

		verify(budgetDetailQueueRepository).addToSearchQue(captor.capture());

		final BudgetDetailRequest actualRequest = captor.getValue();

		assertEquals(expectedResult.get(0).getAnticipatoryAmount(),
				actualRequest.getBudgetDetails().get(0).getAnticipatoryAmount());
		assertEquals(expectedResult.get(0).getApprovedAmount(),
				actualRequest.getBudgetDetails().get(0).getApprovedAmount());
		assertEquals(expectedResult.get(0).getBudgetAvailable(),
				actualRequest.getBudgetDetails().get(0).getBudgetAvailable());
		assertEquals(expectedResult.get(0).getOriginalAmount(),
				actualRequest.getBudgetDetails().get(0).getOriginalAmount());
		assertEquals(expectedResult.get(0).getPlanningPercent(),
				actualRequest.getBudgetDetails().get(0).getPlanningPercent());
	}

	@Test
	public void test_update_with_kafka() {

		List<BudgetDetail> expectedResult = getBudgetDetails();

		budgetDetailRepositoryWithKafka.update(expectedResult, requestInfo);

		verify(budgetDetailQueueRepository).addToQue(captor.capture());

		final BudgetDetailRequest actualRequest = captor.getValue();

		assertEquals(expectedResult.get(0).getAnticipatoryAmount(),
				actualRequest.getBudgetDetails().get(0).getAnticipatoryAmount());
		assertEquals(expectedResult.get(0).getApprovedAmount(),
				actualRequest.getBudgetDetails().get(0).getApprovedAmount());
		assertEquals(expectedResult.get(0).getBudgetAvailable(),
				actualRequest.getBudgetDetails().get(0).getBudgetAvailable());
		assertEquals(expectedResult.get(0).getOriginalAmount(),
				actualRequest.getBudgetDetails().get(0).getOriginalAmount());
		assertEquals(expectedResult.get(0).getPlanningPercent(),
				actualRequest.getBudgetDetails().get(0).getPlanningPercent());
	}

	@Test
	public void test_update_with_out_kafka() {

		List<BudgetDetail> expectedResult = getBudgetDetails();

		BudgetDetailEntity entity = new BudgetDetailEntity().toEntity(expectedResult.get(0));

		when(budgetDetailJdbcRepository.update(any(BudgetDetailEntity.class))).thenReturn(entity);

		budgetDetailRepositoryWithOutKafka.update(expectedResult, requestInfo);

		verify(budgetDetailQueueRepository).addToSearchQue(captor.capture());

		final BudgetDetailRequest actualRequest = captor.getValue();

		assertEquals(expectedResult.get(0).getAnticipatoryAmount(),
				actualRequest.getBudgetDetails().get(0).getAnticipatoryAmount());
		assertEquals(expectedResult.get(0).getApprovedAmount(),
				actualRequest.getBudgetDetails().get(0).getApprovedAmount());
		assertEquals(expectedResult.get(0).getBudgetAvailable(),
				actualRequest.getBudgetDetails().get(0).getBudgetAvailable());
		assertEquals(expectedResult.get(0).getOriginalAmount(),
				actualRequest.getBudgetDetails().get(0).getOriginalAmount());
		assertEquals(expectedResult.get(0).getPlanningPercent(),
				actualRequest.getBudgetDetails().get(0).getPlanningPercent());
	}

	@Test
	public void test_save() {

		BudgetDetailEntity entity = getBudgetDetailEntity();
		BudgetDetail expectedResult = entity.toDomain();

		when(budgetDetailJdbcRepository.create(any(BudgetDetailEntity.class))).thenReturn(entity);

		BudgetDetail actualResult = budgetDetailRepositoryWithKafka.save(getBudgetDetailDomin());

		assertEquals(expectedResult.getAnticipatoryAmount(), actualResult.getAnticipatoryAmount());
		assertEquals(expectedResult.getApprovedAmount(), actualResult.getApprovedAmount());
		assertEquals(expectedResult.getBudgetAvailable(), actualResult.getBudgetAvailable());
		assertEquals(expectedResult.getOriginalAmount(), actualResult.getOriginalAmount());
		assertEquals(expectedResult.getPlanningPercent(), actualResult.getPlanningPercent());

	}

	@Test
	public void test_update() {

		BudgetDetailEntity entity = getBudgetDetailEntity();
		BudgetDetail expectedResult = entity.toDomain();

		when(budgetDetailJdbcRepository.update(any(BudgetDetailEntity.class))).thenReturn(entity);

		BudgetDetail actualResult = budgetDetailRepositoryWithKafka.update(getBudgetDetailDomin());

		assertEquals(expectedResult.getAnticipatoryAmount(), actualResult.getAnticipatoryAmount());
		assertEquals(expectedResult.getApprovedAmount(), actualResult.getApprovedAmount());
		assertEquals(expectedResult.getBudgetAvailable(), actualResult.getBudgetAvailable());
		assertEquals(expectedResult.getOriginalAmount(), actualResult.getOriginalAmount());
		assertEquals(expectedResult.getPlanningPercent(), actualResult.getPlanningPercent());

	}

	@Test
	public void test_search() {

		Pagination<BudgetDetail> expectedResult = new Pagination<>();
		expectedResult.setPageSize(500);
		expectedResult.setOffset(0);

		when(budgetDetailJdbcRepository.search(any(BudgetDetailSearch.class))).thenReturn(expectedResult);

		Pagination<BudgetDetail> actualResult = budgetDetailRepositoryWithKafka.search(getBudgetDetailSearch());

		assertEquals(expectedResult, actualResult);

	}

	private BudgetDetail getBudgetDetailDomin() {
		BudgetDetail budgetDetail = new BudgetDetail();
		budgetDetail.setApprovedAmount(BigDecimal.ONE);
		budgetDetail.setAnticipatoryAmount(BigDecimal.ONE);
		budgetDetail.setBudgetAvailable(BigDecimal.ONE);
		budgetDetail.setOriginalAmount(BigDecimal.ONE);
		budgetDetail.setPlanningPercent(BigDecimal.valueOf(1500));
		budgetDetail.setTenantId("default");
		return budgetDetail;
	}

	private BudgetDetailEntity getBudgetDetailEntity() {
		BudgetDetailEntity entity = new BudgetDetailEntity();
		entity.setApprovedAmount(BigDecimal.ONE);
		entity.setAnticipatoryAmount(BigDecimal.ONE);
		entity.setBudgetAvailable(BigDecimal.ONE);
		entity.setOriginalAmount(BigDecimal.ONE);
		entity.setPlanningPercent(BigDecimal.valueOf(1500));
		entity.setTenantId("default");
		return entity;
	}

	private BudgetDetailSearch getBudgetDetailSearch() {
		BudgetDetailSearch budgetSearch = new BudgetDetailSearch();
		budgetSearch.setPageSize(500);
		budgetSearch.setOffset(0);
		return budgetSearch;

	}

	private List<BudgetDetail> getBudgetDetails() {

		List<BudgetDetail> budgetDetails = new ArrayList<BudgetDetail>();

		BudgetDetail budgetDetail = BudgetDetail.builder().budget(Budget.builder().id("1").build())
				.budgetGroup(BudgetGroupContract.builder().id("1").build()).anticipatoryAmount(BigDecimal.TEN)
				.originalAmount(BigDecimal.TEN).approvedAmount(BigDecimal.TEN).budgetAvailable(BigDecimal.TEN)
				.planningPercent(BigDecimal.valueOf(1500)).build();

		budgetDetail.setTenantId("default");
		budgetDetails.add(budgetDetail);

		return budgetDetails;
	}
}
