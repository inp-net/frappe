import client from '$lib/api/client';
import type { PageLoad } from './$types';
import { getSubjects } from '$lib/utils';

export const load: PageLoad = async ({ fetch, parent }) => {
	const data = await parent();
	const subjects = await getSubjects(
		data.me?.major?.id,
		data.me?.minor ? [data.me.minor.id as number] : [],
		[]
	);
	const tags = await client.GET('/tag/', { fetch });

	return {
		subjects: subjects ?? [],
		tags: tags.data ?? []
	};
};
