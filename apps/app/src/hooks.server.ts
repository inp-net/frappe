import type { Handle, HandleFetch } from '@sveltejs/kit';
import { getAuthUserFromJWT } from './lib/utils';

export const handle: Handle = async ({ event, resolve }) => {
	event.locals.me = getAuthUserFromJWT(event.cookies.get('token'));
	event.locals.authenticated = Boolean(event.locals.me)
	return await resolve(event);
};


export const handleFetch: HandleFetch = async ({event, request, fetch}) => {
	const token = event.cookies.get('token');

	if (token)
		request.headers.set("Authorization", `Bearer ${token}`);

	return fetch(request);
};
