<script lang="ts">
	import DropdownMenuCheck from '$components/DropdownMenuCheck.svelte';
	import DropdownMenuRadio from '$components/DropdownMenuRadio.svelte';
	import Document from '$lib/components/Document.svelte';
	import { InfiniteLoader, LoaderState } from 'svelte-infinite';
	import type { PageProps } from './$types';
	import client from '$lib/api/client';

	let { data }: PageProps = $props();

	let documents = $state(data.documents);
	let page = data.page;

	let hasMore = true;
	let loaderState = new LoaderState();

	let loadMore = async () => {
		if (!hasMore) return;

		page += 1;
		const nextDocs = await client.GET('/document/', {
			params: {
				query: {
					pageNum: page
				}
			}
		});
		const newDocs = nextDocs.data?.content ?? [];
		documents = [...documents, ...newDocs];

		if (nextDocs.data?.last) {
			hasMore = false;
			loaderState.complete();
			return;
		}
		loaderState.loaded();
	};
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
	<InfiniteLoader {loaderState} triggerLoad={loadMore}>
		<section class="grid">
			{#each documents as document (document.id)}
				<Document {document} />
			{/each}
		</section>
		{#snippet loading()}
			Loading ...
		{/snippet}
	</InfiniteLoader>
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
		margin-bottom: 1rem;
	}

	.nb-docs {
		margin-bottom: 1rem;
	}

	.grid {
		display: grid;
		grid-template-columns: repeat(auto-fill, minmax(12rem, 1fr));
		gap: 0.5rem;
		width: 100%;
	}
</style>
