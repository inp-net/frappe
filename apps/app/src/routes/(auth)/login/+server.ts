import { redirect, type RequestHandler } from '@sveltejs/kit';
import { env } from '$env/dynamic/public';

export const GET: RequestHandler = ({ url }) => {
	const callbackUrl = encodeURIComponent(url.origin + '/login/callback');
	redirect(302, `${env.PUBLIC_API_URL}/auth/login?callback=${callbackUrl}`);
};
