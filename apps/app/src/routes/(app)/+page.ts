import client from '$lib/api/client';
import type { PageLoad } from './$types';

export const load: PageLoad = async ({ fetch, parent }) => {
	const data = await parent();
	const documents = await client.GET('/document/', {
		params: {
			query: {
				schoolId: data.me?.school?.id,
				majorId: data.me?.major?.id,
				minorIds: data.me?.minor?.id ? [data.me.minor.id as number] : []
			}
		},
		fetch
	});

	return {
		documents: documents.data?.content ?? [],
		page: documents.data?.pageable?.pageNumber ?? 0
	};
};
