<script>
	import { env } from '$env/dynamic/public';

	const login = () => {
		const callbackUrl = encodeURIComponent(window.location.origin + '/login/callback');
		window.location.href = `${env.PUBLIC_API_URL}/auth/login?callback=${callbackUrl}`;
	};
</script>

<button onclick={login}>Login with Authentik</button>
