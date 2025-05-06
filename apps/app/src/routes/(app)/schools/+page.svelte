<script lang="ts">
	import client from '$lib/api/client';
	import type { PageProps } from './$types';

	let { data }: PageProps = $props();

	async function call() {
		console.log(await client.GET('/school/'));
	}
</script>

<ul>
	{#each data.schools as school}
		<li>#{school.id} - {school.name} ({school.uid})</li>
	{/each}
</ul>

<button onclick={call}> test </button>
