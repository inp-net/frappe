<script lang="ts">
	import { goto } from '$app/navigation';
	import client from '$lib/api/client';
	import type { PageProps } from './$types';

	let { data }: PageProps = $props();

	let schools = $state(data.schools);

	async function create(e: SubmitEvent) {
		e.preventDefault();
		const formData = new FormData(e.target as HTMLFormElement);
		const name = formData.get('name')?.toString() ?? '';
		const uid = formData.get('uid')?.toString() ?? '';

		const newSchool = await client.POST('/school/', {
			body: {
				name: name ?? '',
				uid: uid ?? ''
			}
		});

		schools.push(newSchool.data ?? {});
	}
</script>

<h3>List</h3>

<ul>
	{#each schools as school (school.id)}
		<li><a href={`/admin/schools/${school.id}`}>{school.name} ({school.uid})</a></li>
	{/each}
</ul>

<h3>Create</h3>

<form onsubmit={create}>
	<input type="text" name="name" placeholder="Name" required />
	<input type="text" name="uid" placeholder="UID" required />
	<button type="submit">Create</button>
</form>
<br />
<br />
<button onclick={() => goto('./')}> Go back </button>
