import { parseJWT } from '$lib/utils';
import { json, redirect, type RequestHandler } from '@sveltejs/kit';

export const GET: RequestHandler = ({ cookies }) => {
	cookies.delete('token', { path: '/' });

	redirect(302, '/login');
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
