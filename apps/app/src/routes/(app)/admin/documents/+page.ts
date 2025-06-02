import client from '$lib/api/client';
import type { PageLoad } from './$types';

export const load: PageLoad = async ({ fetch }) => {
	const documents = await client.GET('/document/', {
		fetch
	});

	return {
		documents: documents.data?.content ?? [],
		page: documents.data?.pageable?.pageNumber ?? 0
	};
};
