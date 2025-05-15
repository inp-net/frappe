import client from '$lib/api/client';
import type { PageLoad } from './$types';

export const load: PageLoad = async ({ fetch }) => {
	const teaching_units = await client.GET('/teachingunit/', {
		fetch
	});

	const majors = await client.GET('/major/', {
		fetch
	});

	const minors = await client.GET('/minor/', {
		fetch
	});

	return {
		teaching_units: teaching_units.data ?? [],
		majors: majors.data ?? [],
		minors: minors.data ?? []
	};
};
