import type { AuthUser } from './types/AuthUser';

/**
 * @description This function safely parses a JWT token and returns its payload.
 * @param token JWT token string
 * @returns Parsed payload or throws an error
 */

export function parseJWT(token: string): Record<string, unknown> {
	const base64Url = token.split('.')[1];
	const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
	const jsonPayload = decodeURIComponent(
		atob(base64)
			.split('')
			.map(function (c) {
				return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
			})
			.join('')
	);

	return JSON.parse(jsonPayload);
}

/**
 * Extracts and returns the user information from a JSON Web Token (JWT).
 *
 * @param token - The JWT as a string, or `undefined` if no token is provided.
 * @returns The user information as an `AuthUser` object if the token is valid, or `undefined` if the token is invalid or not provided.
 */
export function getAuthUserFromJWT(token: string | undefined): AuthUser | undefined {
	if (!token) return undefined;
	const payload = parseJWT(token);
	return payload['user_info'] as AuthUser;
}
