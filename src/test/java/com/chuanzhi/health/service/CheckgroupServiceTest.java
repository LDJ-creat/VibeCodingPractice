package com.chuanzhi.health.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.chuanzhi.health.dao.CheckgroupDao;
import com.chuanzhi.health.model.Checkgroup;

@ExtendWith(MockitoExtension.class)
public class CheckgroupServiceTest {

    @InjectMocks
    private CheckgroupService checkgroupService;

    @Mock
    private CheckgroupDao checkgroupDao;

    @Mock
    private Connection connection;

    // This is a bit tricky to mock static methods like DBUtil.getConnection()
    // For this test, we will assume the connection is handled correctly and focus on DAO interactions.
    // A more advanced setup might use PowerMock or refactor DBUtil to be injectable.
    // The service logic, however, can be tested by verifying DAO method calls.

    @BeforeEach
    public void setUp() throws SQLException {
        // We don't need to mock DBUtil if we only verify DAO calls,
        // as the service methods will be called directly.
        // The service's internal connection handling is assumed to work.
        // We inject the mocked DAO into the service.
        checkgroupService.setCheckgroupDao(checkgroupDao);
    }

    @Test
    public void testAdd() throws SQLException {
        // 1. 准备测试数据
        Checkgroup group = new Checkgroup();
        group.setCode("CG001");
        group.setName("Test Group");
        List<Integer> itemIds = Arrays.asList(1, 2, 3);
        group.setCheckitemIds(itemIds);
        int generatedGid = 100;

        // 2. Mock DAO 方法
        // 当调用 checkgroupDao.add 时，返回一个生成的 gid
        when(checkgroupDao.add(any(), any(Checkgroup.class))).thenReturn(generatedGid);

        // 3. 调用被测试的 Service 方法
        checkgroupService.add(group);

        // 4. 验证 DAO 方法是否被正确调用
        // 验证 checkgroupDao.add 方法被调用了一次
        verify(checkgroupDao, times(1)).add(any(), eq(group));

        // 验证 checkgroupDao.addCheckgroupCheckitem 方法被调用了一次，并传入了正确的参数
        verify(checkgroupDao, times(1)).addCheckgroupCheckitem(any(), eq(generatedGid), eq(itemIds));
    }

    @Test
    public void testUpdate() throws SQLException {
        // 1. Prepare test data
        Checkgroup group = new Checkgroup();
        group.setGid(101);
        group.setCode("CG002");
        group.setName("Updated Group");
        List<Integer> itemIds = Arrays.asList(4, 5);
        group.setCheckitemIds(itemIds);

        // 2. Mock DAO behavior (methods called within the transaction)
        // No return value needed for these void methods

        // 3. Call the service method
        checkgroupService.update(group);

        // 4. Verify DAO methods were called in the correct order
        // We can use an InOrder verifier for this
        org.mockito.InOrder inOrder = inOrder(checkgroupDao);

        // Verify update is called first
        inOrder.verify(checkgroupDao, times(1)).update(any(), eq(group));

        // Verify delete associations is called next
        inOrder.verify(checkgroupDao, times(1)).deleteCheckgroupCheckitem(any(), eq(group.getGid()));

        // Verify add new associations is called last
        inOrder.verify(checkgroupDao, times(1)).addCheckgroupCheckitem(any(), eq(group.getGid()), eq(itemIds));
    }
}