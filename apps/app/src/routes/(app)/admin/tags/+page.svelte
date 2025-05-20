<script lang="ts">
	import { goto } from '$app/navigation';
	import client from '$lib/api/client';
	import type { PageProps } from './$types';

	let { data }: PageProps = $props();

	let tags = $state(data.tags);

	async function create(e: SubmitEvent) {
		e.preventDefault();
		const formData = new FormData(e.target as HTMLFormElement);
		const name = formData.get('name')?.toString() ?? '';

		const newTag = await client.POST('/tag/', {
			body: {
				name
			}
		});

		tags.push(newTag.data ?? {});
	}
</script>

<h3>List</h3>

<ul>
	{#each tags as tag (tag.id)}
		<li>
			<a href={`/admin/tags/${tag.id}`}>
				{tag.name} ({tag.id})
			</a>
		</li>
	{/each}
</ul>

<h3>Create</h3>
<form onsubmit={create}>
	<input type="text" name="name" placeholder="Name" required />
	<button type="submit">Create</button>
</form>
<br />
<br />
<button onclick={() => goto('../')}> Go back </button>
