import { redirect } from "@sveltejs/kit";
import type { LayoutServerLoad } from "./$types";

export const load: LayoutServerLoad = async ({ locals }) => {
	if (locals.authenticated) // allready login go back to app
		throw redirect(302, "/");
};
