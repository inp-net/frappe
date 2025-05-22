import client from '$lib/api/client';
import type { PageLoad } from './$types';

export const load: PageLoad = async ({ fetch }) => {
	const documents = await client.GET('/document/', {
		fetch
	});

	const majors = await client.GET('/major/', {
		fetch
	});

	const minors = await client.GET('/minor/', {
		fetch
	});

	const teaching_units = await client.GET('/teachingunit/', {
		fetch
	});

	const subjects = await client.GET('/subject/', {
		fetch
	});

	return {
		documents: documents.data ?? [],
		majors: majors.data ?? [],
		minors: minors.data ?? [],
		teaching_units: teaching_units.data ?? [],
		subjects: subjects.data ?? []
	};
};
