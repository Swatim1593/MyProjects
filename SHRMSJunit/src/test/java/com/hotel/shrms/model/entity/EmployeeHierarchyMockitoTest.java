package com.hotel.shrms.model.entity;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hotel.shmrs.model.entity.Employee;
import com.hotel.shmrs.model.entity.Manager;
import com.hotel.shmrs.model.entity.Receptionist;


@ExtendWith(MockitoExtension.class)
class EmployeeHierarchyMockitoTest {

	static class HotelStaffService {
		public void conductDailyBriefing(List<Employee> employees) {
			for (Employee emp : employees) {
				emp.performDuties();
			}
		}
	}

	private HotelStaffService staffService;

	@BeforeEach
	void setUp() {
		staffService = new HotelStaffService();
	}

	@Test
	@DisplayName("Should verify that performDuties is invoked on abstract Employee mock")
	void testAbstractEmployeeMock() {
		Employee mockEmployee = mock(Employee.class);
		Employee mockEmployee1 = mock(Employee.class);
		Employee mockEmployee2 = mock(Employee.class);

		staffService.conductDailyBriefing(List.of(mockEmployee,mockEmployee1,mockEmployee2));

		verify(mockEmployee, times(1)).performDuties();
		verify(mockEmployee1, times(1)).performDuties();
		verify(mockEmployee2, times(1)).performDuties();
	}

	@Test
	@DisplayName("Should verify performDuties invocations for specific subtype mocks")
	void testPolymorphicSubclassMocks() {
		Manager mockManager = mock(Manager.class);
		Receptionist mockReceptionist = mock(Receptionist.class);

		staffService.conductDailyBriefing(List.of(mockManager, mockReceptionist));

		verify(mockManager, times(1)).performDuties();
		verify(mockReceptionist, times(1)).performDuties();
		verifyNoMoreInteractions(mockManager, mockReceptionist);
	}

	@Test
	@DisplayName("Should spy on concrete Manager instance and track real method execution")
	void testSpyManagerExecution() {
		Manager realManager = new Manager("EMP-01", "Vikram Rathore", 95000.0);
		Manager realManager1 = new Manager("EMP-02", "Vikram", 95000.0);
		Manager realManager2 = new Manager("EMP-03", "Rathore", 95000.0);
		Manager spyManager = spy(realManager);
		Manager spyManager1 = spy(realManager1);
		Manager spyManager2 = spy(realManager2);

		staffService.conductDailyBriefing(List.of(spyManager,spyManager1,spyManager2));

		verify(spyManager, times(1)).performDuties();
		verify(spyManager1, times(1)).performDuties();
		verify(spyManager2, times(1)).performDuties();
	}
}