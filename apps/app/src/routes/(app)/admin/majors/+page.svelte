<script lang="ts">
	import { goto } from '$app/navigation';
	import client from '$lib/api/client';
	import type { PageProps } from './$types';

	let { data }: PageProps = $props();

	let majors = $state(data.majors);

	async function create(e: SubmitEvent) {
		e.preventDefault();
		const formData = new FormData(e.target as HTMLFormElement);
		const name = formData.get('name')?.toString() ?? '';
		const uid = formData.get('uid')?.toString() ?? '';
		const school_uid = formData.get('school_uid')?.toString() ?? '';
		const discontinued = formData.get('discontinued') !== null;

		const newMajor = await client.POST('/major/', {
			body: {
				name: name ?? '',
				uid: uid ?? '',
				school_uid: school_uid ?? '',
				discontinued: discontinued ?? false
			}
		});

		majors.push(newMajor.data ?? {});
	}
</script>

<h3>List</h3>

<ul>
	{#each majors as major (major.id)}
		<li>
			<a href={`/admin/majors/${major.id}`}
				>{major.name} ({major.uid}) school:{major.school?.name}</a
			>
		</li>
	{/each}
</ul>

<h3>Create</h3>
<form onsubmit={create}>
	<input type="text" name="name" placeholder="Name" required />
	<input type="text" name="uid" placeholder="UID" required />
	<select name="school_uid">
		{#each data.schools as school (school.id)}
			<option value={school.uid}>{school.uid}</option>
		{/each}
	</select>
	Discontinued <input type="checkbox" name="discontinued" />
	<button type="submit">Create</button>
</form>
<br />
<br />
<button onclick={() => goto('../')}> Go back </button>
