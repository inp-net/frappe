import client from "$lib/api/client";
import type { PageLoad } from "./$types";

export const load: PageLoad = async ({ fetch }) => {
	const schools = await client.GET("/school/", {
		fetch
	})

	return {
		schools: schools.data ?? []
	}
};
