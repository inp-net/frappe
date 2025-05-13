import client from '$lib/api/client';
import type { PageLoad } from './$types';

export const load: PageLoad = async ({ params, fetch }) => {
	const id = Number(params.id);
	if (isNaN(id)) throw new Error('Invalid school ID');

	const school = await client.GET(`/school/{id}`, {
		params: { path: { id } },
		fetch
	});

	return {
		school: school.data ?? null
	};
};
