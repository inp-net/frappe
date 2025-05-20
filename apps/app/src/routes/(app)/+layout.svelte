<script>
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
		display: grid;
		grid-template-columns: 80px auto;
	}
</style>
