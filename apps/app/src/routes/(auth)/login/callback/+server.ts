import { parseJWT } from '$lib/utils';
import { redirect, type RequestHandler } from '@sveltejs/kit';

export const GET: RequestHandler = ({ url, cookies }) => {
	const token = url.searchParams.get('token');
	if (!token) {
		return new Response('Missing token', { status: 400 });
	}

	const payload = parseJWT(token);

	cookies.set('token', token, {
		path: '/',
		httpOnly: false,
		expires: new Date(payload['exp'] * 1000) || undefined
	});

	throw redirect(302, '/');
};
