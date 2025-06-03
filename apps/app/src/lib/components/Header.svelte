<script lang="ts">
	let { title } = $props();
</script>

<header>
	<h1>{title}</h1>
</header>

<style lang="scss">
	header {
		display: flex;
		margin-bottom: 0.25rem;

		h1 {
			font-size: 2rem;
		}
	}
</style>
