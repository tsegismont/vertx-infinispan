/*
 * Copyright 2023 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.vertx.core.shareddata;

import io.vertx.Lifecycle;
import io.vertx.LoggingTestWatcher;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.ext.cluster.infinispan.InfinispanClusterManager;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Thomas Segismont
 */
public class InfinispanClusteredAsynchronousLockTest extends io.vertx.tests.shareddata.ClusteredAsynchronousLockTest {

  @Rule
  public LoggingTestWatcher watchman = new LoggingTestWatcher();

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  @Override
  public void setUp() throws Exception {
    System.setProperty("jgroups.file.location", temporaryFolder.newFolder().getAbsolutePath());
    super.setUp();
  }

  @Override
  protected Future<Vertx> clusteredVertx(VertxOptions options, ClusterManager clusterManager) {
    Future<Vertx> ret = super.clusteredVertx(options, clusterManager);
    try {
      ret.await(2, TimeUnit.MINUTES);
    } catch (Exception e) {
      fail(e.getMessage());
    }
    return ret;
  }

  @Override
  protected ClusterManager getClusterManager() {
    return new InfinispanClusterManager();
  }

  @Override
  @Test
  public void testLockReleasedForClosedNode() throws Exception {
    super.testLockReleasedForClosedNode();
  }

  @Override
  @Test
  public void testLockReleasedForKilledNode() throws Exception {
    super.testLockReleasedForKilledNode();
  }

  @Override
  protected long getLockTimeout() {
    return 30000;
  }

  @Override
  protected void close(List<Vertx> clustered) throws Exception {
    Lifecycle.close(clustered);
  }
}
