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
			margin: 2rem 2rem 2rem 5rem;
		}
	}
</style>
