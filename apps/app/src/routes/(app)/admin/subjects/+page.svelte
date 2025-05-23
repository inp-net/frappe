<script lang="ts">
	import { goto } from '$app/navigation';
	import client from '$lib/api/client';
	import type { PageProps } from './$types';

	let { data }: PageProps = $props();

	let subjects = $state(data.subjects);

	async function create(e: SubmitEvent) {
		e.preventDefault();
		const formData = new FormData(e.target as HTMLFormElement);
		const name = formData.get('name')?.toString() ?? '';
		const teachingunit_ids = (formData.getAll('teachingunit_id') ?? []).map(Number);
		const newSubject = await client.POST('/subject/', {
			body: {
				name: name ?? '',
				teaching_units: teachingunit_ids ?? []
			}
		});
		console.log(newSubject.data);
		if (newSubject.data !== null && newSubject.data !== undefined) {
			subjects.push(newSubject.data ?? {});
		}
	}
</script>

<h3>List</h3>

<ul>
	{#each subjects as subject (subject.id)}
		<li>
			<a href={`/admin/subjects/${subject.id}`}>{subject.name} ({subject.id}) </a>
		</li>
	{/each}
</ul>

<h3>Create</h3>
<form onsubmit={create}>
	<input type="text" name="name" placeholder="Name" required />
	<select multiple name="teachingunit_id">
		{#each data.teachingunits as teachingunit (teachingunit.id)}
			<option value={teachingunit.id}>{teachingunit.name}</option>
		{/each}
	</select>
	<button type="submit">Create</button>
</form>
<br />
<br />
<button onclick={() => goto('./')}> Go back </button>
