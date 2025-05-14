import client from '$lib/api/client';
import type { PageLoad } from './$types';

export const load: PageLoad = async ({ params, fetch }) => {
	const id = Number(params.id);
	if (isNaN(id)) throw new Error('Invalid minor ID');

	const minor = await client.GET(`/minor/{id}`, {
		params: { path: { id } },
		fetch
	});

	const majors = await client.GET(`/major/`, {
		fetch
	});

	return {
		minor: minor.data ?? null,
		majors: majors.data ?? []
	};
};
