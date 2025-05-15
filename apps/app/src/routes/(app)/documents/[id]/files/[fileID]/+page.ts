import client from '$lib/api/client';
import type { PageLoad } from './$types';

export const load: PageLoad = async ({ params, fetch }) => {
	const document = await client.GET('/document/{id}', {
		params: { path: { id: params.id } },
		fetch
	});

	const file = document.data?.files?.find((file) => file.id === params.fileID);

	if (!file) return new Response('File not found', { status: 404 });

	return {
		documentID: params.id,
		file: file
	};
};
