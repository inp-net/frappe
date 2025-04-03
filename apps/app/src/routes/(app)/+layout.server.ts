import { redirect } from '@sveltejs/kit';
import type { LayoutServerLoad } from './$types';

export const load: LayoutServerLoad = async ({ locals }) => {
	if (!locals.authenticated) // if not authenticated goto login
		throw redirect(302, "/login");
	
	return {
		me: locals.me
	}
};
