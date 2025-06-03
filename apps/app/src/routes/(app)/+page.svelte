<script lang="ts">
	import Header from '$components/Header.svelte';
	import DropdownMenuCheck from '$components/DropdownMenuCheck.svelte';
	import DropdownMenuRadio from '$components/DropdownMenuRadio.svelte';
	import Document from '$lib/components/Document.svelte';
	import { InfiniteLoader, LoaderState } from 'svelte-infinite';
	import type { PageProps } from './$types';
	import client from '$lib/api/client';
	import { getMinors, getSubjects, getTeachingUnit, getDocuments } from '$lib/utils';

	let { data }: PageProps = $props();

	let minors = $state(data.minors);
	let teaching_units = $state(data.teaching_units);
	let subjects = $state(data.subjects);
	let documents = $state(data.documents);

	let page = data.page;

	let hasMore = true;
	let loaderState = new LoaderState();

	let loadMore = async () => {
		if (!hasMore) return;

		page += 1;
		const nextDocs = await getDocuments(
			page,
			major_sel,
			minors_sel,
			teaching_units_sel,
			subjects_sel,
			tags_sel
		);
		const newDocs = nextDocs?.content ?? [];
		documents = [...documents, ...newDocs];

		if (nextDocs?.last) {
			hasMore = false;
			loaderState.complete();
			return;
		}
		loaderState.loaded();
	};

	$effect(() => {
		client
			.GET('/major/', {
				params: {
					query: {
						schoolId: data.me?.school?.id
					}
				}
			})
			.then((res) => {
				data.majors = res.data ?? [];
			});
	});

	let major_sel = $state(data.me?.major?.id);
	let minors_sel = $state(data.me?.minor?.id ? [data.me?.minor?.id] : []);
	let teaching_units_sel = $state([]);
	let subjects_sel = $state([]);
	let tags_sel = $state([]);

	$effect(() => {
		if (major_sel === data.me?.major?.id) {
			minors_sel = data.me?.minor?.id ? [data.me?.minor?.id] : [];
		} else {
			minors_sel = [];
		}

		teaching_units_sel = [];
		subjects_sel = [];
	});

	$effect(() => {
		getMinors(major_sel).then((data) => {
			minors = data;
			teaching_units_sel = [];
			subjects_sel = [];
		});
	});

	$effect(() => {
		getTeachingUnit(major_sel, minors_sel).then((data) => {
			teaching_units = data;
			subjects_sel = [];
		});
	});

	$effect(() => {
		getSubjects(major_sel, minors_sel, teaching_units_sel).then((data) => {
			subjects = data;
		});
	});

	$effect(() => {
		getDocuments(0, major_sel, minors_sel, teaching_units_sel, subjects_sel, tags_sel).then(
			(data) => {
				documents = data?.content ?? [];
				page = 0;
			}
		);
	});
</script>

<section class="wrapper">
	<Header title="Documents" />

	<p class="nb-docs">
		{documents.length}
		{#if documents.length <= 1}
			document
		{:else}
			documents
		{/if}
	</p>

	<div class="selectors">
		<DropdownMenuRadio name="Majeures" data={data.majors} bind:selected={major_sel} />
		{#key minors}
			<DropdownMenuCheck name="Mineures" data={minors} bind:selecteds={minors_sel} />
		{/key}
		{#key teaching_units}
			<DropdownMenuCheck
				name="UEs"
				data={teaching_units}
				bind:selecteds={teaching_units_sel}
			/>
		{/key}
		{#key subjects}
			<DropdownMenuCheck name="Matières" data={subjects} bind:selecteds={subjects_sel} />
		{/key}
		<DropdownMenuCheck name="Tags" data={data.tags} bind:selecteds={tags_sel} />
	</div>

	<InfiniteLoader {loaderState} triggerLoad={loadMore}>
		<section class="documents">
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
	.wrapper {
		margin: 1rem;

		.selectors {
			display: flex;
			gap: 0.5rem;
			margin-bottom: 1rem;
		}

		.nb-docs {
			margin-bottom: 1rem;
		}

		.documents {
			display: grid;
			grid-template-columns: repeat(auto-fill, minmax(12rem, 1fr));
			gap: 0.5rem;
			width: 100%;
		}
	}
</style>
