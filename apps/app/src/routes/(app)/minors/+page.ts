import client from '$lib/api/client';
import type { PageLoad } from './$types';

export const load: PageLoad = async ({ fetch }) => {
	const minors = await client.GET('/minor/', {
		fetch
	});

	const majors = await client.GET('/major/', {
		fetch
	});

	return {
		minors: minors.data ?? [],
		majors: majors.data ?? []
	};
};
