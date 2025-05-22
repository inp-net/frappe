<script lang="ts">
	import Selector from '$components/Selector.svelte';
	import Document from '$lib/components/Document.svelte';
	import type { PageProps } from './$types';

	let { data }: PageProps = $props();

	let documents = $state(data.documents);
</script>

<header>
	<h1>Documents</h1>
	<div class="selectors">
		<Selector name="Majeures" data={data.majors} />
		<Selector name="Mineures" data={data.minors} />
		<Selector name="UEs" data={data.teaching_units} />
		<Selector name="Cours" data={data.subjects} />
	</div>
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

		.selectors {
			display: flex;
			gap: 0.5rem;
		}

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
