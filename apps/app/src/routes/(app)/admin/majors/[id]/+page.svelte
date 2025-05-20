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
		const uid = formData.get('uid')?.toString() ?? '';
		const school_uid = formData.get('school_uid')?.toString() ?? '';
		const discontinued = formData.get('discontinued') !== null;

		await client.PATCH(`/major/{id}`, {
			params: { path: { id } },
			body: {
				name: name ?? '',
				uid: uid ?? '',
				school_uid: school_uid ?? '',
				discontinued: discontinued ?? false
			}
		});
		goto('./');
	}

	async function deleteMajor(id: number) {
		await client.DELETE(`/major/{id}`, {
			params: { path: { id } }
		});
		goto('./');
	}
</script>

<h1>Major #{data.major?.id}</h1>

<p>
	name : {data.major?.name}, uid : {data.major?.uid}, discontinued : {data.major?.discontinued},
	school : {data.major?.school?.name}
</p>

<h3>Update</h3>

<form onsubmit={save}>
	<input type="hidden" name="id" value={data.major?.id} />
	<input type="text" name="name" value={data.major?.name} required />
	<input type="text" name="uid" value={data.major?.uid} required />

	<select name="school_uid">
		{#each data.schools as school (school.id)}
			<option value={school.uid}>{school.uid}</option>
		{/each}
	</select>

	Discontinued<input type="checkbox" name="discontinued" />
	<button type="submit">Save</button>
</form>

<h3>Delete</h3>

<button onclick={() => deleteMajor(data.major?.id ?? 0)}>Delete</button>
<br />
<br />
<button onclick={() => goto('./')}> Go back </button>
