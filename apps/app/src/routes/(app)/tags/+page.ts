import client from '$lib/api/client';
import type { PageLoad } from './$types';

export const load: PageLoad = async ({ fetch }) => {
	const tags = await client.GET('/tag/', {
		fetch
	});

	return {
		tags: tags.data ?? []
	};
};
