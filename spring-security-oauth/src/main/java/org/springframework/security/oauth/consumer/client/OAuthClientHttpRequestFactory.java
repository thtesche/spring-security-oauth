package org.springframework.security.oauth.consumer.client;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.security.oauth.consumer.OAuthConsumerSupport;
import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.security.oauth.consumer.OAuthSecurityContext;
import org.springframework.security.oauth.consumer.OAuthSecurityContextHolder;
import org.springframework.security.oauth.consumer.OAuthSecurityContextImpl;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Request factory that extends all http requests with the OAuth credentials for a specific protected resource.
 *
 * @deprecated The OAuth 1.0 Protocol <a href="https://tools.ietf.org/html/rfc5849">RFC 5849</a> is obsoleted by the OAuth 2.0 Authorization Framework <a href="https://tools.ietf.org/html/rfc6749">RFC 6749</a>.
 *
 * @author Ryan Heaton
 */
@Deprecated
public class OAuthClientHttpRequestFactory implements ClientHttpRequestFactory {

  private final ClientHttpRequestFactory delegate;
  private final ProtectedResourceDetails resource;
  private final OAuthConsumerSupport support;
  private Map<String, String> additionalOAuthParameters;

  public OAuthClientHttpRequestFactory(ClientHttpRequestFactory delegate, ProtectedResourceDetails resource, OAuthConsumerSupport support) {
    this.delegate = delegate;
    this.resource = resource;
    this.support = support;

    if (delegate == null) {
      throw new IllegalArgumentException("A delegate must be supplied for an OAuth2ClientHttpRequestFactory.");
    }
    if (resource == null) {
      throw new IllegalArgumentException("A resource must be supplied for an OAuth2ClientHttpRequestFactory.");
    }
    this.additionalOAuthParameters = !CollectionUtils.isEmpty(resource.getAdditionalParameters()) ?
            new HashMap<String, String>(resource.getAdditionalParameters()) :
            Collections.<String, String>emptyMap();
  }

  public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
    OAuthSecurityContext context = OAuthSecurityContextHolder.getContext();
    if (context == null) {
    	context = new OAuthSecurityContextImpl();
    }

    Map<String, OAuthConsumerToken> accessTokens = context.getAccessTokens();
    OAuthConsumerToken accessToken = accessTokens == null ? null : accessTokens.get(this.resource.getId());

    boolean useAuthHeader = this.resource.isAcceptsAuthorizationHeader();
    if (!useAuthHeader) {
      String queryString = this.support.getOAuthQueryString(this.resource, accessToken, uri.toURL(), httpMethod.name(), this.additionalOAuthParameters);
      String uriValue = String.valueOf(uri);
      uri = URI.create((uriValue.contains("?") ? uriValue.substring(0, uriValue.indexOf('?')) : uriValue) + "?" + queryString);
    }

    ClientHttpRequest req = delegate.createRequest(uri, httpMethod);
    if (useAuthHeader) {
      String authHeader = this.support.getAuthorizationHeader(this.resource, accessToken, uri.toURL(), httpMethod.name(), this.additionalOAuthParameters);
      req.getHeaders().add("Authorization", authHeader);
    }

    Map<String, String> additionalHeaders = this.resource.getAdditionalRequestHeaders();
    if (additionalHeaders != null) {
      for (Map.Entry<String, String> header : additionalHeaders.entrySet()) {
        req.getHeaders().add(header.getKey(), header.getValue());
      }
    }
    return req;
  }

  /**
   * Any additional OAuth parameters to send with the OAuth request.
   *
   * @return Any additional OAuth parameters to send with the OAuth request.
   */
  public Map<String, String> getAdditionalOAuthParameters() {
    return additionalOAuthParameters;
  }

  /**
   * Any additional OAuth parameters to send with the OAuth request.
   *
   * @param additionalOAuthParameters Any additional OAuth parameters to send with the OAuth request.
   */
  public void setAdditionalOAuthParameters(Map<String, String> additionalOAuthParameters) {
    this.additionalOAuthParameters = additionalOAuthParameters;
  }
}
