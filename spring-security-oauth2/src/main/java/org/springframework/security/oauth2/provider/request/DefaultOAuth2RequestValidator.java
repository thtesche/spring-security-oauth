package org.springframework.security.oauth2.provider.request;

import java.util.Set;

import org.springframework.security.oauth2.common.exceptions.InvalidScopeException;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2RequestValidator;
import org.springframework.security.oauth2.provider.TokenRequest;

/**
 * Default implementation of {@link OAuth2RequestValidator}. 
 *
 * @deprecated See the <a href="https://github.com/spring-projects/spring-security/wiki/OAuth-2.0-Migration-Guide">OAuth 2.0 Migration Guide</a> for Spring Security 5.
 *
 * @author Amanda Anganes
 *
 */
@Deprecated
public class DefaultOAuth2RequestValidator implements OAuth2RequestValidator {

	public void validateScope(AuthorizationRequest authorizationRequest, ClientDetails client) throws InvalidScopeException {
		validateScope(authorizationRequest.getScope(), client.getScope());
	}

	public void validateScope(TokenRequest tokenRequest, ClientDetails client) throws InvalidScopeException {
		validateScope(tokenRequest.getScope(), client.getScope());
	}
	
	private void validateScope(Set<String> requestScopes, Set<String> clientScopes) {

		if (clientScopes != null && !clientScopes.isEmpty()) {
			for (String scope : requestScopes) {
				if (!clientScopes.contains(scope)) {
					throw new InvalidScopeException("Invalid scope", clientScopes);
				}
			}
		}
		
		if (requestScopes.isEmpty()) {
			throw new InvalidScopeException("Empty scope (either the client or the user is not allowed the requested scopes)");
		}
	}

}
