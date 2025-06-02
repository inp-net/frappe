import client from '$lib/api/client';
import type { PageLoad } from './$types';

export const load: PageLoad = async ({ params, fetch }) => {
	const id = Number(params.id);
	if (isNaN(id)) throw new Error('Invalid subject ID');

	const subject = await client.GET(`/subject/{id}`, {
		params: { path: { id } },
		fetch
	});

	const teachingunits = await client.GET(`/teachingunit/`, {
		fetch
	});

	return {
		subject: subject.data ?? null,
		teachingunits: teachingunits.data ?? []
	};
};
