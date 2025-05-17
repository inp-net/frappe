<script lang="ts">
	import Document from '$lib/components/Document.svelte';
	import type { PageProps } from './$types';

	let { data }: PageProps = $props();

	let documents = $state(data.documents);
</script>

<section>
	{#each documents as document (document.id)}
		<Document {document} />
	{/each}
</section>

<style lang="scss">
	section {
		display: grid;
		grid-template-columns: repeat(auto-fill, minmax(12rem, 1fr));
		gap: 0.5rem;
	}
</style>
