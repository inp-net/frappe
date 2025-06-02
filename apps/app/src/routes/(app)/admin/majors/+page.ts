import client from '$lib/api/client';
import type { PageLoad } from './$types';

export const load: PageLoad = async ({ fetch }) => {
	const majors = await client.GET('/major/', {
		fetch
	});

	const schools = await client.GET('/school/', {
		fetch
	});

	return {
		majors: majors.data ?? [],
		schools: schools.data ?? []
	};
};
