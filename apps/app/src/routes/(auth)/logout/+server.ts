import { json, redirect, type RequestHandler } from '@sveltejs/kit';
import { env } from '$env/dynamic/public';

export const GET: RequestHandler = ({ cookies }) => {
	cookies.delete('token', { path: '/' });

	redirect(302, env.PUBLIC_OIDC_LOGOUT_URL || '/login');
};

export const POST: RequestHandler = ({ cookies }) => {
	cookies.delete('token', { path: '/' });
	return json(
		{
			success: true,
			message: 'Logged out successfully'
		},
		{
			status: 200
		}
	);
};
