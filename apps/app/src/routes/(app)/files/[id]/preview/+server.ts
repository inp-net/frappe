import client from '$lib/api/client';
import type { RequestHandler } from '@sveltejs/kit';

/**
 * Proxy endpoint to download a file from the API.
 *
 * @param id - The ID of the file to download
 * @returns
 */
export const GET: RequestHandler = async ({ params, fetch }) => {
	const { id: fileID } = params;
	if (!fileID) return new Response('File ID is required', { status: 400 });

	const request = await client.GET('/document/file/{fileID}/preview', {
		params: {
			path: {
				fileID
			}
		},
		parseAs: 'blob', // if the file is plain text or a base64 string,
		fetch
	});

	if (request.error || !request.data) return new Response('File not found', { status: 404 });

	// proxy the response back to the client
	return new Response(request.data, {
		headers: request.response.headers
	});
};
