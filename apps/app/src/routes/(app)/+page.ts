import client from '$lib/api/client';
import type { PageLoad } from './$types';

export const load: PageLoad = async ({ fetch }) => {
	const documents = await client.GET('/document/', {
		fetch
	});

	const minors = await client.GET('/minor/', {
		fetch
	});

	return {
		documents: documents.data ?? [],
		minors: minors.data ?? []
	};
};
