package org.springframework.security.oauth.provider;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth.provider.token.OAuthAccessProviderToken;

import javax.servlet.http.HttpServletRequest;

/**
 * Callback interface for handing authentication details that are used when an authenticated request for a protected resource is received.
 *
 * @deprecated The OAuth 1.0 Protocol <a href="https://tools.ietf.org/html/rfc5849">RFC 5849</a> is obsoleted by the OAuth 2.0 Authorization Framework <a href="https://tools.ietf.org/html/rfc6749">RFC 6749</a>.
 *
 * @author Ryan Heaton
 */
@Deprecated
public interface OAuthAuthenticationHandler {

  /**
   * Create the authentication object for an authenticated OAuth request.
   *
   * @param request The request that was successfully authenticated.
   * @param authentication The consumer authentication (details about how the request was authenticated).
   * @param authToken The OAuth token associated with the authentication. This token MAY be null if no authenticated token was needed to successfully
   * authenticate the request (for example, in the case of 2-legged OAuth).
   * @return The new authentication object. For example, the user authentication if the request is made on behalf of a user.
   */
  Authentication createAuthentication(HttpServletRequest request, ConsumerAuthentication authentication, OAuthAccessProviderToken authToken);
}
