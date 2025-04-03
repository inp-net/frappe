import type { AuthUser } from './types/AuthUser';

/**
 *
 * @description This function takes a JWT token and decodes it. It returns the payload of the token.
 * @param token JWT token
 * @returns Parsed payload of the token
 */
export function parseJWT(token: string): { [key: string]: any } {
	const payload = token.split('.')[1];
	return JSON.parse(atob(payload));
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
