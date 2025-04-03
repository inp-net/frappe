import type { Handle } from '@sveltejs/kit';
import { getAuthUserFromJWT } from './lib/utils';

export const handle: Handle = async ({ event, resolve }) => {
	event.locals.me = getAuthUserFromJWT(event.cookies.get('token'));
	event.locals.authenticated = Boolean(event.locals.me)
	return await resolve(event);
};
