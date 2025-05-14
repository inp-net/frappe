import client from '$lib/api/client';
import type { PageLoad } from './$types';

export const load: PageLoad = async ({ params, fetch }) => {
	const id = Number(params.id);
	if (isNaN(id)) throw new Error('Invalid major ID');

	const major = await client.GET(`/major/{id}`, {
		params: { path: { id } },
		fetch
	});

	const schools = await client.GET(`/school/`, {
		fetch
	});

	return {
		major: major.data ?? null,
		schools: schools.data ?? []
	};
};
