<script lang="ts">
	import DropdownMenuCheck from '$components/DropdownMenuCheck.svelte';
	import DropdownMenuRadio from '$components/DropdownMenuRadio.svelte';
	import Document from '$lib/components/Document.svelte';
	import type { PageProps } from './$types';

	let { data }: PageProps = $props();

	let documents = $state(data.documents);
</script>

<header>
	<h1>Documents</h1>
</header>

<p class="nb-docs">
	{documents.length}
	{#if documents.length <= 1}
		document
	{:else}
		documents
	{/if}
</p>

<div class="selectors">
	<DropdownMenuRadio name="Majeures" data={data.majors} />
	<DropdownMenuCheck name="Mineures" data={data.minors} />
	<DropdownMenuCheck name="UEs" data={data.teaching_units} />
	<DropdownMenuCheck name="Cours" data={data.subjects} />
	<DropdownMenuCheck name="Tags" data={data.tags} />
</div>

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
		margin-bottom: 0.25rem;

		h1 {
			font-size: 1.5rem;
		}
	}

	.selectors {
		display: flex;
		gap: 0.5rem;
	}

	.nb-docs {
		margin-bottom: 1rem;
	}

	section {
		display: grid;
		grid-template-columns: repeat(auto-fill, minmax(12rem, 1fr));
		gap: 0.5rem;
		width: 100%;
	}
</style>
