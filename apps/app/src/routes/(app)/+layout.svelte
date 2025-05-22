<script lang="ts">
	import NavBar from '$components/NavBar.svelte';

	let { children } = $props();
</script>

<div class="app">
	<NavBar />
	<main>
		{@render children()}
	</main>
</div>

<style>
	.app {
		display: flex;

		main {
			width: 100%;
			margin: 1rem 1rem 1rem 4rem;
		}
	}
</style>
