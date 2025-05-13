import createClient, { type Middleware } from 'openapi-fetch';
import type { paths } from './api';
import { env } from '$env/dynamic/public';

const client = createClient<paths>({ baseUrl: env.PUBLIC_API_URL });

const authMiddleware: Middleware = {
	async onRequest({ request }) {
		if (typeof document === 'undefined') return request; // SSR

		const token = document.cookie.match(/token=([^;]+)/)?.[1];
		if (token) request.headers.set('Authorization', `Bearer ${token}`);

		return request;
	}
};

client.use(authMiddleware);

export default client;
