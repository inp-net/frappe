import client from '$lib/api/client';
import type { PageLoad } from './$types';

export const load: PageLoad = async ({ params, fetch }) => {
	const id = params.id;

	const document = await client.GET(`/document/{id}`, {
		params: { path: { id } },
		fetch
	});

	const comments = await client.GET(`/comment/`, {
		fetch
	});

	return {
		document: document.data ?? {},
		comments: comments.data ?? []
	};
};
