import client from '$lib/api/client';
import type { PageLoad } from './$types';

export const load: PageLoad = async ({ fetch }) => {
	const subjects = await client.GET('/subject/', {
		fetch
	});

	const teachingunits = await client.GET('/teachingunit/', {
		fetch
	});

	return {
		subjects: subjects.data ?? [],
		teachingunits: teachingunits.data ?? []
	};
};
