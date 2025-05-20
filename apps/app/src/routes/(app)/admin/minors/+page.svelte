<script lang="ts">
	import { goto } from '$app/navigation';
	import client from '$lib/api/client';
	import type { PageProps } from './$types';

	let { data }: PageProps = $props();

	let minors = $state(data.minors);

	async function create(e: SubmitEvent) {
		e.preventDefault();
		const formData = new FormData(e.target as HTMLFormElement);
		const name = formData.get('name')?.toString() ?? '';
		const major_uid = formData.get('major_uid')?.toString() ?? '';

		const newMinor = await client.POST('/minor/', {
			body: {
				name: name ?? '',
				major_uid: major_uid ?? ''
			}
		});

		minors.push(newMinor.data ?? {});
	}
</script>

<h3>List</h3>

<ul>
	{#each minors as minor (minor.id)}
		<li>
			<a href={`/admin/minors/${minor.id}`}
				>{minor.name} ({minor.id}) major:{minor.major?.name}</a
			>
		</li>
	{/each}
</ul>

<h3>Create</h3>
<form onsubmit={create}>
	<input type="text" name="name" placeholder="Name" required />
	<select name="major_uid">
		{#each data.majors as major (major.id)}
			<option value={major.uid}>{major.uid}</option>
		{/each}
	</select>
	<button type="submit">Create</button>
</form>
<br />
<br />
<button onclick={() => goto('../')}> Go back </button>
