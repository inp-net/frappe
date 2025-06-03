import client from '$lib/api/client';
import type { LayoutLoad } from './$types';

export const load: LayoutLoad = async ({ fetch }) => {
	const me = await client.GET('/me', {
		fetch
	});

	const majors = await client.GET('/major/', {
		params: {
			query: {
				schoolId: me.data?.school?.id
			}
		},
		fetch
	});

	const minors = await client.GET('/minor/', {
		params: {
			query: {
				majorId: me.data?.major?.id
			}
		},
		fetch
	});

	const teaching_units = await client.GET('/teachingunit/', {
		fetch
	});

	const subjects = await client.GET('/subject/', {
		fetch
	});

	const tags = await client.GET('/tag/', {
		fetch
	});

	return {
		me: me.data,
		majors: majors.data ?? [],
		minors: minors.data ?? [],
		teaching_units: teaching_units.data ?? [],
		subjects: subjects.data ?? [],
		tags: tags.data ?? []
	};
};
