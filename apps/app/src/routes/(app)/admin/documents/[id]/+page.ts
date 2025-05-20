import client from '$lib/api/client';
import type { PageLoad } from './$types';

export const load: PageLoad = async ({ params, fetch }) => {
	const document = await client.GET('/document/{id}', {
		params: { path: { id: params.id } },
		fetch
	});

	return {
		document: document.data ?? null
	};
};
