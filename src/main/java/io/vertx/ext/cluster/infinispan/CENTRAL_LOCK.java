/*
 * Copyright 2017 Red Hat, Inc.
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

package io.vertx.ext.cluster.infinispan;

import io.vertx.core.Handler;

/**
 * Woraround {@link #use_thread_id_for_lock_owner} visibility:
 * it can't be set programatically in {@link org.jgroups.protocols.CENTRAL_LOCK},
 * but we need this to create a fork channel in {@link InfinispanClusterManager#join(Handler)}.
 *
 * @author Thomas Segismont
 */
class CENTRAL_LOCK extends org.jgroups.protocols.CENTRAL_LOCK {

  public void setUseThreadIdForLockOwner(boolean use_thread_id_for_lock_owner) {
    this.use_thread_id_for_lock_owner = use_thread_id_for_lock_owner;
  }
}
