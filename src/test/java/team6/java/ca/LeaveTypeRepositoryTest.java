package team6.java.ca;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import team6.java.ca.entities.LeaveType;
import team6.java.ca.repositories.LeaveTypeRepository;


@SpringBootTest
public class LeaveTypeRepositoryTest {

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Test
    public void testCreateAndFindLeaveType() {
    	leaveTypeRepository.deleteAll();
        LeaveType leaveType = new LeaveType("Sick Leave");
        leaveTypeRepository.save(leaveType);

        LeaveType foundLeaveType = leaveTypeRepository.findByLeaveTypeName("Sick Leave");

        assertThat(foundLeaveType).isNotNull();
        assertThat(foundLeaveType.getLeaveTypeName()).isEqualTo("Sick Leave");
    }

    @Test
    public void testFindLeaveTypeNameById() {
    	leaveTypeRepository.deleteAll();
        LeaveType leaveType = new LeaveType("Annual Leave");
        leaveTypeRepository.save(leaveType);

        String leaveTypeName = leaveTypeRepository.findLeaveTypeNameById(leaveType.getLeaveTypeId());

        assertThat(leaveTypeName).isEqualTo("Annual Leave");
    }

    @Test
    public void testFindAllLeaveTypes() {
        leaveTypeRepository.deleteAll();

        leaveTypeRepository.save(new LeaveType("Maternity Leave"));
        leaveTypeRepository.save(new LeaveType("Paternity Leave"));

        List<LeaveType> leaveTypes = leaveTypeRepository.findAll();

        assertThat(leaveTypes).hasSize(2);
        assertThat(leaveTypes).extracting(LeaveType::getLeaveTypeName)
                .containsExactlyInAnyOrder("Maternity Leave", "Paternity Leave");
    }
}