/*
 * Copyright 2008 Web Cohesion
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.security.oauth.provider;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * Authentication details and includes the details of the OAuth consumer.
 *
 * @deprecated The OAuth 1.0 Protocol <a href="https://tools.ietf.org/html/rfc5849">RFC 5849</a> is obsoleted by the OAuth 2.0 Authorization Framework <a href="https://tools.ietf.org/html/rfc6749">RFC 6749</a>.
 *
 * @author Ryan Heaton
 */
@SuppressWarnings("serial")
@Deprecated
public class OAuthAuthenticationDetails extends WebAuthenticationDetails {

  private final ConsumerDetails consumerDetails;

  public OAuthAuthenticationDetails(HttpServletRequest request, ConsumerDetails consumerDetails) {
    super(request);
    this.consumerDetails = consumerDetails;
  }

  public ConsumerDetails getConsumerDetails() {
    return consumerDetails;
  }
}
