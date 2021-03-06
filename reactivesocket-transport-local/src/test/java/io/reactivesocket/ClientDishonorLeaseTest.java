/*
 * Copyright 2016 Netflix, Inc.
 * <p>
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 *  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 *  specific language governing permissions and limitations under the License.
 */

package io.reactivesocket;

import io.reactivesocket.test.util.LocalRSRule;
import io.reactivesocket.util.PayloadImpl;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ClientDishonorLeaseTest {

    @Rule
    public final LocalRSRule rule = new LocalRSRule();

    @Test(timeout = 10000)
    public void testNoLeasesSentToClient() throws Exception {
        ReactiveSocket socket = rule.connectSocket();
        rule.sendLease();

        TestSubscriber<Payload> s = TestSubscriber.create();
        socket.requestResponse(PayloadImpl.EMPTY).subscribe(s);
        s.awaitTerminalEvent();

        assertThat("Unexpected leases received by the client.", rule.getLeases(), is(empty()));
    }

}
