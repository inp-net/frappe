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
		const majors = (formData.getAll('majors_id') ?? []).map(Number);
		const minors = (formData.getAll('minors_id') ?? []).map(Number);

		await client.PATCH(`/teachingunit/{id}`, {
			params: { path: { id } },
			body: {
				name,
				majors,
				minors
			}
		});

		goto('./');
	}

	async function deleteTeachingUnit(id: number) {
		await client.DELETE(`/teachingunit/{id}`, {
			params: { path: { id } }
		});
		goto('./');
	}
</script>

<h1>Teaching Unit #{data.teaching_unit?.id}</h1>

<div>
	Name : {data.teaching_unit?.name} <br />
	Majors :
	<ul>
		{#each data.teaching_unit?.majors ?? [] as major (major.id)}
			<li>{major.name}</li>
		{/each}
	</ul>
	Minors :
	<ul>
		{#each data.teaching_unit?.minors ?? [] as minor (minor.id)}
			<li>{minor.name}</li>
		{/each}
	</ul>
</div>

<h3>Update</h3>

<form class="form" onsubmit={save}>
	<input type="hidden" name="id" value={data.teaching_unit?.id} />
	<label for="name">Choisir un nouveau nom</label>
	<input type="text" name="name" value={data.teaching_unit?.name} required />
	<label for="majors_id">Choisir de nouvelles majeures</label>
	<select name="majors_id" id="" multiple>
		{#each data.majors as major (major.id)}
			<option value={major.id}>{major.name}</option>
		{/each}
	</select>
	<label for="minors_id">Choisir de nouvelles mineures</label>
	<select name="minors_id" id="" multiple>
		{#each data.minors as minor (minor.id)}
			<option value={minor.id}>{minor.name}</option>
		{/each}
	</select>
	<button type="submit">Save</button>
</form>

<h3>Delete</h3>

<button onclick={() => deleteTeachingUnit(data.teaching_unit?.id ?? 0)}> Delete </button>
<br />
<br />
<button onclick={() => goto('./')}> Go back </button>

<style>
	.form {
		display: flex;
		flex-direction: column;
		gap: 0.5rem;
		width: 30rem;
	}
</style>
