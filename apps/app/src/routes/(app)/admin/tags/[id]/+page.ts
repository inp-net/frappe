import client from '$lib/api/client';
import type { PageLoad } from './$types';

export const load: PageLoad = async ({ params, fetch }) => {
	const id = Number(params.id);
	if (isNaN(id)) throw new Error('Invalid tag ID');

	const tag = await client.GET(`/tag/{id}`, {
		params: { path: { id } },
		fetch
	});

	return {
		tag: tag.data ?? null
	};
};
