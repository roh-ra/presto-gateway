package com.lyft.data.gateway.ha.router;

import com.lyft.data.gateway.ha.config.DataStoreConfiguration;
import com.lyft.data.gateway.ha.HaGatewayTestUtils;
import com.lyft.data.gateway.ha.persistence.JdbcConnectionManager;
import org.testng.annotations.BeforeClass;

import java.io.File;

public class TestRuleEngineBackendManager {
  private RuleEngineBackendManager ruleEngineBackendManager;

  @BeforeClass(alwaysRun = true)
  public void setUp() {
    File baseDir = new File(System.getProperty("java.io.tmpdir"));
    File tempH2DbDir = new File(baseDir, "h2db-" + System.currentTimeMillis());
    tempH2DbDir.deleteOnExit();
    HaGatewayTestUtils.seedRequiredData(
        new HaGatewayTestUtils.TestConfig("", tempH2DbDir.getAbsolutePath()));
    String jdbcUrl = "jdbc:h2:" + tempH2DbDir.getAbsolutePath();
    DataStoreConfiguration db = new DataStoreConfiguration(jdbcUrl, "sa", "sa", "org.h2.Driver");
    JdbcConnectionManager connectionManager = new JdbcConnectionManager(db);
    ruleEngineBackendManager = new RoutingRuleEngineBackendManager(connectionManager) {};
  }
}
