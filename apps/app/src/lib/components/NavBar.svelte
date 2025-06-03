<script lang="ts">
	import { page } from '$app/state';
	import Icon from '@iconify/svelte';
</script>

<nav>
	<div class="top">
		<a href="/" class={{ active: page.url.pathname === '/' }}>
			<Icon icon="heroicons:home" />
		</a>
	</div>
	<div class="bottom">
		<a href="/user" class={{ active: page.url.pathname.startsWith('/user') }}>
			<Icon icon="heroicons:user-circle" />
		</a>
		<a href="/logout">
			<Icon icon="heroicons:arrow-right-on-rectangle-solid" />
		</a>
	</div>
</nav>

<style lang="scss">
	a {
		display: flex;
		justify-content: center;
		align-items: center;
		padding: 0.3rem;
		font-size: 1.5rem;
		color: #66686b;
		border-radius: 5px;
		transition: all 0.2s ease-in-out;

		&:hover,
		&.active {
			background-color: rgba(15, 90, 166, 15%);
		}
	}

	nav {
		display: flex;
		position: fixed;
		flex-direction: column;
		justify-content: space-between;
		align-items: center;
		width: 3rem;
		height: 100dvh;
		padding: 1rem 0;
		box-sizing: border-box;
		background-color: #f8f9fa;
		border-right: solid 1px #e6e6e6;
		font-size: 1.2rem;

		%top-bottom {
			display: flex;
			justify-content: center;
			align-items: center;
			flex-direction: column;
			width: 100%;
			gap: 0.5rem;
		}

		.top {
			@extend %top-bottom;
		}

		.bottom {
			@extend %top-bottom;
		}
	}
</style>
