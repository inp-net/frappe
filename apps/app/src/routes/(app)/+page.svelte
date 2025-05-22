<script lang="ts">
	import Document from '$lib/components/Document.svelte';
	import type { PageProps } from './$types';

	let { data }: PageProps = $props();

	let documents = $state(data.documents);
</script>

<header>
	<h1>Documents</h1>
</header>

<section>
	{#each documents as document (document.id)}
		<Document {document} />
	{/each}
</section>

<style lang="scss">
	header {
		display: flex;
		justify-content: space-between;
		width: 100%;
		margin-bottom: 1rem;

		h1 {
			font-size: 1.5rem;
		}
	}

	section {
		display: grid;
		grid-template-columns: repeat(auto-fill, minmax(12rem, 1fr));
		gap: 0.5rem;
		width: 100%;
	}
</style>
