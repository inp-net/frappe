import client from '$lib/api/client';
import type { LayoutLoad } from './$types';

export const load: LayoutLoad = async ({ fetch }) => {
	const subjects = await client.GET('/subject/', { fetch });
	const tags = await client.GET('/tag/', { fetch });

	return {
		subjects: subjects.data ?? [],
		tags: tags.data ?? []
	};
};
