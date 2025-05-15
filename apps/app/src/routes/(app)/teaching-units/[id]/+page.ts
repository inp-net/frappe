import client from '$lib/api/client';
import type { PageLoad } from './$types';

export const load: PageLoad = async ({ params, fetch }) => {
	const id = Number(params.id);
	if (isNaN(id)) throw new Error('Invalid teaching unit ID');

	const teaching_unit = await client.GET(`/teachingunit/{id}`, {
		params: { path: { id } },
		fetch
	});

	const majors = await client.GET(`/major/`, {
		fetch
	});

	const minors = await client.GET(`/minor/`, {
		fetch
	});

	return {
		teaching_unit: teaching_unit.data ?? null,
		majors: majors.data ?? [],
		minors: minors.data ?? []
	};
};
