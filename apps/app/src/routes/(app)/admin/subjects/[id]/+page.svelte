<script lang="ts">
	import { goto } from '$app/navigation';
	import client from '$lib/api/client';
	import type { PageProps } from './$types';

	let { data }: PageProps = $props();

	async function save(e: SubmitEvent) {
		e.preventDefault();
		const formData = new FormData(e.target as HTMLFormElement);
		const id = Number(formData.get('id')?.toString());
		const name = formData.get('name')?.toString() ?? '';
		const teachingunit_ids = (formData.getAll('teachingunit_id') ?? []).map(Number);

		await client.PATCH(`/subject/{id}`, {
			params: { path: { id } },
			body: {
				name: name ?? '',
				teaching_units: teachingunit_ids ?? []
			}
		});

		goto('./');
	}

	async function deleteSubject(id: number) {
		await client.DELETE(`/subject/{id}`, {
			params: { path: { id } }
		});
		goto('./');
	}
</script>

<h1>Subject #{data.subject?.id}</h1>

<div>
	Name : {data.subject?.name}, Teaching units :
	<ul>
		{#each data.subject?.teachingUnits ?? [] as teachingunit (teachingunit.id)}
			<li>{teachingunit.name}</li>
		{/each}
	</ul>
</div>

<h3>Update</h3>

<form onsubmit={save}>
	<input type="hidden" name="id" value={data.subject?.id} />
	<input type="text" name="name" value={data.subject?.name} required />
	<select multiple name="teachingunit_id">
		{#each data.teachingunits as teachingunit (teachingunit.id)}
			<option value={teachingunit.id}>{teachingunit.name}</option>
		{/each}
	</select>
	<button type="submit">Save</button>
</form>

<h3>Delete</h3>

<button onclick={() => deleteSubject(data.subject?.id ?? 0)}>Delete</button>
<br />
<br />
<button onclick={() => goto('./')}> Go back </button>
